package com.timerg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "player")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "firstPlayerEntity", fetch = FetchType.LAZY)
    private List<MatchEntity> matchesAsFirstPlayer;

    @OneToMany(mappedBy = "secondPlayerEntity", fetch = FetchType.LAZY)
    private List<MatchEntity> matchesAsSecondPlayer;

    @OneToMany(mappedBy = "winner", fetch = FetchType.LAZY)
    private List<MatchEntity> matchesAsWinnerPlayer;
}
