package org.jboss.arquillian.examples.quickstart;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.examples.quickstart.HelloEJB;
import org.jboss.arquillian.examples.quickstart.HelloEJBBean;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@RunWith(Arquillian.class)
public class HelloEJBContainerTest {
	private static ByteArrayAsset EMPTY_BEANS_XML = new ByteArrayAsset(
			"<beans/>".getBytes());

	/**
	 * TODO Works for Glassfish at the moment, JBoss settings see comments.
	 */
	//@Inject works as well --> CDI injection
	@EJB
	private HelloEJB helloEJB;

	@Deployment
	public static WebArchive createTestArchive() {

		JavaArchive arch = ShrinkWrap.create(JavaArchive.class, "helloEJB.jar")
				.addClasses(HelloEJB.class, HelloEJBBean.class)
				.addManifestResource(EMPTY_BEANS_XML, "beans.xml");

		WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
				.setWebXML("glassfish-remote-3/test-web.xml").addLibrary(arch);

		return webArchive;
	}

	/**
	 * JBossAS6 config
	 */
	// @Inject currently not working due to bug in JBoss6M3.
//	 @EJB
//	 private HelloEJB helloEJB;
//	
//	 @Deployment
//	 public static JavaArchive createTestArchive() {
//	 JavaArchive arch = ShrinkWrap.create(JavaArchive.class, "helloEJB.jar")
//	 .addClasses(HelloEJB.class, HelloEJBBean.class)
//	 .addManifestResource(EMPTY_BEANS_XML, "beans.xml");
//	
//	 System.out.println("### building " + arch.getName());
//	
//	 return arch;
//	 }

	@Test
	public void testHelloEJB() {
		System.out.println("### testSayHelloEJB");
		String result = helloEJB.sayHelloEJB("Simon");
		Assert.assertEquals("Hello Simon", result);
	}
}
