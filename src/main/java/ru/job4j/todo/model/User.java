package ru.job4j.todo.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    @EqualsAndHashCode.Include
    private String email;
    private String password;
    @Column(name = "user_zone")
    private String timezone;
}
