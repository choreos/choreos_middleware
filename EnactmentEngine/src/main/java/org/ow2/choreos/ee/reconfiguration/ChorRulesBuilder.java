package org.ow2.choreos.ee.reconfiguration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;

public class ChorRulesBuilder {

    private static final String CHOR_RULES_TEMPLATE = "rules/chor_rules_template";
    private static final String CHOR_RULES_XML_TEMPLATE = "rules/SLA_violations_template.xml";

    private ServicesRuleBuilder servicesRuleBuilder;

    private Logger logger = Logger.getLogger("reconfLogger");

    private static final String CHOR_RULES_PLACEHOLDER = "@{chor_rules}";
    private static final String CHOR_ID_PLACEHOLDER = "@{chor_id}";
    private static final String SERVICES_RULES_PLACEHOLDER = "@{services_rules}";

    public ChorRulesBuilder() {
	servicesRuleBuilder = new ServicesRuleBuilder();
    }

    public String assemblyGlimpseRules(Choreography choreography) {
	StringBuffer bf = new StringBuffer();
	String chorRules = assemblyChorRules(choreography);

	try {
	    bf = bf.append(FileUtils.readFileToString(new File(getClass().getClassLoader()
		    .getResource(CHOR_RULES_XML_TEMPLATE).getFile())));
	} catch (IOException e) {
	    logger.error("Could not open chor rules xml template file");
	    return bf.toString();
	}

	String glimpseRules = bf.toString().replace(CHOR_RULES_PLACEHOLDER, chorRules);
	return glimpseRules;
    }

    private String assemblyChorRules(Choreography choreography) {
	StringBuffer bf = new StringBuffer();
	String servicesRules = servicesRuleBuilder.assemblyRules(choreography.getDeployableServices(),
		choreography.getId());

	try {
	    bf = bf.append(FileUtils.readFileToString(new File(getClass().getClassLoader()
		    .getResource(CHOR_RULES_TEMPLATE).getFile())));
	} catch (IOException e) {
	    logger.error("Could not open chor rules template file");
	    return bf.toString();
	}

	String chorRules = bf.toString().replace(CHOR_ID_PLACEHOLDER, choreography.getId())
		.replace(SERVICES_RULES_PLACEHOLDER, servicesRules);

	return chorRules;
    }

}
