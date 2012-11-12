package org.jboss.arquillian.examples.quickstart.extension;

import org.jboss.arquillian.core.spi.LoadableExtension;

public class QuickstartLoadableExtension implements LoadableExtension {

	public void register(ExtensionBuilder builder) {
		builder.observer(QuickstartObserver.class);
	}

}
