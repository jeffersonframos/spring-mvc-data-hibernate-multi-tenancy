package com.ramos.f.jefferson.service;

import com.ramos.f.jefferson.entity.Foo;
import java.util.List;

public interface FooService {
    
    public Foo findOne(long id);
    
    public List<Foo> findAll();
    
}
