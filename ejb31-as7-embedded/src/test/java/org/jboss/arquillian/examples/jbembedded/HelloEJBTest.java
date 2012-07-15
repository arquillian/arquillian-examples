package org.jboss.arquillian.examples.jbembedded;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class HelloEJBTest {

    @EJB
    private HelloEJB helloEJB;

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "helloEJB.jar")
            .addClasses(HelloEJB.class);
    }

    @Test
    public void testHelloEJB() {
        String result = helloEJB.sayHelloEJB("Michael");
        assertEquals("Hello Michael", result);
    }
}

