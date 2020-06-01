package com.bantuin.ticket.controller;

import com.bantuin.ticket.form.DepartmentForm;
import com.bantuin.ticket.model.Department;
import com.bantuin.ticket.service.DepartmentService;
import com.bantuin.ticket.util.ServiceResult;
import id.co.javan.keboot.core.controller.EntityCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentController extends EntityCrudController<Department, Long, DepartmentForm> {

    @Autowired
    protected DepartmentService service;

    @GetMapping(value = "/api/department")
    public ServiceResult<List<Department>> index() {
        return  service.getAll();
    }
}
