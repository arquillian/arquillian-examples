package org.jboss.arquillian.sandbox.examples.quickstart;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.sandbox.examples.quickstart.HelloEJB;
import org.jboss.arquillian.sandbox.examples.quickstart.HelloEJBBean;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@RunWith(Arquillian.class)
public class HelloEJBContainerTest {
	private static ByteArrayAsset EMPTY_BEANS_XML = new ByteArrayAsset(
			"<beans/>".getBytes());

	// @Inject currently not working due to bug in JBoss6M3.
	@EJB
	private HelloEJB helloEJB;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive arch = ShrinkWrap.create("helloEJB.jar", JavaArchive.class)
				.addClasses(HelloEJB.class, HelloEJBBean.class)
				.addManifestResource(EMPTY_BEANS_XML, "beans.xml");

		System.out.println("### building " + arch.getName());

		return arch;
	}

	@Test
	public void testHelloEJB() {
		System.out.println("### testSayHello");
		String result = helloEJB.sayHelloEJB("Simon");
		Assert.assertEquals("Hello Simon", result);
	}
}
