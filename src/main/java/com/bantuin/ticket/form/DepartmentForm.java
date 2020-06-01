package com.bantuin.ticket.form;

import com.bantuin.ticket.model.Department;
import com.bantuin.ticket.model.User;
import id.co.javan.keboot.core.form.EntityCrudForm;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class DepartmentForm implements EntityCrudForm<Department> {

    private Long id;

    private String name;

    private String description;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    private Set<User> users = new HashSet<>();

    @Override
    public Department toCreateEntity() {
        return null;
    }

    @Override
    public Department toUpdateEntity(Department department) {
        return null;
    }
}
