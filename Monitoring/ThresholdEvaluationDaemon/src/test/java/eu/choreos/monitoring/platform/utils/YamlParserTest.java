package eu.choreos.monitoring.platform.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.Threshold;

public class YamlParserTest {
	
	@Before
	public void setUp(){
	}

	@Test
	public void shouldReadSingleThresholds() throws IOException{
		String fileName = ClassLoader.getSystemResource("SingleThresholdEvaluationDaemonConfig.yml").getFile();
		
		Threshold threshold1 = new Threshold("load_one",Threshold.MAX,1);

		List<Threshold> expectedList = new ArrayList<Threshold>();
		expectedList.add(threshold1);
		
		List<Threshold> thresholds = YamlParser.getThresholdsFromFile(fileName);
		assertEquals(1, thresholds.size());
		
		assertEquals(threshold1, thresholds.get(0));
	}

	@Test
	public void shouldReadTwoThresholds() throws IOException{
		String fileName = ClassLoader.getSystemResource("ThresholdEvaluationDaemonConfig.yml").getFile();

		
		List<Threshold> expectedList = new ArrayList<Threshold>();
		
		Threshold threshold1 = new Threshold("load_one",Threshold.MAX,1);
		expectedList.add(threshold1);
		Threshold threshold2 = new Threshold("load_three",Threshold.MIN,0.7);
		expectedList.add(threshold2);

		List<Threshold> thresholds = YamlParser.getThresholdsFromFile(fileName);
		assertEquals(expectedList.size(),thresholds.size());
		

		assertEquals(threshold1, thresholds.get(0));

		assertEquals(threshold2, thresholds.get(1));
	}
}
