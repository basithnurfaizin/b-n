package com.bantuin.ticket.service;

import com.bantuin.ticket.form.DepartmentForm;
import com.bantuin.ticket.model.Department;
import com.bantuin.ticket.repository.DepartmentRepository;
import com.bantuin.ticket.util.ServiceResult;
import id.co.javan.keboot.core.service.impl.EntityCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DepartmentServiceImpl extends EntityCrudServiceImpl<Department, Long , DepartmentForm, DepartmentRepository> implements DepartmentService {

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository){
        this.repository = repository;
    }

    @Override
    public ServiceResult<List<Department>> getAll() {
        List<Department> departments = repository.findAll();

        return new ServiceResult<>(false,"success",departments);
    }

    @Override
    public Specification<Department> getSearchFilter(Object... objects) {
        return null;
    }
}
