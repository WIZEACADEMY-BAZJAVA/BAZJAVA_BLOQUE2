package com.wizeline.patterns.behavioral.elements;

import com.wizeline.patterns.behavioral.Visitor;

public class JsonElement extends Element {

    public JsonElement(String uuid) {
        super(uuid);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
