package org.ow2.choreos.tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;

public class TrackerInfo {

    public static final String ROLE = "tracker";
    private static final String SERVICE_NAME_PRE = "tracker";
    private transient Choreography chor = null;

    public void setChoreography(final Choreography chor) {
        this.chor = chor;
    }

    public String getName(final int trackerNumber) {
        return SERVICE_NAME_PRE + trackerNumber;
    }

    public String getWsdl(final int trackerNumber) {
        if (chor == null) {
            throw new IllegalStateException("Choreography not set.");
        }

        final String wanted = getName(trackerNumber);
        final List<DeployableService> chorServices = chor.getDeployableServices();
        String current;

        if (chorServices != null) {
            for (DeployableService chorService : chorServices) {
                current = chorService.getSpec().getName();
                if (wanted.equals(current)) {
                    return chorService.getUris().get(0) + "?wsdl";
                }
            }
        }

        throw new NoSuchElementException("Tracker not found: " + wanted);
    }

    public boolean isAnswerCorrect(final String answer) {
        return isAnswerCorrect(answer, 0);
    }

    public boolean isAnswerCorrect(final String answer, final int trackerNumber) {
        boolean isCorrect = true;
        final List<Integer> numbers = getAnswerAsIntegers(answer);

        for (int i = 0; i < numbers.size(); i += 6) {
            if (!isGroupAnswerCorrect(numbers, i)) {
                isCorrect = false;
                break;
            }
        }

        return isCorrect;
    }

    private List<Integer> getAnswerAsIntegers(final String answer) {
        final String[] numberStrings = answer.split(" ");
        final List<Integer> numbers = new ArrayList<Integer>(numberStrings.length);

        for (String numberString : numberStrings) {
            numbers.add(Integer.parseInt(numberString));
        }

        return numbers;
    }

    private boolean isGroupAnswerCorrect(final List<Integer> numbers, final int firstOfGroupIndex) {
        final int firstOfGroup = firstOfGroupIndex - (firstOfGroupIndex / 5);
        return (numbers.get(firstOfGroupIndex) == firstOfGroup
                && numbers.get(firstOfGroupIndex + 1) == firstOfGroup + 1
                && numbers.get(firstOfGroupIndex + 2) == firstOfGroup + 2
                && numbers.get(firstOfGroupIndex + 3) == firstOfGroup + 3
                && numbers.get(firstOfGroupIndex + 4) == firstOfGroup + 4 && numbers.get(firstOfGroupIndex + 5) == firstOfGroup + 3);
    }
}
