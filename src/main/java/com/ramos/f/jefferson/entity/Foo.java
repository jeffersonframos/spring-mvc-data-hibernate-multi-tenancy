package com.ramos.f.jefferson.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "foo")
public class Foo implements Serializable{
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(generator = "foo_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "foo_id_seq", sequenceName = "foo_id_seq", allocationSize = 1)
    private long id;
    
    @Column(name = "name", nullable = false)
    private String name;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Foo other = (Foo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
