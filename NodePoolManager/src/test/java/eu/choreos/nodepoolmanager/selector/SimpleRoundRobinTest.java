package eu.choreos.nodepoolmanager.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

public class SimpleRoundRobinTest {

	CloudProvider mockedProvider;
	SimpleRoundRobinSelector selector;
	private Node responseNode;

	@Before
	public void setUp() throws RunNodesException {

		mockedProvider = Mockito.mock(CloudProvider.class);

		responseNode = new Node();
		responseNode.setChefName("chefNameResponse");
		responseNode.setHostname("hostnameResponse");

		List<Node> previouslyExistingNodes = new ArrayList<Node>();
		Node node1 = new Node();
		Node node2 = new Node();
		previouslyExistingNodes.add(node1);
		previouslyExistingNodes.add(node2);

		when(mockedProvider.createNode(any(Node.class))).thenReturn(
				responseNode);

		when(mockedProvider.getNodes()).thenReturn(previouslyExistingNodes);

		selector = new SimpleRoundRobinSelector(mockedProvider, 2);
	}

	@Test
	public void constructorMethodShouldntCreateNewNodesIfRequestedNodePoolIsLessThanExistingNodes() {
		SimpleRoundRobinSelector localSelector = new SimpleRoundRobinSelector(
				mockedProvider, 1);
		assertEquals(2, localSelector.getAvailableNodes().size());
	}

	@Test
	public void constructorMethodShouldCreateNewNodesIfRequestedNodePoolIsGreaterThanExistingNodes() {
		SimpleRoundRobinSelector localSelector = new SimpleRoundRobinSelector(
				mockedProvider, 3);
		assertEquals(3, localSelector.getAvailableNodes().size());
	}

	@Test
	public void createInstantiatedNodePoolShouldIncreaseTheNodePoolSize() {
		assertEquals(2, selector.getAvailableNodes().size());

		selector.createInstantiatedNodePool(4, mockedProvider);

		assertEquals(4, selector.getAvailableNodes().size());
	}

	@Test
	public void getNextIndexShouldReturnEveryIndexAndThenReturnToZero() {

		int nodePoolSize = selector.getAvailableNodes().size();
		assertEquals(2, nodePoolSize);

		testEveryIndexAndThenReturnToZero(2);
	}

	private void testEveryIndexAndThenReturnToZero(int size) {

		int nextIndex;
		
		nextIndex = selector.getNextIndex();
		for (int i = nextIndex; i < size; i++) {
			assertEquals("Index was wrong.", i, nextIndex);
			nextIndex = selector.getNextIndex();
		}
		
		assertEquals("Next item did not return to ZERO.", 0, nextIndex);

	}

	@Test
	public void shouldIncreaseNodePoolSizeUponRequest() {
		int nodePoolSize = selector.getAvailableNodes().size();
		assertEquals(2, nodePoolSize);

		testEveryIndexAndThenReturnToZero(2);
		
		selector.changeNodePoolSize(4);
		
		nodePoolSize = selector.getAvailableNodes().size();
		assertEquals(4, nodePoolSize);

		testEveryIndexAndThenReturnToZero(4);
	}

	@Test
	public void testSelectNode() {
		int previousIndex = selector.getNextIndex();
		
		assertEquals(selector.getAvailableNodes().get(previousIndex + 1), selector.selectNode(new Config()));
	}
}
