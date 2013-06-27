package org.ow2.choreos.deployment.nodes.cm;

import java.util.ArrayList;
import java.util.List;

public class UpdateHandlers {

    private List<UpdateHandler> waitingHandlers = new ArrayList<UpdateHandler>();
    private List<UpdateHandler> handlersForProcessing = new ArrayList<UpdateHandler>();
    
    /**
     * Adds a handler in wait state
     * 
     * @param handler
     */
    public void addHandler(UpdateHandler handler) {
        waitingHandlers.add(handler);
    }

    /**
     * Fetches all the waiting handlers, so they can be processed
     */
    public void fetchHandlers() {
        handlersForProcessing.addAll(waitingHandlers);
        waitingHandlers = new ArrayList<UpdateHandler>();
    }

    /**
     * Retrieves and removes previously fetched handlers
     * 
     * @return
     */
    public List<UpdateHandler> getHandlersForProcessing() {
        List<UpdateHandler> result = handlersForProcessing;
        handlersForProcessing = new ArrayList<UpdateHandler>();
        return result;
    }

}
