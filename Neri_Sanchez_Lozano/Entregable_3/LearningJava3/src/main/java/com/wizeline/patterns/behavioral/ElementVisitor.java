package com.wizeline.patterns.behavioral;

import java.util.List;

import com.wizeline.patterns.behavioral.elements.Element;
import com.wizeline.patterns.behavioral.elements.JsonElement;
import com.wizeline.patterns.behavioral.elements.XmlElement;

public class ElementVisitor implements Visitor {

    @Override
    public void visit(XmlElement xe) {
        System.out.println(
                "processing an XML element with uuid: " + xe.uuid);
    }

    @Override
    public void visit(JsonElement je) {
        System.out.println(
                "processing a JSON element with uuid: " + je.uuid);
    }

    @Override
    public void visit(List<Element> elements) {
        for (Element element : elements) {
            element.accept(this);
        }
    }

}
