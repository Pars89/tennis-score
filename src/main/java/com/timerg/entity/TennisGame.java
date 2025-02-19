package com.timerg.entity;


import com.timerg.dto.PlayerReadDto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TennisGame {
    private PlayerReadDto firstPlayer;
    private PlayerReadDto secondPlayer;
    private PlayerReadDto winnerPlayer;
    private int firstPoint;
    private int secondPoint;
    private int firstGames;
    private int secondGames;
    private int firstSets;
    private int secondSets;
    @Builder.Default
    StatusGame statusGame = StatusGame.ONGOING;
    public void addPointFirstPlayer() {
        firstPoint += 1;
        checkGames();
    }
    public void addPointSecondPlayer() {
        secondPoint += 1;
        checkGames();
    }
    private void checkGames() {
        if (firstPoint == 4 && secondPoint <= 2 || (firstPoint - secondPoint == 2 && secondPoint >= 3)) {
            firstGames += 1;
            checkSets();
        } else if (secondPoint == 4 && firstPoint <= 2 || (secondPoint - firstPoint == 2 && firstPoint >= 3)) {
            secondGames += 1;
            checkSets();
        }
    }

    private void checkSets() {
        firstPoint = 0;
        secondPoint = 0;
        if (firstGames == 6 && secondGames <= 4 || firstGames == 7 && secondGames == 5) {
            firstSets += 1;
            checkWinner();
        } else if (secondGames == 6 && firstGames <= 4 || secondGames == 7 && firstGames == 5) {
            secondSets += 1;
            checkWinner();
        } else if (firstGames >= 6 && secondGames >= 6) {
            if (firstGames - secondGames == 2) {
                firstSets += 1;
                checkWinner();
            } else if (secondGames - firstGames == 2) {
                secondSets += 1;
                checkWinner();
            }
        }
    }

    private void checkWinner() {
        firstGames = 0;
        secondGames = 0;
        if (firstSets == 2) {
            statusGame = StatusGame.FIRST_WINNER;
            winnerPlayer = firstPlayer;
        } else if (secondSets == 2) {
            statusGame = StatusGame.SECOND_WINNER;
            winnerPlayer = secondPlayer;
        }
    }
}
