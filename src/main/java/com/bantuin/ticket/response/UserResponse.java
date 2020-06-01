
package com.bantuin.ticket.response;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class UserResponse {

    private String token;
    private User user;

}
