package org.example.buoi1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id // khoa chinh
    @Column(name = "department_id") //mappinng voi cot department_id o db
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int id;

    @Column(name = "department_name", nullable = false, length = 100) // not null, do dai =100
    private String name;

    @Column(name = "quantity_account")
    private int quantity;
}
