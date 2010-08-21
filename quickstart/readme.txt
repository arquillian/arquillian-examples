
                                 quickstart
 Arquillian enables you to test your business logic in a remote or embedded container. Alternatively, it can deploy an archive to the container so the test can 
 interact  as a remote client.
 
 This project extends weld-jsf-jee archetype with basic arquillian support. Examples contain both tests for POJO and EJB.

 Getting started - example using JBoss6M3 locally:
 1) download JBoss6M3 (http://sourceforge.net/projects/jboss/files/JBoss/JBoss-6.0.0.M3)
 2) configure JBoss Maven repo in settings.xml (http://community.jboss.org/wiki/MavenGettingStarted)
 3) configure JBoss home within arquillian.xml (or set JBOSS_HOME environment variable, alternatively)
 4) run: mvn clean install -Pjbossas-managed-6
 
 Using other server profiles:
 * mvn clean install -Pglassfish-remote-3
 
 
 Tests will be executed within container. Container can either be started manually or automatically by Arquillian.
 
 All about arquillian: http://jboss.org/arquillian

								 
						
						
 ========================================								 
 Source archetype: weld-jsf-jee

 What is it?
 ===========

 This is your project! It's a sample, deployable Maven 2 project to help you
 get your foot in the door developing with Java EE 6. This project is setup to
 allow you to create a compliant Java EE 6 application using JSF 2.0, CDI 1.0,
 EJB 3.1 and JPA 2.0) that can run on a certified application server (Complete
 or Web Profile). It includes a persistence unit and some sample persistence
 and transaction code to help you get your feet wet with database access in
 enterprise Java. 

 System requirements
 ===================

 All you need to run this project is Java 5.0 (Java SDK 1.5) or greator and
 Maven 2.0.10 or greater. This application is setup to be run on a Java EE 6
 certified application server. It has been tested with GlassFish V3 and JBoss
 AS 6.0.

 If you want to deploy the application to a standalone Servlet Container, then
 you will need to set one up. Alternatively, you can use a Maven command to run
 the application in place on an embedded version of GlassFish.

 Please note that Maven 2 project needs to use the JBoss Maven repository
 because there are certain Java EE API JARs that are not yet publised to the
 Maven Central Repository (see https://jira.jboss.org/jira/browse/WELD-222)

 Deploying the application
 =========================

 To deploy the application to JBoss AS (standalone), first make sure that the
 JBOSS_HOME environment variable points to a JBoss AS 6.0 installation.
 Alternatively, you can set the location of JBoss AS using the following
 profile defintion in the .m2/settings.xml file in your home directory:

<?xml version="1.0" encoding="UTF-8"?>
<settings
   xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

   <profiles>
      <profile>
         <id>environment</id>
         <activation>
            <activeByDefault>true</activeByDefault>
         </activation>
         <properties>
            <jboss.home>/path/to/jboss-as-6.0.0.M1</jboss.home>
         </properties>
      </profile>
   </profiles>
   
</settings>

 You can now deploy to JBoss AS by executing the following command:

  mvn package jboss:hard-deploy

 Start JBoss AS. The application will be running at the following URL:

  http://localhost:8080/quickstart

 If you want to deploy to GlassFish (standalone), you first need to change
 the name of the DataSource used by the persistence unit! Open this file:

  src/main/resources/META-INF/persistence.xml

 Change the value of <jta-data-source> to the following:

  <jta-data-source>jdbc/__default</jta-data-source>

 This configuration uses the built-in default Derby DataSource in GlassFish.
 Optionally, you can use an alternative DataSource of your choice.
 
 Now, execute the command:

  mvn package
 
 You can now deploy the target/quickstart.war archive and launch the
 application the through GlassFish administration console.

 Alternatively, you can deploy the application without moving any files around
 using the embedded GlassFish application server.

 To run the application using embedded GlassFish, execute this command:

  mvn package embedded-glassfish:run

 The application will be running at the following URL:
 
  http://localhost:7070/quickstart

 Importing the project into an IDE
 =================================

 If you created the project using the Maven 2 archetype wizard in your IDE
 (Eclipse, NetBeans or IntelliJ IDEA), then there is nothing to do. You should
 already have an IDE project.

 If you created the project from the commandline using archetype:generate, then
 you need to bring the project into your IDE. If you are using NetBeans 6.8 or
 IntelliJ IDEA 9, then all you have to do is open the project as an existing
 project. Both of these IDEs recognize Maven 2 projects natively.

 To import into Eclipse, you first need to install the m2eclipse plugin. To get
 started, add the m2eclipse update site (http://m2eclipse.sonatype.org/update/)
 to Eclipse and install the m2eclipse plugin and required dependencies. Once
 that is installed, you'll be ready to import the project into Eclipse.

 Select File > Import... and select "Import... > Maven Projects" and select
 your project directory. m2eclipse should take it from there.

 Once in the IDE, you can execute the Maven commands through the IDE controls
 to run the application on an embedded Servlet Container.

 Resources
 =========

 Weld archetypes:
 -  Quickstart:        http://seamframework.org/Documentation/WeldQuickstartForMavenUsers
 -  Issue tracker:     https://jira.jboss.org/jira/browse/WELDX
 -  Source code:       http://anonsvn.jboss.org/repos/weld/archetypes
 -  Forums:            http://seamframework.org/Community/WeldUsers
 JSR-299 overview:     http://seamframework.org/Weld
 JSF community site:   http://www.javaserverfaces.org

