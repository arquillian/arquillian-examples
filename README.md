# Arquillian Examples

This repository hosts the example projects that are covered in the [Arquillian Guides](http://arquillian.org/guides/). These projects can be identified by the word "tutorial" in the name. There are several additional examples that demonstrate other functionality in Arquillian. All the projects in this repository are self-contained (they do not use parent projects).

To see an even more comprehensive set of example tests, browse the [Arquillian Showcase](https://github.com/arquillian/arquillian-showcase) repository.

## What is Arquillian?

Arquillian is an innovative and highly extensible testing platform for the JVM that enables developers to easily create automated integration, functional and acceptance tests for Java middleware.
                                 
Find at more at http://arquillian.org

## Project Index

Below is an index of the projects in this repository paired with a brief description.

### [arquillian-tutorial](https://github.com/arquillian/arquillian-examples/tree/master/arquillian-tutorial)

This project is a starting point for using Arquillian. It has a simple CDI test case that runs against Weld EE Embedded (default), Embedded GlassFish 3.1 (default) and Managed JBoss AS 7.1.

### [arquillian-tutorial-rinse-repeat](https://github.com/arquillian/arquillian-examples/tree/master/arquillian-tutorial-rinse-repeat)

This project is a follow-up to the arquillian-tutorial project. It demonstrates the integration of CDI and EJB with a test case that runs against GlassFish Embedded 3.1 (default), Remote JBoss AS 7.1, Remote GlassFish 3.1 and Managed JBoss AS 7.1.

### [arquillian-persistence-tutorial](https://github.com/arquillian/arquillian-examples/tree/master/arquillian-persistence-tutorial)

This project contains a JPA 2 integration test that runs against Embedded GlassFish 3.1 (default), Remote GlassFish 3.1, Managed JBoss AS 7.1 and Remote JBoss AS 7.1.

### [arquillian-drone-tutorial](https://github.com/arquillian/arquillian-examples/tree/master/arquillian-drone-tutorial)

This project demonstrates the use of Arquillian Drone to drive a Selenium test. It runs against Embedded GlassFish 3.1 (default), Managed JBoss AS 7.1 and Remote JBoss AS 7.1.

### ejb31-gfembedded

This project contains EJB3.1 integration test and runs against Glassfish Embedded 3 container. The projects target is to provide simplest possible setup for this test combination.

### ejb31-jbembedded

This project contains EJB3.1 integration test and runs against JBoss AS 6 Embedded container. The projects target is to provide simplest possible setup for this test combination.
 
### ejb3-openejb

This project contains EJB3 integration test and runs against Apache OpenEJB 3.1 Embedded container. The projects target is to provide simplest possible setup for this test combination.

### quickstart

This is a simple startup project with contains both tests for POJO and EJB running against a variety of containers.
 
### jpalab

This project is a JPA 1.x lab that experiments with functionality and boundaries of transaction-scoped and extended persistence contexts. It can be run on the OpenEJB 3.1 Embedded container with either OpenJPA, Hibernate or EclipseLink as the JPA provider.

### jsfunit-servlet

This project demonstrates how to use JSFUnit with a Servlet container.

### xa

This project demonstrates the use of XA DataSources that enlist in a distributed transaction within an Arquillian test.
