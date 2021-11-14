package com.ramos.f.jefferson.dto;

import com.ramos.f.jefferson.entity.User;
import java.util.List;

public class UserDTO {
    private long id;
    private String username;
    private String tenant;
    private List<String> roles;

    public UserDTO() {
    }
    
    public UserDTO(User user, List<String> roles) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.tenant = user.getTenant();
        this.roles = roles;
    }

    public UserDTO(long id, String username, String tenant, List<String> roles) {
        this.id = id;
        this.username = username;
        this.tenant = tenant;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTenant() {
        return tenant;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
