package org.ow2.choreos.tracker.experiment.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.MultiPartEmail;
import org.ow2.choreos.utils.Configuration;

public class GmailAuthentication {

    private static final String MAIL_CONF_FILE = "email_alert.properties";
    
    private final Configuration mailConf = new Configuration(MAIL_CONF_FILE);
    private final String username, password;
    
    public GmailAuthentication() {
        username = mailConf.get("username");
        password = mailConf.get("password");
    }
    
    public MultiPartEmail getAuthenticatedEmail() {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(username, password));
        email.setSSL(true);
        return email;
    }

}
