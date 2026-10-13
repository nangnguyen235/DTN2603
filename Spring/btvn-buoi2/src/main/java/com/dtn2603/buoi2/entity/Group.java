package com.dtn2603.buoi2.entity;

import com.dtn2603.buoi2.entity.Account;
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
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer groupId;

    @Column(length = 100, nullable = false)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Account creatorId;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private Date createDate;
}
