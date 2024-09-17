package com.wizeline.patterns.behavioral;

import java.util.List;

import com.wizeline.patterns.behavioral.elements.Element;
import com.wizeline.patterns.behavioral.elements.JsonElement;
import com.wizeline.patterns.behavioral.elements.XmlElement;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);

    void visit(List<Element> elements);
}
