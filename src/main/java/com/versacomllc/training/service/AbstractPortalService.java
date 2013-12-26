package com.versacomllc.training.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.versacomllc.training.emailer.ConfigurationData;
import com.versacomllc.training.emailer.EmailTemplate;
import com.versacomllc.training.emailer.Emailer;
import com.versacomllc.training.emailer.EmailerTask;
import com.versacomllc.training.util.TaskExecutionPool;

/**
 * Define the service interface for ....
 * 
 * @author Shamim Ahmmed
 * 
 */
public abstract class AbstractPortalService {

	protected final Logger logger = Logger.getLogger(getClass());

	@Autowired
	private Properties appProperties;

	@Autowired
	private ServletContext context;

	protected Locale locale;

    /**
     * Return key value form resource bundle
     * @param key
     * @return key value
     */
    public String getText(String key) {
        String message;

        try {
            message = getResourceBundle(null).getString(key);
        } catch (java.util.MissingResourceException mre) {
            logger.warn("Missing key for '" + key + "'");

            return "???" + key + "???";
        }

        return message;
    }

    /**
     * Return key value from resource bundle with specified
     * format
     * @param key
     * @param arg
     * @return the key value.
     */
    public String getText(String key, Object arg) {
        if (arg == null) {
            return getResourceBundle(null).getString(key);
        }

        MessageFormat form = new MessageFormat(getResourceBundle(null).getString(key));

        if (arg instanceof String) {
            return form.format(new Object[] { arg });
        } else if (arg instanceof Object[]) {
            return form.format(arg);
        } else {
            logger.error("arg '" + arg + "' not String or Object[]");

            return "";
        }
    }
    
	protected ResourceBundle getResourceBundle(final String language) {
		if (language == null || language.isEmpty()) {
			locale = new Locale("en");
		} else {
			locale = new Locale(language);
		}
		ResourceBundle bundle = ResourceBundle.getBundle("message", locale);
		return bundle;
	}

	protected ConfigurationData getEmailConfiguration() {

		final String user = (String) appProperties.get("email.user");
		final String password = (String) appProperties.get("email.password");
		final String email = (String) appProperties.get("email.address");
		final String subject = (String) appProperties.get("email.subject");
		final String greeting = (String) appProperties.get("email.greeting");
		final String server = (String) appProperties.get("email.server");
		final String port = (String) appProperties.get("email.port");
		ConfigurationData conf = new ConfigurationData();
		conf.setGreeting(greeting);
		conf.setSubject(subject);
		conf.setSenderEmail(email);
		conf.setSenderPass(password);
		conf.setSenderUser(user);
		conf.setMailServer(server);
		try {
			conf.setPort(Integer.parseInt(port));
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		return conf;
	}

	protected void sendEmail(String email, String emailSubject, String body,
			String name) throws Exception {
		String templateName = "email_template_basic.vm";

		Map<String, String> templateValues = new HashMap<String, String>();
		templateValues.put("app_link",
				appProperties.getProperty("portal.home.link"));
		templateValues.put("app_link_label",
				appProperties.getProperty("portal.home.label"));
		templateValues.put("member_name", name);
		templateValues.put("message_body_text", body);
		EmailTemplate template = new EmailTemplate(templateName,
				templateValues, this.context);
		String msgBody = template.generate(); //

		/** Send email notification */
		Emailer emailer = new Emailer(email, msgBody, emailSubject,
				getEmailConfiguration());
		Runnable task = new EmailerTask(emailer);
		TaskExecutionPool pool = TaskExecutionPool.getInstance();
		pool.addTaskToPool(task);

	}
}
