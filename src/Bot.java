import java.util.Random;

public class Bot {

        private enum State {
        SEARCH, TARGET
    }

    private static State state = State.SEARCH;
    private static int lastHitRow = -1;
    private static int lastHitCol = -1;
    private static int targetDirection = 0; // 0: Right, 1: Down, 2: Left, 3: Up

    public static boolean easyBot(int[][] playerShipLocations, String[][] PlayerSunkCheck) {

        Random random = new Random();

        int row, col;

        row = random.nextInt(9);
        col = random.nextInt(9);

        while (playerShipLocations[row][col] == 2 || playerShipLocations[row][col] == 3) {
            row = random.nextInt(9);
            col = random.nextInt(9);
        }

        if (playerShipLocations[row][col] == 1) {
            playerShipLocations[row][col] = 2;
            PlayerSunkCheck[row][col] = PlayerSunkCheck[row][col] + "S";
            return true; // Hit
        } else {
            playerShipLocations[row][col] = 3;
            return false; // Miss
        }
    }

    public static boolean MediumBot(int[][] playerShipLocations, String[][] PlayerSunkCheck) {
        Random random = new Random();
        int row, col;
        boolean hit;

        if (state == State.SEARCH) {
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
            } while (playerShipLocations[row][col] >= 2);

            if (playerShipLocations[row][col] == 1) {
                state = State.TARGET;
                lastHitRow = row;
                lastHitCol = col;
                playerShipLocations[row][col] = 2;
                PlayerSunkCheck[row][col] += "S";
                return true; // Hit
            } else {
                playerShipLocations[row][col] = 3;
                return false; // Miss
            }
        } else {
            // In TARGET mode
            hit = false;
            int[] nextTarget = getNextTarget(lastHitRow, lastHitCol);
            row = nextTarget[0];
            col = nextTarget[1];

            if (row >= 0 && row < 9 && col >= 0 && col < 9) {
                if (playerShipLocations[row][col] == 1) {
                    playerShipLocations[row][col] = 2;
                    PlayerSunkCheck[row][col] += "S";
                    lastHitRow = row;
                    lastHitCol = col;
                    return true; // Hit
                } else {
                    playerShipLocations[row][col] = 3;
                    state = State.SEARCH;
                    targetDirection = (targetDirection + 1) % 4;
                    return false; // Miss
                }
            } else {
                state = State.SEARCH;
                targetDirection = (targetDirection + 1) % 4;
                return false;
            }
        }
    }

    private static int[] getNextTarget(int row, int col) {
        switch (targetDirection) {
            case 0:
                return new int[]{row, col + 1}; // Right
            case 1:
                return new int[]{row + 1, col}; // Down
            case 2:
                return new int[]{row, col - 1}; // Left
            case 3:
                return new int[]{row - 1, col}; // Up
            default:
                return new int[]{row, col};
        }
    }


    public static boolean HardBot(int[][] playerShipLocations, String[][] PlayerSunkCheck) {


        return false;
    }

}

