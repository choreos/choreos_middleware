package org.ow2.choreos.npm.cloudprovider;

import java.util.Properties;

import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.ow2.choreos.servicedeployer.Configuration;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

// the pool didn't work, so it is now a simple factory 
public class ComputeServicePool {
	
	private static final String PROVIDER = "aws-ec2";

	private static ComputeServicePool pool = new ComputeServicePool();
	
	private ComputeService createInstance(String image) {
		
		Properties overrides = new Properties();
		overrides.setProperty(AWSEC2Constants.PROPERTY_EC2_AMI_QUERY,
				"image-id=" + image);
		
		ComputeServiceContext context = new ComputeServiceContextFactory()
		.createContext(PROVIDER,
				Configuration.get("AMAZON_ACCESS_KEY_ID"),
				Configuration.get("AMAZON_SECRET_KEY"),
				ImmutableSet.<Module> of(), overrides);
		
		return context.getComputeService();
	}

	
	public static ComputeService getComputeService(String image) {
		
		return pool.createInstance(image);
	}

}
