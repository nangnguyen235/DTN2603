package com.dtn2603.buoi2.entity.entity;

import com.dtn2603.buoi2.entity.Department;
import com.dtn2603.buoi2.entity.Position;
import com.dtn2603.buoi2.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountId;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 100)
    private String fullname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "position_id")
    private Position position;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private Date createDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
