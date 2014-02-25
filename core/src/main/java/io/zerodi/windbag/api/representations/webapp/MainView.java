package io.zerodi.windbag.api.representations.webapp;

import com.yammer.dropwizard.views.View;

/**
 * @author zerodi
 */
public class MainView extends View {

	private MainView() {
		super("single_page_app_view.ftl");
	}

	public static MainView getInstance() {
	    return new MainView();
	}
}
