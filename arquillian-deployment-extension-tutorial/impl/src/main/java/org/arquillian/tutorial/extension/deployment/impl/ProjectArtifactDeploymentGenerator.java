/*
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.arquillian.tutorial.extension.deployment.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.maven.model.Model;
import org.arquillian.tutorial.extension.deployment.api.DeployProjectArtifact;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.impl.maven.MavenDependencyResolverSettings;
import org.jboss.shrinkwrap.resolver.impl.maven.MavenRepositorySystem;

/**
 * TODO get the build final name and the output directory from the Maven environment
 * 
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 */
public class ProjectArtifactDeploymentGenerator implements DeploymentScenarioGenerator {
    @Override
    public List<DeploymentDescription> generate(TestClass testClass) {
        if (testClass.isAnnotationPresent(DeployProjectArtifact.class)) {
            DeployProjectArtifact config = testClass.getAnnotation(DeployProjectArtifact.class);
            String archiveName = config.value();
            if (archiveName.length() == 0) {
                archiveName = getArchiveNameFromPom();
            }
            Class<? extends Archive<?>> type = JavaArchive.class;
            if (archiveName.endsWith(".war")) {
                type = WebArchive.class;
            }
            else if (archiveName.endsWith(".ear")) {
                type = EnterpriseArchive.class;
            }
            File archiveFile = new File("target/" + archiveName);
            if (!archiveFile.exists()) {
                throw new IllegalStateException("Project artifact has not been generated: " + archiveFile.getAbsolutePath());
            }
            Archive<?> archive = ShrinkWrap.create(type, archiveName).as(ZipImporter.class)
                    .importFrom(new File("target/" + archiveName)).as(type);
            DeploymentDescription dd = new DeploymentDescription("application", archive);
            dd.shouldBeTestable(config.testable());
            return Arrays.asList(dd);
        }
        return Collections.emptyList();
    }
    
    // FIXME relying on internals of ShrinkWrap Maven Resolver
    private String getArchiveNameFromPom() {
        MavenRepositorySystem system = new MavenRepositorySystem();
        MavenDependencyResolverSettings settings = new MavenDependencyResolverSettings();
        Model model = system.loadPom(new File("pom.xml"), settings, system.getSession(settings));
        String finalName = model.getBuild().getFinalName();
        String packaging = model.getPackaging();
        return finalName + '.' + packaging;
    }
}
