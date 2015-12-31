# i18npoc
*Proof-of-concept of on-the-fly localization of Vaadin applications.*

The purpose of this PoC is sole to 
1) demonstrate that it is technically possible to do on-the-fly localization of Vaadin apps
2) get a feeling of the workflow for an approach like this

This PoC is *not* in any way production read nor ready to be used as such. I've paid absolutely no attention to making clean code, performance or any other aspects than to "make it work".

This is *not an add-on*. The code is *not packaged* in any way. This just *just* a proof-of-concept.

## How to use it
1) Instead of having natural language in, for example, captions, use translations keys instead. For example, textField.setCaption("{username}")

2) Create a properties file containing the translations.

3) Apply a TranslationProvider to the UI or any component in the component hierachy. A TranslationProvider is responsible for mapping the key to an actual localized message. ResourceBundleTranslationProvider is the default implementation that uses properties files for the localized messages.

Vaadin uses the TranslationProvider and automatically replaces the key's with the localized messages, on-the-fly without the developer having to do any additional work.

## Technical description

### Concepts
TranslationProvider - an interface that defines means of fetching localized messages. 
ResourceBundleTranslationProvider - Default implementation for the TranslationProvider that uses ResourceBundles as the source for translated messages

### How it works
In Vaadin's communication layer, all messages are parsed and search for translation keys, just before the content is transformed into JSON output. If a translation key is found, then the TranslationProvider attached to the component being processed is requested for the actual localized message. In other words, component states are never modified, even if you change the language. The components contain only the keys, keys are searched & replaced just before the JSON HTTP response.

You can attach a TranslationProvider to any component (defined in the Component interface). If a component doesn't have a TranslationProvider defined, one is looked from the component's parent. Thus, it is enough that you define a TranslationProvider to the UI and override the default provider when needed.

## Known limitations
Parameterized messages are often context dependent. In these situations you should define a custom TranslationProvider for components that have localized messages with parameters. LoginView demonstrates how to do this. This approach has one draw-back: when just the parameter is updated, the component's state doesn't change, thus, the message with the new parameter is not sent to the browser. In order to get the localized message updated in the browser with the new parameter value, we will have to manually trigger an update of the state. In my example case, it can be seen in the ClickListener in the LoginView, where I manually call errorMessage.markAsDirty();