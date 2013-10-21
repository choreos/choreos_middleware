package org.ow2.choreos.invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvokerHistory {

    private static InvokerHistory INSTANCE = new InvokerHistory();

    public static InvokerHistory getInstance() {
        return INSTANCE;
    }

    // key is taskName and value is a list of times in seconds
    private Map<String, ArrayList<Long>> history = new HashMap<String, ArrayList<Long>>();

    public List<Long> getHistory(String taskName) {
        createIfMissing(taskName);
        return history.get(taskName);
    }

    public void addTime(String taskName, long timeInSeconds) {
        createIfMissing(taskName);
        history.get(taskName).add(timeInSeconds);
    }

    private void createIfMissing(String taskName) {
        if (!history.containsKey(taskName))
            history.put(taskName, new ArrayList<Long>());
    }

    public void printHistory() {
        for (String taskName : history.keySet())
            System.out.println(taskName + ": " + history.get(taskName));
    }

    public void clear() {
        history = new HashMap<String, ArrayList<Long>>();
    }

}
