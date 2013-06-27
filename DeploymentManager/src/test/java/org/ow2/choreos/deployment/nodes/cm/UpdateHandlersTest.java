package org.ow2.choreos.deployment.nodes.cm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Test;

public class UpdateHandlersTest {

    @Test
    public void shouldRetrieveAddedHandlers() {
        
        UpdateHandler h1 = mock(UpdateHandler.class);
        UpdateHandler h2 = mock(UpdateHandler.class);
        UpdateHandlers handlers = new UpdateHandlers();
        
        handlers.addHandler(h1);
        handlers.addHandler(h2);
        handlers.fetchHandlers();
        List<UpdateHandler> retrievedHandlers = handlers.getHandlersForProcessing();
        
        assertEquals(2, retrievedHandlers.size());
        assertTrue(retrievedHandlers.get(0) == h1);
        assertTrue(retrievedHandlers.get(1) == h2);
    }
    
    @Test
    public void handlersShouldBeEmptyAfterRetrieving() {
        
        UpdateHandler h1 = mock(UpdateHandler.class);
        UpdateHandler h2 = mock(UpdateHandler.class);
        UpdateHandlers handlers = new UpdateHandlers();
        
        handlers.addHandler(h1);
        handlers.addHandler(h2);
        handlers.fetchHandlers();
        handlers.getHandlersForProcessing();
        handlers.fetchHandlers();
        List<UpdateHandler> retrievedHandlers = handlers.getHandlersForProcessing();
        
        assertTrue(retrievedHandlers.isEmpty());
    }
    
    @Test
    public void shouldNotRetrieveWithoutFetch() {
        
        UpdateHandler h1 = mock(UpdateHandler.class);
        UpdateHandler h2 = mock(UpdateHandler.class);
        UpdateHandlers handlers = new UpdateHandlers();
        
        handlers.addHandler(h1);
        handlers.addHandler(h2);
        List<UpdateHandler> retrievedHandlers = handlers.getHandlersForProcessing();
        
        assertTrue(retrievedHandlers.isEmpty());
    }
    
    @Test
    public void shouldNotRetrieveHandlersAddedAfterTheFetch() {
        
        UpdateHandler h1 = mock(UpdateHandler.class);
        UpdateHandler h2 = mock(UpdateHandler.class);
        UpdateHandler h3 = mock(UpdateHandler.class);
        UpdateHandlers handlers = new UpdateHandlers();
        
        handlers.addHandler(h1);
        handlers.addHandler(h2);
        handlers.fetchHandlers();
        handlers.addHandler(h3);
        List<UpdateHandler> retrievedHandlers = handlers.getHandlersForProcessing();
        
        assertEquals(2, retrievedHandlers.size());
        assertTrue(retrievedHandlers.get(0) == h1);
        assertTrue(retrievedHandlers.get(1) == h2);
    }

}
