package pl.dmcs.domain;

import jakarta.persistence.*;
import pl.dmcs.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PlayerDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String board;

    private boolean isBot;

    private String shipSizes;

    @ElementCollection
    private List<Integer> sunkenShips = new ArrayList<>();

    // Constructors
    public PlayerDomain() {}

    public PlayerDomain(int[][] board, boolean isBot, int[] shipSizes, List<Integer> sunkenShips) {
        this.board = serializeBoard(board);
        this.isBot = isBot;
        this.shipSizes = serializeShipSizes(shipSizes);
        this.sunkenShips = sunkenShips;
    }

    public PlayerDomain(Player player){
        this.board = serializeBoard(player.getBoard());
        this.isBot = player.isBot();
        this.shipSizes = serializeShipSizes(player.getShipSizes());
        this.sunkenShips = player.getSunkenShips();
    }

    public Player toPlayer() {
        Player player = new Player();
        player.setBoard(getBoard());
        player.setBot(isBot);
        player.setShipSizes(getShipSizes());
        player.setSunkenShips(sunkenShips);
        return player;
    }

    public int[] getShipSizes() {
        return deserializeShipSizes(shipSizes);
    }

    public void setShipSizes(int[] shipSizes) {
        this.shipSizes = serializeShipSizes(shipSizes);
    }

    private String serializeShipSizes(int[] shipSizes) {
        StringBuilder sb = new StringBuilder();
        for (int size : shipSizes) {
            sb.append(size).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private int[] deserializeShipSizes(String shipSizes) {
        String[] sizes = shipSizes.split(",");
        int[] result = new int[sizes.length];
        for (int i = 0; i < sizes.length; i++) {
            result[i] = Integer.parseInt(sizes[i]);
        }
        return result;
    }

    public int[][] getBoard() {
        return deserializeBoard(board);
    }

    public void setBoard(int[][] board) {
        this.board = serializeBoard(board);
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

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    public List<Integer> getSunkenShips() {
        return sunkenShips;
    }

    public void setSunkenShips(List<Integer> sunkenShips) {
        this.sunkenShips = sunkenShips;
    }
}
