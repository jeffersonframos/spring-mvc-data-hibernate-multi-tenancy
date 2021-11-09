package com.ramos.f.jefferson.repository;

import com.ramos.f.jefferson.entity.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {
    
}
