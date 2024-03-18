package org.makkiato.arcadeclient.query;

public class Select {
    public static Select SelectFrom(String document) {
        return new Select(document);
    }

    private String document;
    private Where where;


    public Select(String document) {
        this.document = document;
    }

    public Select Where(Condition condition) {
        where = new Where(this, condition);
        return this;
    }

    public String toString() {
        return String.format("select from %s %s", document, where.toString());
    }
}
