package com.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Roles extends BaseEntity {

    String name;

    @ManyToMany(mappedBy = "roles")
    Set<Users> users = new HashSet<>();
}
