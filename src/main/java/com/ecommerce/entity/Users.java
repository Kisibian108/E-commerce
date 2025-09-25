package com.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Users extends BaseEntity {

    @Column(name = "user_name")
    String username;
    @Column(name = "pass_word")
    String password;
    String email;
    String role;

}
