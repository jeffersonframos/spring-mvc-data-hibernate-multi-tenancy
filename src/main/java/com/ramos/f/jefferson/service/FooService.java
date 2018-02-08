/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.service;

import com.ramos.f.jefferson.entity.Foo;
import java.util.List;

/**
 *
 * @author jeffe
 */
public interface FooService {
    
    public Foo findOne(long id);
    
    public List<Foo> findAll();
    
}
