package com.bantuin.ticket.form;

import com.bantuin.ticket.model.User;
import id.co.javan.keboot.core.form.EntityCrudForm;
import java.util.Date;

public class UserForm implements EntityCrudForm<User> {

    private String username;
    private String password;

    private String email;
    private String name;

    private String token;
    private Date created_at;
    private Date deleted_at;
    private Date update_at;

    private Long roles_user;
    private Integer status;

    private String roles;

    private Long department;

    @Override
    public User toCreateEntity() {
        return null;
    }

    @Override
    public User toUpdateEntity(User user) {
        return null;
    }
}
