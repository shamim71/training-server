package com.versacomllc.training.emailer;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class EmailerTask implements Runnable {

	protected Emailer emailer;

	protected static final Logger logger = Logger.getLogger(EmailerTask.class);

	public EmailerTask(final Emailer emailer) {
		this.emailer = emailer;
	}

	public void run() {
		try {

			ConfigurationData conf = emailer.getConf();
			HtmlEmail email = new HtmlEmail();
			email.setHostName(conf.getMailServer());
			email.addTo(this.emailer.getTo());
			email.setFrom(conf.getSenderEmail(), "Training Portal Team");
			email.setSubject(this.emailer.getSubject());

			// set the html message
			email.setHtmlMsg(this.emailer.getMessageBody());

			// set the alternative message
			email.setTextMsg("Your email client does not support HTML messages");

			email.setSmtpPort(conf.getPort());
			email.setAuthenticator(new DefaultAuthenticator(conf
					.getSenderUser(), conf.getSenderPass()));
			email.setDebug(false);
			email.setTLS(false);

			email.send();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		}

	}

}
