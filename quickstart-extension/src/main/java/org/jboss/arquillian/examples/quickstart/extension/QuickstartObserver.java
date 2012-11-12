package org.jboss.arquillian.examples.quickstart.extension;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.annotation.SuiteScoped;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;

public class QuickstartObserver {

	@Inject @SuiteScoped
	private InstanceProducer<QuickstartType> typeInstance;
	
	public void exposeInternalState(@Observes BeforeSuite event) {
		typeInstance.set(new QuickstartType("Arquillian"));
	}
	
	public static class QuickstartType {
		private String name;
		
		public QuickstartType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
}
