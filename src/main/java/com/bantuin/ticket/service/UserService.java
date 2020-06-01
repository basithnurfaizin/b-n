package com.bantuin.ticket.service;

import com.bantuin.ticket.model.User;
import org.springframework.data.repository.query.Param;

public interface UserService {

    User getByUsername(@Param("username") String username);

    User save(User user);
}
