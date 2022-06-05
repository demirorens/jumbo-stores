package com.jumbo.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document("user")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String firstname;

    private String lastname;

    private String username;

    private String password;

    @Indexed(unique = true)
    private String email;

    @DBRef
    private Set<Role> roles;


}
