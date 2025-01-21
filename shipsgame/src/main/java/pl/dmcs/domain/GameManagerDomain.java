package pl.dmcs.domain;

import jakarta.persistence.*;
import pl.dmcs.GameManager;

import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
public class GameManagerDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionId;

    private String difficulty;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerDomain human;

    @OneToOne(cascade = CascadeType.ALL)
    private PlayerDomain ai;

    @Column(columnDefinition = "TEXT")
    private String human_board;

    @Column(columnDefinition = "TEXT")
    private String ai_board;

    private final LocalDateTime expirationTime;


    // Constructors
    public GameManagerDomain() {
        this.expirationTime = LocalDateTime.now().plusSeconds(300);
    }

    public GameManagerDomain(String sessionId, String difficulty, PlayerDomain human, PlayerDomain ai) {
        this.sessionId = sessionId;
        this.difficulty = difficulty;
        this.human = human;
        this.ai = ai;
        this.expirationTime = LocalDateTime.now().plusSeconds(300);
    }

    public GameManagerDomain(GameManagerDomain gameManagerDomain, GameManager gameManager, String sessionId) {
        this.id = gameManagerDomain.getId();
        this.difficulty = gameManager.getDifficulty();
        this.human = new PlayerDomain(gameManager.getHuman());
        this.ai = new PlayerDomain(gameManager.getAi());
        this.human_board = serializeBoard(gameManager.getHuman_board());
        this.ai_board = serializeBoard(gameManager.getAi_board());
        this.expirationTime = LocalDateTime.now().plusSeconds(300);
        this.sessionId = sessionId;
    }

    public GameManagerDomain(GameManager gameManager, String sessionId) {
        this.sessionId = sessionId;
        this.difficulty = gameManager.getDifficulty();
        this.human = new PlayerDomain(gameManager.getHuman());
        this.ai = new PlayerDomain(gameManager.getAi());
        this.human_board = serializeBoard(gameManager.getHuman_board());
        this.ai_board = serializeBoard(gameManager.getAi_board());
        this.expirationTime = LocalDateTime.now().plusSeconds(300);
    }

    public GameManager toGameManager() {
        GameManager gameManager = new GameManager();
        gameManager.setDifficulty(difficulty);
        gameManager.setHuman(human.toPlayer());
        gameManager.setAi(ai.toPlayer());
        gameManager.setHuman_board(getHuman_board());
        gameManager.setAi_board(getAi_board());
        return gameManager;
    }

    public int[][] getHuman_board() {
        return deserializeBoard(human_board);
    }

    public void setHuman_board(int[][] human_board) {
        this.human_board = serializeBoard(human_board);
    }

    public int[][] getAi_board() {
        return deserializeBoard(ai_board);
    }

    public void setAi_board(int[][] ai_board) {
        this.ai_board = serializeBoard(ai_board);
    }

    private String serializeBoard(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                sb.append(cell).append(",");
            }
            sb.deleteCharAt(sb.length() - 1).append(";");
        }
        return sb.toString();
    }

    private int[][] deserializeBoard(String board) {
        String[] rows = board.split(";");
        int[][] result = new int[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split(",");
            result[i] = new int[cells.length];
            for (int j = 0; j < cells.length; j++) {
                result[i][j] = Integer.parseInt(cells[j]);
            }
        }
        return result;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public PlayerDomain getHuman() {
        return human;
    }

    public void setHuman(PlayerDomain human) {
        this.human = human;
    }

    public PlayerDomain getAi() {
        return ai;
    }

    public void setAi(PlayerDomain ai) {
        this.ai = ai;
    }


}