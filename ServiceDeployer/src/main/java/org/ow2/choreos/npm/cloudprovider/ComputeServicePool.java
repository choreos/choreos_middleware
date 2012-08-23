package org.ow2.choreos.npm.cloudprovider;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.ow2.choreos.servicedeployer.Configuration;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;

public class ComputeServicePool {
	
	public static final int MAX_COMPUTE_SERVICES = 3;
	private static final String PROVIDER = "aws-ec2";

	private static ComputeServicePool pool = new ComputeServicePool();
	
	private List<ComputeService> computeServices = new ArrayList<ComputeService>();
	private AtomicInteger counter = new AtomicInteger();

	private ComputeService next(String image) {
		
		int idx = counter.getAndIncrement();
		idx = idx % MAX_COMPUTE_SERVICES;
		if (idx > computeServices.size()-1) {
			ComputeService cs = createInstance(image);
			computeServices.add(cs);
		}
		ComputeService selected = computeServices.get(idx);
		return selected;
	}
	
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
		
		return pool.next(image);
	}

}
