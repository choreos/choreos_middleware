/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

/**
 * Stores choreography descriptions.
 * 
 * @author leonardo
 * 
 */
public class ChorRegistry {

    private static ChorRegistry instance = new ChorRegistry();

    private Map<String, ChoreographyContext> chorsContexts = new ConcurrentHashMap<String, ChoreographyContext>();
    private AtomicInteger counter = new AtomicInteger();

    private ChorRegistry() {

    }

    public static ChorRegistry getInstance() {
        return instance;
    }

    /**
     * Creates a new choreography entry
     * 
     * @return the just registred choreography ID
     */
    public String create(ChoreographySpec chorSpec) {
        String id = Integer.toString(counter.incrementAndGet());
        Choreography chor = new Choreography();
        chor.setId(id);
        chor.setChoreographySpec(chorSpec);
        chorsContexts.put(id, new ChoreographyContext(chor));
        return id;
    }
    
    public void addChoreography(Choreography chor) {
        if (chorsContexts.containsKey(chor.getId()))
            throw new IllegalArgumentException("Choreography is already on registry");
        else
            chorsContexts.put(chor.getId(), new ChoreographyContext(chor));
    }

    public Choreography getChoreography(String chorId) {
        return chorsContexts.get(chorId).getChoreography();
    }
    
    public ChoreographySpec getRequestedChoreographySpec(String chorId) {
        return chorsContexts.get(chorId).getRequestedChoreographySpec();
    }

    public ChoreographyContext getContext(String chorId) {
        return chorsContexts.get(chorId);
    }

    public boolean contains(String chorId) {
        return chorsContexts.containsKey(chorId);
    }

    public Map<String, Choreography> getAll() {
        Map<String, Choreography> chors = new HashMap<String, Choreography>();
        for (ChoreographyContext ctx : chorsContexts.values()) {
            chors.put(ctx.getChoreography().getId(), ctx.getChoreography());
        }
        return chors;
    }
    
    public void clean() {
        Iterator<String> it = chorsContexts.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            chorsContexts.remove(key);
        }
    }
}