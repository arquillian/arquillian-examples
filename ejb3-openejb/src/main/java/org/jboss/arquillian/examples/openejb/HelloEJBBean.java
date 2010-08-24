package org.jboss.arquillian.examples.openejb;

import javax.ejb.Stateless;

/**
 * EJB3 Stateless Session Bean.
 *
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@Stateless
public class HelloEJBBean implements HelloEJB {

    public String sayHelloEJB(String name) {
        return "Hello " + name;
    }
}
