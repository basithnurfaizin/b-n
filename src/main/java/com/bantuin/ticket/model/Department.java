package com.bantuin.ticket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="departments")
public class Department {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    @CreatedDate
    @Column(name="created_at")
    private Date createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private Date updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Set<User> users = new HashSet<>();
}
