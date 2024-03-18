package org.makkiato.arcadeclient.query;

public class And extends Condition {
    private Condition left;
    private Condition right;

    public And(Condition left, Condition right) {
        this.left = left;
        this.right = right;
    }

    public String toString() {
        return String.format("(%s and %s)", left.toString(), right.toString());
    }
}
