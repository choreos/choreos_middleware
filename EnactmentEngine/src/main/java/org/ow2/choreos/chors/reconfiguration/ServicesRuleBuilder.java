package org.ow2.choreos.chors.reconfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.qos.DesiredQoS;
import org.ow2.choreos.services.datamodel.qos.ResponseTimeMetric;

public class ServicesRuleBuilder {

    private static final String SERVICE_RULE_TEMPLATE = "rules/service_rule_template";

    private Logger logger = Logger.getLogger("reconfLogger");

    private static final String SERVICE_ID_PLACEHOLDER = "@{service_id}";
    private static final String CHOR_ID_PLACEHOLDER = "@{chor_id}";
    private static final String ACCEPTABLE_PERCENTAGE_RESPONSE_TIME_PLACEHOLDER = "@{acceptable_precentage}";
    private static final String MAX_RESPONSE_TIME_PLACEHOLDER = "@{max_response_time}";
    private static final String MAX_CPU_USAGE_PLACEHOLDER = "@{max_cpu_usage}";
    private static final String MIN_CPU_USAGE_PLACEHOLDER = "@{min_cpu_usage}";

    public String assemblyRules(List<DeployableService> deployableServices, String chorID) {
	StringBuffer fileContent = new StringBuffer();

	for (DeployableService service : deployableServices) {
	    String assemblyServiceRule = assemblyServiceRule(service, chorID);
	    fileContent.append(assemblyServiceRule);
	    if (!assemblyServiceRule.isEmpty())
		fileContent.append("\n\n");
	}

	return fileContent.toString();
    }

    private String assemblyServiceRule(DeployableService service, String chorID) {
	StringBuffer serviceRuleTemplate = new StringBuffer();

	try {
	    serviceRuleTemplate = serviceRuleTemplate.append(FileUtils.readFileToString(new File(getClass()
		    .getClassLoader().getResource(SERVICE_RULE_TEMPLATE).getFile())));
	} catch (IOException e) {
	    logger.error("Could not open service rule template file");
	    return "";
	}
	DeployableServiceSpec spec = service.getSpec();
	DesiredQoS serviceDesiredQoS = spec.getDesiredQoS();
	if (serviceDesiredQoS == null)
	    return "";
	ResponseTimeMetric responseTimeMetric = serviceDesiredQoS.getResponseTimeMetric();
	if (responseTimeMetric == null)
	    return "";

	StringBuffer serviceRule = new StringBuffer();

	for (ServiceInstance instance : service.getInstances()) {

	    StringBuffer tmp = new StringBuffer(serviceRuleTemplate);

	    String instanceRule = tmp
		    .toString()
		    .replace(SERVICE_ID_PLACEHOLDER, instance.getInstanceId())
		    .replace(CHOR_ID_PLACEHOLDER, chorID)
		    .replace(ACCEPTABLE_PERCENTAGE_RESPONSE_TIME_PLACEHOLDER,
			    "" + responseTimeMetric.getAcceptablePercentage())
		    .replace(MAX_RESPONSE_TIME_PLACEHOLDER, "" + responseTimeMetric.getMaxDesiredResponseTime())
		    .replace(MAX_CPU_USAGE_PLACEHOLDER, "" + serviceDesiredQoS.getResourceLimits().getMaxCPUUsage())
		    .replace(MIN_CPU_USAGE_PLACEHOLDER, "" + serviceDesiredQoS.getResourceLimits().getMinCPUUsage());

	    serviceRule.append(instanceRule);

	}

	return serviceRule.toString();
    }
}
