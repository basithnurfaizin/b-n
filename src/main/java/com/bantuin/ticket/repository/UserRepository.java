package com.bantuin.ticket.repository;

import com.bantuin.ticket.model.User;
import id.co.javan.keboot.core.repository.EntityCrudRepository;

public interface UserRepository extends EntityCrudRepository<User, Long> {
    User findByUsername(String username);
}
