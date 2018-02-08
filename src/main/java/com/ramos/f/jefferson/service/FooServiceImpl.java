/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.service;

import com.ramos.f.jefferson.entity.Foo;
import com.ramos.f.jefferson.repository.FooRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jeffe
 */
@Service
public class FooServiceImpl implements FooService{
    
    @Autowired
    private FooRepository repository;

    @Override
    public Foo findOne(long id) {
        Optional<Foo> optional = repository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public List<Foo> findAll() {
        return repository.findAll();
    }
    
}
