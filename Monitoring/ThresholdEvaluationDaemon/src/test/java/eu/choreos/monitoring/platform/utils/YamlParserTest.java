package eu.choreos.monitoring.platform.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.AbstractThreshold;
import eu.choreos.monitoring.platform.daemon.DoubleThreshold;
import eu.choreos.monitoring.platform.daemon.SingleThreshold;

public class YamlParserTest {
	
	@Before
	public void setUp(){
	}

	@Test
	public void shouldReadSingleThresholds() throws IOException{
		String fileName = ClassLoader.getSystemResource("SingleThresholdEvaluationDaemonConfig.yml").getFile();
		
		SingleThreshold threshold1 = new SingleThreshold("load_one",SingleThreshold.MAX,1);

		List<SingleThreshold> expectedList = new ArrayList<SingleThreshold>();
		expectedList.add(threshold1);
		
		List<AbstractThreshold> thresholds = YamlParser.getThresholdsFromFile(fileName);
		assertEquals(1, thresholds.size());
		
		assertEquals(threshold1, thresholds.get(0));
	}

	@Test
	public void shouldReadTwoThresholds() throws IOException{
		String fileName = ClassLoader.getSystemResource("ThresholdEvaluationDaemonConfig.yml").getFile();

		
		List<AbstractThreshold> expectedList = new ArrayList<AbstractThreshold>();
		
		SingleThreshold threshold1 = new SingleThreshold("load_one",SingleThreshold.MAX,1);
		expectedList.add(threshold1);
		SingleThreshold threshold2 = new SingleThreshold("load_three",SingleThreshold.MIN,0.7);
		expectedList.add(threshold2);
		DoubleThreshold threshold3 = new DoubleThreshold("load_five", DoubleThreshold.BETWEEN, 0.6, 0.7);
		expectedList.add(threshold3);

		List<AbstractThreshold> thresholds = YamlParser.getThresholdsFromFile(fileName);
		assertEquals(expectedList.size(),thresholds.size());
		

		assertEquals(threshold1, thresholds.get(0));

		assertEquals(threshold2, thresholds.get(1));
		
		assertEquals(threshold3, thresholds.get(2));
	}
}
