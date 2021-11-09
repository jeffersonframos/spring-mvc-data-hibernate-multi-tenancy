package com.ramos.f.jefferson.service;

import com.ramos.f.jefferson.entity.Foo;
import com.ramos.f.jefferson.repository.FooRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class FooServiceImpl implements FooService{
    
    private final FooRepository repository;
    
    public FooServiceImpl(FooRepository repository) {
        this.repository = repository;
    }

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
