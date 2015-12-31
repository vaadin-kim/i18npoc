package com.example.i18npoc;

import java.text.MessageFormat;
import java.util.Locale;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.i18n.ResourceBundleTranslationProvider;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class LoginView extends LoginDesign {

	private int failedLogins = 0;

	@SuppressWarnings("unchecked")
	public LoginView() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null); //$NON-NLS-1$
		container.addItem(Locale.ENGLISH).getItemProperty("name").setValue("{lang_english}");
		container.addItem(new Locale("fi")).getItemProperty("name").setValue("{lang_finnish}");

		selectLanguage.setContainerDataSource(container);
		selectLanguage.setNullSelectionAllowed(false);
		selectLanguage.setValue(Locale.ENGLISH);
		selectLanguage.setItemCaptionPropertyId("name");
		selectLanguage.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				getUI().setLocale((Locale) event.getProperty().getValue());

			}
		});

		loginButton.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				failedLogins++;
				if (failedLogins == 1) {
					errorMessage.setValue("{login_error_singular}");
				} else {
					errorMessage.setValue("{login_error_plural}");
				}
				errorMessage.markAsDirty();
			}
		});

		errorMessage.setTranslationProvider(new ResourceBundleTranslationProvider("login") {
			@Override
			public String getTranslation(Locale locale, String key) {
				String message = super.getTranslation(locale, key);
				return MessageFormat.format(message, failedLogins);
			}
		});
	}

}
