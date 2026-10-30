import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BattleShipBoard {
    public static final int SIZE = 10;

    private Ship[][] ships;
    private boolean[][] fired;
    private List<Ship> shipList;
    private Random random;

    public BattleShipBoard() {
        random = new Random();
        reset();
    }

    public void reset() {
        ships = new Ship[SIZE][SIZE];
        fired = new boolean[SIZE][SIZE];
        shipList = new ArrayList<>();

        int[] shipSizes = {5, 4, 3, 3, 2};

        for (int size : shipSizes) {
            Ship ship = new Ship(size);

            if (placeShip(ship, size)) {
                shipList.add(ship);
            }
        }
    }

    private boolean placeShip(Ship ship, int size) {
        List<int[]> positions = new ArrayList<>();

        // Generate horizontal placements
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col <= SIZE - size; col++) {
                positions.add(new int[]{row, col, 0});
            }
        }

        // Generate vertical placements
        for (int row = 0; row <= SIZE - size; row++) {
            for (int col = 0; col < SIZE; col++) {
                positions.add(new int[]{row, col, 1});
            }
        }

        Collections.shuffle(positions, random);

        for (int[] position : positions) {
            int row = position[0];
            int col = position[1];
            boolean vertical = position[2] == 1;

            boolean available = true;

            // Check whether all cells are empty
            for (int i = 0; i < size; i++) {
                int r = vertical ? row + i : row;
                int c = vertical ? col : col + i;

                if (ships[r][c] != null) {
                    available = false;
                    break;
                }
            }

            // Place the ship if the cells are available
            if (available) {
                for (int i = 0; i < size; i++) {
                    int r = vertical ? row + i : row;
                    int c = vertical ? col : col + i;

                    ships[r][c] = ship;
                }

                return true;
            }
        }

        return false;
    }

    public ShotResult fire(int row, int col) {
        if (row < 0 || row >= SIZE
                || col < 0 || col >= SIZE
                || fired[row][col]) {
            return ShotResult.ALREADY_FIRED;
        }

        fired[row][col] = true;

        Ship ship = ships[row][col];

        if (ship == null) {
            return ShotResult.MISS;
        }

        ship.hit();

        if (ship.isSunk()) {
            return ShotResult.SUNK;
        }

        return ShotResult.HIT;
    }

    public boolean isFired(int row, int col) {
        return fired[row][col];
    }

    public Ship getShipAt(int row, int col) {
        return ships[row][col];
    }

    public boolean allShipsSunk() {
        for (Ship ship : shipList) {
            if (!ship.isSunk()) {
                return false;
            }
        }

        return true;
    }

    public int getTotalShipCells() {
        int total = 0;

        for (Ship ship : shipList) {
            total += ship.getSize();
        }

        return total;
    }
}
