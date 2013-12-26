package com.versacomllc.training.emailer;

public class Emailer {

	 /** email subject*/
    private String subject;
    private String messageBody;
    private String to;
    private String contentType;

    private ConfigurationData conf;
    
    public Emailer(String to, String messageBody, String subject, ConfigurationData conf){
        this.to = to;
        this.subject = subject;
        this.messageBody = messageBody;
        this.contentType = "text/plain";
        this.conf = conf;
    }

	public String getSubject() {
		return subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public String getTo() {
		return to;
	}

	public String getContentType() {
		return contentType;
	}

	public ConfigurationData getConf() {
		return conf;
	}

}
