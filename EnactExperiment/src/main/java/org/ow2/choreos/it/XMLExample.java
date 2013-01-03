package org.ow2.choreos.it;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.ChorServiceSpec;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.ServiceDependency;
import org.ow2.choreos.servicedeployer.datamodel.ArtifactType;

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
	private static ChorServiceSpec wsSpec(String name, int port) {
		
		ChorServiceSpec spec = new ChorServiceSpec();
		spec.setName(name);
		spec.setCodeUri("http://choreos.eu/registry/" + name + ".jar");
		spec.setPort(port);
		spec.setEndpointName(name);
		spec.setArtifactType(ArtifactType.COMMAND_LINE);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static ChorServiceSpec legacySpec(String name, int port, String ip) {
		
		ChorServiceSpec spec = new ChorServiceSpec();
		spec.setName(name);
		spec.setCodeUri("http://" + ip + ":" + port + "/" + name + "/");
		spec.setPort(port);
		spec.setEndpointName(name);
		spec.setArtifactType(ArtifactType.LEGACY);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static ChorServiceSpec cdSpec(String name) {
		
		ChorServiceSpec spec = new ChorServiceSpec();
		spec.setName(name);
		spec.setCodeUri("http://choreos.eu/registry/" + name + ".tar.gz");
		spec.setEndpointName(name);
		spec.setArtifactType(ArtifactType.EASY_ESB);
		spec.getRoles().add(name);
		return spec;
	}
	
	private static Map<String, ChorServiceSpec> generateSpecs() {
		
		Map<String, ChorServiceSpec> specsMap = new HashMap<String, ChorServiceSpec>();
		
		int port = 1234;
		int machine = 101;
		
		for (String name: SERVICES) {
			String ip = "192.168.56." + (machine++);
			ChorServiceSpec spec = legacySpec(name, port++, ip);
			specsMap.put(name, spec);
		}
		
		for (String name: CDS) {
			ChorServiceSpec spec = cdSpec(name);
			specsMap.put(name, spec);
		}
		
		return specsMap;
	}
	
	private static void wireDependencies(Map<String, ChorServiceSpec> specsMap) {

		for (String[] dependency: DEPENDENCIES) {
			
			String client = dependency[0];
			String supplier = dependency[1];
			ServiceDependency serviceDependency = new ServiceDependency();
			serviceDependency.setServiceName(supplier);
			serviceDependency.setServiceRole(supplier);			
			ChorServiceSpec clientSpec = specsMap.get(client);
			clientSpec.getDependencies().add(serviceDependency);
		}
	}
	
	public static void main(String[] args) {
		
		Map<String, ChorServiceSpec> specsMap = generateSpecs();
		wireDependencies(specsMap);
		
		List<ChorServiceSpec> specs = new ArrayList<ChorServiceSpec>(specsMap.values());
		ChorSpec chorSpec = new ChorSpec();
		chorSpec.setServiceSpecs(specs);

		System.out.println(chorSpec);
		
		ChoreographyDeployer ee = new ChorDeployerClient("http://localhost:9102/enactmentengine");
		ee.createChoreography(chorSpec); // here we invoke, and after we get the XML with WireShark
	}

}
