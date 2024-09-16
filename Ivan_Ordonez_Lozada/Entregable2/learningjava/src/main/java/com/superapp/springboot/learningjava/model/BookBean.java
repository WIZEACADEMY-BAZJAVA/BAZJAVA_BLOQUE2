package com.superapp.springboot.learningjava.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement(name = "book")
@XmlType(propOrder = {"id", "name", "date"})
@XmlSeeAlso({BookBean.class})
public class BookBean implements Serializable {

    private static final long serialVersionUID = -8967927368021920536L;
    
    private Long id;
    private String name;
    private String autor;
    private Date date;

    public BookBean(Long id, String name, String autor, Date date) {
        this.id = id;
        this.name = name;
        this.autor = autor;
        this.date = date;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}