package com.bantuin.ticket.service;

import com.bantuin.ticket.model.Department;
import com.bantuin.ticket.util.ServiceResult;

import java.util.List;

public interface DepartmentService {

    ServiceResult<List<Department>> getAll();
}
