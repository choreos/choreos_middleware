package org.ow2.choreos.tracker;

public class TrackerProperties {

    private static final String NAME_PREFIX = "tracker";
    private static final String BLACK_LP = "TrackerBlackService";
    private static final String WHITE_LP = "TrackerWhiteService";

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
