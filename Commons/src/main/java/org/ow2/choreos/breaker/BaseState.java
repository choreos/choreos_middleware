package org.ow2.choreos.breaker;

public abstract class BaseState<T> implements CircuitBreakerState<T> {

    private String stateName;

    public BaseState(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("rawtypes")
        BaseState other = (BaseState) obj;
        if (stateName == null) {
            if (other.stateName != null)
                return false;
        } else if (!stateName.equals(other.stateName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return stateName;
    }

}
