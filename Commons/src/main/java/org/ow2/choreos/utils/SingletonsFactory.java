package org.ow2.choreos.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class SingletonsFactory<T> {

    private Configuration classMap;
    private Map<String, T> singletons = new ConcurrentHashMap<String, T>();

    private Logger logger = Logger.getLogger(SingletonsFactory.class);

    /**
     * @param classMap
     *            Configuration whose keys are "commercial names" and the values
     *            are the respective full qualified class names.
     */
    public SingletonsFactory(Configuration classMap) {
        this.classMap = classMap;
    }

    /**
     * 
     * @param type
     * @return
     * @throws IllegalArgumentException
     *             if could not retrieve singleton
     */
    public T getInstance(String type) {
        if (!singletons.containsKey(type)) {
            synchronized (SingletonsFactory.class) {
                if (!singletons.containsKey(type)) {
                    T singleton = newInstance(type);
                    singletons.put(type, singleton);
                }
            }
        }
        return singletons.get(type);
    }

    private T newInstance(String type) {
        String className = this.classMap.get(type);
        T singleton = null;
        try {
            @SuppressWarnings("unchecked")
            // catches handle the problem
            Class<T> clazz = (Class<T>) Class.forName(className);
            singleton = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            singletonCreationFailed(type);
        } catch (InstantiationException e) {
            singletonCreationFailed(type);
        } catch (IllegalAccessException e) {
            singletonCreationFailed(type);
        } catch (ClassCastException e) {
            singletonCreationFailed(type);
        }
        return singleton;
    }

    private void singletonCreationFailed(String type) {
        logger.error("invalid type: " + type);
        throw new IllegalArgumentException();
    }

}
