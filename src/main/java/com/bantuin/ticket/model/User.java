package com.bantuin.ticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="username",unique=true)
    private String username;
    private String email;
    private String name;
    private String password;
    @Column(name="auth_token")
    private String token;
    private Date created_at;
    private Date deleted_at;
    private Date update_at;

    @JsonIgnoreProperties({ "created_at", "updated_at", "users" })
    @ManyToOne
    @JoinColumn(name="roles_user")
    private Role role;

    @JsonIgnoreProperties({ "created_at", "updated_at", "users" })
    @ManyToOne
    @JoinColumn(name="departments_user")
    private Department department;

}
