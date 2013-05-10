package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class CloudProviderInterceptorTest {
	
	private static final String CP_MOCK_NAME = "MockCloudProvider"; 
	
	@Test
	public void shouldDelegateAllTheRequests() throws Exception {
		
		CloudProvider cpMock = this.getMockCloudProvider();
		CloudProviderInterceptor interceptor = new CloudProviderInterceptor(cpMock);
		
		interceptor.getProviderName();
		verify(cpMock).getProviderName();
		interceptor.getNode("1");
		verify(cpMock).getNode("1");
		interceptor.getNodes();
		verify(cpMock).getNodes();
		interceptor.destroyNode("2");
		verify(cpMock).destroyNode("2");
		interceptor.createNode(null, null);
		verify(cpMock).createNode(null, null);
		interceptor.createOrUseExistingNode(null, null);
		verify(cpMock).createOrUseExistingNode(null, null);
	}
	
	private CloudProvider getMockCloudProvider() throws Exception {
		
		Node node1 = new Node();
		node1.setId("1");
		Node node2 = new Node();
		node1.setId("2");
		
		List<Node> initialNodes = new ArrayList<Node>();
		initialNodes.add(node1);
		
		CloudProvider cpMock = mock(CloudProvider.class);
		when(cpMock.getProviderName()).thenReturn(CP_MOCK_NAME);
		when(cpMock.getNodes()).thenReturn(initialNodes);
		when(cpMock.getNode("1")).thenReturn(node1);
		when(cpMock.createNode(any(Node.class), any(ResourceImpact.class))).thenReturn(node2);
		when(cpMock.createOrUseExistingNode(any(Node.class), any(ResourceImpact.class))).thenReturn(node2);
		
		return cpMock;
	}

}
