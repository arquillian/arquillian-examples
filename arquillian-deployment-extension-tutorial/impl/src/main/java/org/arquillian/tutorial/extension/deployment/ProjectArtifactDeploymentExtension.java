package org.arquillian.tutorial.extension.deployment;

import org.arquillian.tutorial.extension.deployment.impl.ProjectArtifactDeploymentGenerator;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.spi.LoadableExtension;

import java.lang.Override;

public class ProjectArtifactDeploymentExtension implements LoadableExtension {

    @Override
    public void register(ExtensionBuilder builder) {
        builder.service(DeploymentScenarioGenerator.class, ProjectArtifactDeploymentGenerator.class);
    }
}