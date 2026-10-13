package com.dtn2603.buoi2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(
        name = "group_account",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_group_account", columnNames = {"group_id", "account_id"})
        }
)
public class GroupAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(columnDefinition = "TIMESTAMP")
    @CreationTimestamp 
    private Date joinDate;
}
