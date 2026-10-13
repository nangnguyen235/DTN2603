package com.dtn2603.buoi2.entity;

import com.dtn2603.buoi2.entity.enums.PositionName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PositionName positionName;
}
