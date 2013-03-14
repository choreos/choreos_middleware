package org.ow2.choreos.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceDependency;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

public class XMLExample {
	
	private static final String[] SERVICES = new String[] { "Airport", "ATC",
			"StandAndGateManagement", "AirpotBusCompany",
			"AmenityProvider", "AirportSpeakerActuatorsAggregator",
			"AirportInfraredSensorsAggregator",
			"AirportNoiseSensorsAggregator", "SecurityCompany",
			"LuggageHandlingCompany" 
	};
	
	// obs: CD-ISA was pictured in the slide as "CD-ATC", but there was already another CD-ATC
	private static final String[] CDS = new String[] { "CD-AIR-SGM", "CD-SGM-AIR",
			"CD-AIR-ABC", "CD-ABS-AIR", "CD-AIR-AP", "CD-AIR-ASAA",
			"CD-ISA", "CD-AIR-ANSA", "CD-SC", "CD-AIR-SC", "CD-AIR-LHC",
			"CD-LHC", "CD-ATC"
	};
	
	private static final String[][] DEPENDENCIES = new String[][] { 
		/* client, supplier */
		{"Airport", "CD-AIR-SGM"}, 
		{"Airport", "CD-AIR-ABC"}, 
		{"Airport", "CD-AIR-AP"}, 
		{"Airport", "CD-AIR-ASAA"}, 
		{"Airport", "CD-AIR-ANSA"}, 
		{"Airport", "CD-AIR-SC"}, 
		{"Airport", "CD-AIR-LHC"},
		{"ATC", "CD-ATC"}, 		
		{"StandAndGateManagement", "CD-SGM-AIR"}, 
		{"AirpotBusCompany", "CD-ABS-AIR"},
		{"AirportInfraredSensorsAggregator", "CD-ISA"}, 
		{"SecurityCompany", "CD-SC"}, 
		{"LuggageHandlingCompany", "CD-LHC"}, 
		/*
		{"CD-AIR-SGM", "StandAndGateManagement"}, 
		{"CD-SGM-AIR", "Airport"},
		{"CD-AIR-ABC", "AirpotBusCompany"},
		{"CD-ABS-AIR", "Airport"},
		{"CD-AIR-AP", "AmenityProvider"},
		{"CD-AIR-ASAA", "AirportSpeakerActuatorsAggregator"},
		{"CD-ISA", "Airport"},
		{"CD-AIR-ANSA", "AirportNoiseSensorsAggregator"},
		{"CD-SC", "Airport"},
		{"CD-AIR-SC", "SecurityCompany"},
		{"CD-AIR-LHC", "LuggageHandlingCompany"},
		{"CD-LHC", "Airport"},
		{"CD-ATC", "Airport"},
		*/
	};

	@SuppressWarnings("unused")
	private static ServiceSpec wsSpec(String name, int port) {
		
		ServiceSpec spec = new ServiceSpec();
		spec.setName(name);
		spec.setPackageUri("http://choreos.eu/registry/" + name + ".jar");
		spec.setPort(port);
		spec.setEndpointName(name);
		spec.setPackageType(PackageType.COMMAND_LINE);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static ServiceSpec legacySpec(String name, int port, String ip) {
		
		ServiceSpec spec = new ServiceSpec();
		spec.setName(name);
		spec.setPackageUri("http://" + ip + ":" + port + "/" + name + "/");
		spec.setPort(port);
		spec.setEndpointName(name);
		spec.setPackageType(PackageType.LEGACY);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static ServiceSpec cdSpec(String name) {
		
		ServiceSpec spec = new ServiceSpec();
		spec.setName(name);
		spec.setPackageUri("http://choreos.eu/registry/" + name + ".tar.gz");
		spec.setEndpointName(name);
		spec.setPackageType(PackageType.EASY_ESB);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static Map<String, ServiceSpec> generateSpecs() {
		
		Map<String, ServiceSpec> specsMap = new HashMap<String, ServiceSpec>();
		
		int port = 1234;
		int machine = 101;
		
		for (String name: SERVICES) {
			String ip = "192.168.56." + (machine++);
			ServiceSpec spec = legacySpec(name, port++, ip);
			specsMap.put(name, spec);
		}
		
		for (String name: CDS) {
			ServiceSpec spec = cdSpec(name);
			specsMap.put(name, spec);
		}
		
		return specsMap;
	}
	
	private static void wireDependencies(Map<String, ServiceSpec> specsMap) {

		for (String[] dependency: DEPENDENCIES) {
			
			String client = dependency[0];
			String supplier = dependency[1];
			ServiceDependency serviceDependency = new ServiceDependency();
			serviceDependency.setServiceName(supplier);
			serviceDependency.setServiceRole(supplier);			
			ServiceSpec clientSpec = specsMap.get(client);
			clientSpec.getDependencies().add(serviceDependency);
		}
	}
	
	public static void main(String[] args) {
		
		Map<String, ServiceSpec> specsMap = generateSpecs();
		wireDependencies(specsMap);
		
		List<ServiceSpec> specs = new ArrayList<ServiceSpec>(specsMap.values());
		ChorSpec chorSpec = new ChorSpec();
		chorSpec.setServiceSpecs(specs);

		System.out.println(chorSpec);
		
		ChoreographyDeployer ee = new ChorDeployerClient("http://localhost:9102/enactmentengine");
		ee.createChoreography(chorSpec); // here we invoke, and after we get the XML with WireShark
	}

}
