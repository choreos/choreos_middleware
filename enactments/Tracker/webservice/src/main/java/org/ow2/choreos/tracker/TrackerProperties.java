package org.ow2.choreos.tracker;

import java.util.HashMap;
import java.util.Map;

public class TrackerProperties {

    private static final String NAME_PREFIX = "tracker";
    private static final String BLACK_LP = "TrackerBlackService";
    private static final String WHITE_LP = "TrackerWhiteService";
    private static final Map<TrackerType,String> PACKAGE_URIS = new HashMap<TrackerType,String>();

    static {
        PACKAGE_URIS.put(TrackerType.WHITE, "http://...");
        PACKAGE_URIS.put(TrackerType.BLACK, "http://...");
    }
    
    public String getName(final int trackerNumber) {
        return NAME_PREFIX + trackerNumber;
    }

    public String getEndpoint(final TrackerType type) {
        String endpoint;

        if (TrackerType.WHITE.equals(type)) {
            endpoint = "white";
        } else {
            endpoint = "black";
        }

        return endpoint;
    }

    public TrackerType getType(final int trackerNumber) {
        TrackerType type;
        if (trackerNumber % 5 == 4) {
            type = TrackerType.BLACK;
        } else {
            type = TrackerType.WHITE;
        }

        return type;
    }
    
    public String getPackageUri(final int trackerNumber) {
        TrackerType type = getType(trackerNumber);
        return PACKAGE_URIS.get(type);
    }

    public String getLocalPart(final TrackerType type) {
        String localPart;
        if (TrackerType.WHITE.equals(type)) {
            localPart = WHITE_LP;
        } else {
            localPart = BLACK_LP;
        }

        return localPart;
    }
}
