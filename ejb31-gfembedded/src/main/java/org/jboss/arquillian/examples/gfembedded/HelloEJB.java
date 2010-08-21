package org.jboss.arquillian.examples.gfembedded;

import javax.ejb.Stateless;

/**
 * EJB3.1. with no-interface view.
 *
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class HelloEJB {

	public String sayHelloEJB(String name) {

		return "Hello " + name;
	}
}
