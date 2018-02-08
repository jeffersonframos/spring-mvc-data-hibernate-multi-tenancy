/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ramos.f.jefferson.entity.Foo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author jeffe
 */
public class FooDTO {
    
    @JsonProperty("id")
    private long id;
    
    @JsonProperty("name")
    private String name;

    @Autowired
    public FooDTO() {
    }
    
    public FooDTO(Foo foo) {
        this.id = foo.getId();
        this.name = foo.getName();
    }

    public FooDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
