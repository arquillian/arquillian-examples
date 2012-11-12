package org.jboss.arquillian.examples.quickstart.extension.unit;

import java.util.List;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.test.AbstractContainerTestTestBase;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.examples.quickstart.extension.QuickstartObserver;
import org.jboss.arquillian.examples.quickstart.extension.QuickstartObserver.QuickstartType;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.junit.Test;


public class UnitTestCase extends AbstractContainerTestTestBase {

	@Override
	protected void addExtensions(List<Class<?>> extensions) {
		extensions.add(QuickstartObserver.class);
	}
	
	@Inject
	private Instance<QuickstartType> typeInstance;
	
	@Test
	public void shouldExposeQucikStartTypeDuringBeforeSuite() {
		
		fire(new BeforeSuite());
		
		assertEventFired(QuickstartType.class, 1);
		Assert.assertEquals("Arquillian", typeInstance.get().getName());
	}

	@Test
	public void shouldNotExposeQucikStartTypeDuringBeforeClass() {
		
		fire(new BeforeClass(this.getClass()));
		
		assertEventFired(QuickstartType.class, 0);
		Assert.assertNull(typeInstance.get());
	}
}
