package com.vaadin.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourceBundleTranslationProvider implements TranslationProvider {

	private Map<Locale, ResourceBundle> bundles = new HashMap<>();
	private String bundleName;

	public ResourceBundleTranslationProvider(String bundleName) {
		this.bundleName = bundleName;

	}

	@Override
	public String getTranslation(Locale locale, String key) {
		try {
			return getBundle(locale).getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	private ResourceBundle getBundle(Locale locale) {
		if (bundles.containsKey(locale)) {
			return bundles.get(locale);
		}

		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		return bundle;
	}

}
