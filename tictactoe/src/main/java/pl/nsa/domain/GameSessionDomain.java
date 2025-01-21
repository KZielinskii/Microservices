package pl.nsa.domain;


import jakarta.persistence.*;
import pl.nsa.Difficulty;
import pl.nsa.GameSession;

import java.time.LocalDateTime;
@Entity
public class GameSessionDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String sessionId;
    private String board;
    private Difficulty difficulty;
    private char playerSymbol;
    private char aiSymbol;
    private Long UserId;
    private Integer moveCounter = 0;
    private final LocalDateTime expirationTime;

    public GameSessionDomain() {
        expirationTime = LocalDateTime.now().plusSeconds(300);
    }
    public GameSessionDomain(GameSession gameSession, String sessionId) {
        this.sessionId = sessionId;
        this.setBoard(gameSession.getBoard());
        this.difficulty = gameSession.getDifficulty();
        this.playerSymbol = gameSession.getPlayerSymbol();
        this.aiSymbol = gameSession.getAiSymbol();
        this.UserId = gameSession.getUserId();
        this.moveCounter = gameSession.getMoveCounter();
        this.expirationTime = gameSession.getExpirationTime();
    }

    public GameSessionDomain(GameSessionDomain gameSessionDomain,GameSession gameSession,String sessionId){
        this.sessionId = sessionId;
        this.setBoard(gameSession.getBoard());
        this.difficulty = gameSession.getDifficulty();
        this.playerSymbol = gameSession.getPlayerSymbol();
        this.aiSymbol = gameSession.getAiSymbol();
        this.UserId = gameSession.getUserId();
        this.moveCounter = gameSession.getMoveCounter();
        this.expirationTime = gameSession.getExpirationTime();
        this.id = gameSessionDomain.id;
    }


    public char[][] getBoard() {
        return deserializeBoard(board);
    }

    public void setBoard(char[][] board) {
        this.board = serializeBoard(board);
    }

    private String serializeBoard(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            sb.append(new String(row)).append(";");
        }
        return sb.toString();
    }

    private char[][] deserializeBoard(String board) {
        char[][] result = new char[3][3];
        String[] rows = board.split(";");
        for (int i = 0; i < 3; i++) {
            result[i] = rows[i].toCharArray();
        }
        return result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(char playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public char getAiSymbol() {
        return aiSymbol;
    }

    public void setAiSymbol(char aiSymbol) {
        this.aiSymbol = aiSymbol;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Integer getMoveCounter() {
        return moveCounter;
    }

    public void setMoveCounter(Integer moveCounter) {
        this.moveCounter = moveCounter;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }


    @Override
    public String toString() {
        return "GameSessionDomain{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", board='" + board + '\'' +
                ", difficulty=" + difficulty +
                ", playerSymbol=" + playerSymbol +
                ", aiSymbol=" + aiSymbol +
                ", UserId=" + UserId +
                ", moveCounter=" + moveCounter +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
