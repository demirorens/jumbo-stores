package com.jumbo.store.payload;


import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
}
