
                                 ejb3-openejb
								 
 Arquillian enables you to test your business logic in a remote or embedded container. Alternatively, it can deploy an archive to the container so the test can interact as a remote client.
 
 All about arquillian: http://jboss.org/arquillian
 
 Example contains EJB3 integration test and runs against Apache OpenEJB 3.1 Embedded container. The projects target is to provide simplest possible setup for this test combination.

 Getting started
 ================ 
 1) Download sources.
 2) Configure JBoss Maven repositories in settings.xml (http://community.jboss.org/wiki/MavenGettingStarted).
 3) Run: mvn test.
 
 Tests will be executed within container. Container will be started by Arquillian, automatically.
 
 System requirements
 ===================
 All you need to run this project is Java 5.0 (Java SDK 1.5) or greater and
 Maven 2.0.10 or greater. This application is setup to be run on a Java EE 6
 certified application server.
