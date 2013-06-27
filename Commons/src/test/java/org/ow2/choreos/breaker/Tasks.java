package org.ow2.choreos.breaker;

import java.util.concurrent.Callable;

public class Tasks {

    public static final String RESPONSE = "OK";

    public static Callable<String> goodTask() {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return RESPONSE;
            }
        };
        return task;
    }

    public static Callable<String> badTask() {
        Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                throw new Exception();
            }
        };
        return task;
    }

}
