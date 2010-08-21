package org.jboss.arquillian.examples.quickstart;

import org.jboss.arquillian.examples.quickstart.HelloEJB;

import javax.ejb.Stateless;

/**
 * As there is a know issue with JBoss6M3, naming conventions apply for EJB
 * injection within Arquillian tests.
 * 
 * @see {@link EJBInjectionEnricher#lookup}
 * 
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class HelloEJBBean implements HelloEJB {

	@Override
	public String sayHelloEJB(String name) {

		String hello = "Hello " + name;

		return hello;
	}
}
