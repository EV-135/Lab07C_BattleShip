public class Game {
    private BattleShipBoard board;

    private int missCounter;
    private int strikeCounter;
    private int totalMisses;
    private int totalHits;

    private boolean playing;

    public Game() {
        board = new BattleShipBoard();
        reset();
    }

    public ShotResult fire(int row, int col) {
        if (!playing) {
            return ShotResult.ALREADY_FIRED;
        }

        ShotResult result = board.fire(row, col);

        if (result == ShotResult.ALREADY_FIRED) {
            return result;
        }

        if (result == ShotResult.MISS) {
            missCounter++;
            totalMisses++;

            if (missCounter == 5) {
                strikeCounter++;
                missCounter = 0;
            }

            if (strikeCounter == 3) {
                playing = false;
                return ShotResult.LOSS;
            }

            return ShotResult.MISS;
        }

        // A successful hit
        totalHits++;
        missCounter = 0;

        if (result == ShotResult.SUNK) {
            if (board.allShipsSunk()) {
                playing = false;
                return ShotResult.WIN;
            }

            return ShotResult.SUNK;
        }

        return ShotResult.HIT;
    }

    public void reset() {
        board.reset();

        missCounter = 0;
        strikeCounter = 0;
        totalMisses = 0;
        totalHits = 0;

        playing = true;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public int getStrikeCounter() {
        return strikeCounter;
    }

    public int getTotalMisses() {
        return totalMisses;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public boolean isPlaying() {
        return playing;
    }

    public BattleShipBoard getBoard() {
        return board;
    }
}
