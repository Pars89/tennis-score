package com.timerg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "match")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_player_id")
    private PlayerEntity firstPlayerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_player_id")
    private PlayerEntity secondPlayerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_player_id")
    private PlayerEntity winner;

}
