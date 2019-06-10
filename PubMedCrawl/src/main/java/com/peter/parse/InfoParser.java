package com.peter.parse;

import org.springframework.context.ApplicationContext;

public abstract class InfoParser {
	protected ApplicationContext context;

	public InfoParser(ApplicationContext context) {
		this.context = context;
	}
	public abstract void parser();
}
