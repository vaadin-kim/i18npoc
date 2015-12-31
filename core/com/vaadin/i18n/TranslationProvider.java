package com.vaadin.i18n;

import java.util.Locale;

import com.vaadin.shared.Connector;

public interface TranslationProvider {

	public static ThreadLocal<Connector> currentConnector = new ThreadLocal<>();

	public String getTranslation(Locale locale, String key);

}
