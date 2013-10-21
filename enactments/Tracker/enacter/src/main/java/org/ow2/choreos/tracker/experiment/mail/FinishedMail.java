package org.ow2.choreos.tracker.experiment.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.ow2.choreos.utils.Configuration;

public class FinishedMail {

    private static final String MAIL_CONF_FILE = "email_alert.properties";
    
    private final Configuration mailConf = new Configuration(MAIL_CONF_FILE);
    private final String username, to;
    
    public FinishedMail() {
        this.username = mailConf.get("username");
        this.to = mailConf.get("to");
    }
    
    public void send() throws EmailException {
        GmailAuthentication authenticator = new GmailAuthentication();
        MultiPartEmail email = authenticator.getAuthenticatedEmail();
        email.setFrom(username + "@gmail.com");
        email.setSubject("ScalabilityExperiment executions finished!");
        email.setMsg("=)");
        email.addTo(to);
        email.send();
    }

}
