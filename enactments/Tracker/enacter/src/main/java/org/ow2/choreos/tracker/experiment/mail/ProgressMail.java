package org.ow2.choreos.tracker.experiment.mail;

import java.util.Arrays;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.ow2.choreos.tracker.experiment.ExperimentDefinition;
import org.ow2.choreos.tracker.experiment.ScalabilityExperiment;
import org.ow2.choreos.utils.Configuration;

public class ProgressMail {

    private static final String MAIL_CONF_FILE = "email_alert.properties";
    
    private final Configuration mailConf = new Configuration(MAIL_CONF_FILE);
    private final ExperimentDefinition experimentDefinition;
    private final String username, to;
    
    public ProgressMail(ExperimentDefinition experimentDefinition) {
        this.experimentDefinition = experimentDefinition;
        this.username = mailConf.get("username");
        this.to = mailConf.get("to");
    }

    public void send() throws EmailException {
        GmailAuthentication authenticator = new GmailAuthentication();
        MultiPartEmail email = authenticator.getAuthenticatedEmail();
        email.setFrom(username + "@gmail.com");
        email.setSubject("ScalabilityExperiment progress");
        String msg = getMessage();
        email.setMsg(msg);
        email.addTo(to);
        EmailAttachment attachment = getAttachment();
        email.attach(attachment);
        email.send();
    }
    
    private EmailAttachment getAttachment() {
        int qty = experimentDefinition.getChorsQty();
        int size = experimentDefinition.getChorsSize();
        int run = experimentDefinition.getRun();
        int vm = experimentDefinition.getVmLimit();
        String path = "results/chor" + qty + "x" + size + "_" + vm + "vms_" + run + "run.txt";
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        return attachment;
    }
    
    private String getMessage() {
        StringBuilder message = new StringBuilder("Execution completed: " + experimentDefinition + "\n");
        message.append("\nExperiment paramaters:\n");
        message.append("NUM_EXECUTIONS = " + ScalabilityExperiment.NUM_EXECUTIONS + "\n");
        message.append("CHORS_SIZES = " + Arrays.toString(ScalabilityExperiment.CHORS_SIZES) + "\n");
        message.append("VMS_LIMITS = " + Arrays.toString(ScalabilityExperiment.VMS_LIMITS) + "\n");
        return message.toString();
    }
    
}
