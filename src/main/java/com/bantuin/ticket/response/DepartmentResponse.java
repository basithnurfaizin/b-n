package com.bantuin.ticket.response;

import lombok.Data;
import java.util.Date;

@Data
public class DepartmentResponse {

    private Long id;

    private String name;

    private String description;

    private Date createdAt;

    private Date updatedAt;

}
