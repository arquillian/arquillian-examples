package org.jboss.arquillian.sandbox.examples.quickstart;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.sandbox.examples.quickstart.HelloWorld;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:michaelschuetz83@gmail.com">Michael Schuetz</a>
 */
@RunWith(Arquillian.class)
public class HelloWorldContainerTest {
	private static ByteArrayAsset EMPTY_BEANS_XML = new ByteArrayAsset(
			"<beans/>".getBytes());

	@Inject
	private HelloWorld helloWorld;

	@Deployment
	public static JavaArchive createTestArchive() {
		JavaArchive arch = ShrinkWrap.create("helloWorld.jar",
				JavaArchive.class).addClasses(HelloWorld.class)
				.addManifestResource(EMPTY_BEANS_XML, "beans.xml");

		System.out.println("### building " + arch.getName());

		return arch;
	}

	@Test
	public void testSayHello() {
		System.out.println("### testSayHello");
		Assert.assertEquals("Hello World!", helloWorld.getText());
	}
}
