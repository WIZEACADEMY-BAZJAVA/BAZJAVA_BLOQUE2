package com.wizeline.patterns.behavioral.elements;

import com.wizeline.patterns.behavioral.Visitor;

public abstract class Element {

    public String uuid;

    public Element(String uuid) {
        this.uuid = uuid;
    }

    public abstract void accept(Visitor v);
}
