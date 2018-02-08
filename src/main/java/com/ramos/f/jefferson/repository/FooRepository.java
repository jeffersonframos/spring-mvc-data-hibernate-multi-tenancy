/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramos.f.jefferson.repository;

import com.ramos.f.jefferson.entity.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jeffe
 */
public interface FooRepository extends JpaRepository<Foo, Long> {
    
}
