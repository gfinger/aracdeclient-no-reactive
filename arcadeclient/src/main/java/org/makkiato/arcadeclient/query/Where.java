package org.makkiato.arcadeclient.query;

public class Where {
    private Condition condition;
    private Select from; 

    public Where(Select from, Condition condition) {
        this.from = from;
        this.condition = condition;
    }

    public String toString() {
        return String.format("where %s", condition.toString());
    }
}
