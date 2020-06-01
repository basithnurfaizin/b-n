package com.bantuin.ticket.service.impl;

import com.bantuin.ticket.form.UserForm;
import com.bantuin.ticket.model.User;
import com.bantuin.ticket.repository.UserRepository;
import com.bantuin.ticket.service.UserService;
import id.co.javan.keboot.core.service.impl.EntityCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends EntityCrudServiceImpl<User, Long, UserForm, UserRepository>  implements UserService {


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public Specification<User> getSearchFilter(Object... objects) {
        return null;
    }


    @Override
    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
