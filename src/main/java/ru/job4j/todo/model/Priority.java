package ru.job4j.todo.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "priorities")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
public class Priority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private int position;
}
