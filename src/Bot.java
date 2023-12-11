import java.util.Random;

public class Bot {


    private  static  int[] lastRow= new int[1];
    private static int[] lastCol= new int[1];

    public static boolean easyBot(int[][] playerShipLocations, String[][]PlayerSunkCheck) {

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

    public static boolean MediumBot(int[][] playerShipLocations,boolean hitOrMiss, String[][]PlayerSunkCheck) {
        Random random = new Random();
        int row, col;

        if (!hitOrMiss) {
            row = random.nextInt(9);
            col = random.nextInt(9);

            while (playerShipLocations[row][col] == 2 || playerShipLocations[row][col] == 3) {
                row = random.nextInt(9);
                col = random.nextInt(9);
            }
            lastRow[0]= row;
            lastCol[0]= col;

            if (playerShipLocations[row][col] == 1) {
                playerShipLocations[row][col] = 2;
                PlayerSunkCheck[row][col] = PlayerSunkCheck[row][col] + "S";
                hitOrMiss = true; // Hit
            } else {
                playerShipLocations[row][col] = 3;
                hitOrMiss = false; // Miss
            }
        } else {


        }

        return hitOrMiss;
    }

    public static boolean HardBot(int[][] playerShipLocations,boolean hitOrMiss, String[][]PlayerSunkCheck) {


        return false;
    }

}

