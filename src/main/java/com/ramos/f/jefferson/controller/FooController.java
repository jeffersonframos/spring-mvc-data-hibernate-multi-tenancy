package com.ramos.f.jefferson.controller;

import com.ramos.f.jefferson.entity.Foo;
import com.ramos.f.jefferson.exception.ResourceNotFounException;
import com.ramos.f.jefferson.service.FooService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Secured("ROLE_ADMIN")
public class FooController {
    
    private final FooService fooService;
    
    public FooController(FooService fooService) {
        this.fooService = fooService;
    }
    
    @GetMapping
    public ResponseEntity<?> getAllFoo(){
        return new ResponseEntity<>(fooService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoo(@PathVariable("id") String stringId) throws ResourceNotFounException{
        try{
            long id = Long.parseLong(stringId);
            Foo foo = fooService.findOne(id);
            if(foo == null){
                throw new ResourceNotFounException("Foo not found");
            }
            return new ResponseEntity<>(foo, HttpStatus.OK);
        } catch (NumberFormatException ex) {
            throw new ResourceNotFounException("Invalid ID: must be a number");
        }
    }
    
}
