package org.example.design.chain;

public abstract class Hande {
    protected Hande next;

    public Hande setNext(Hande next) {
        this.next = next;
        return next;
    }

    public void handle(String username, String password) {
        process(username, password);
    }

    protected abstract void process(String username, String password);
}