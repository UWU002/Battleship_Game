import java.util.Scanner;
import java.util.Random;

public class Main {
    public static final String CReset = "\u001B[0m";
    public static final String CGreen = "\u001B[32m";
    public static final String CRed = "\u001B[31m";


    public static void main(String[] args) {
        // Game Settings
        String player1Name, player2Name;
        int gameMode;
        int[][] playerShipLocations = ArrayStartups();
        int[][] enemyShipLocations = ArrayStartups();
        String[][] playerSunkCheck = Array2Startups();
        String[][] enemySunkCheck = Array2Startups();
        int[] hits;
        int roundCounter = 0, playerRecevedHits = 0, enemyRecevedHits = 0;
        int botDifficulty;
        //Game logic
        boolean isHit;

        introduction();
        tutorial();
        gameMode = gameMode();
        botDifficulty = botDifficulty(gameMode);
        player1Name = definePlayerName(gameMode, roundCounter);
        autoOrManual(playerShipLocations, enemyShipLocations, roundCounter, playerSunkCheck, enemySunkCheck);
        roundCounter++;
        player2Name = definePlayerName(gameMode, roundCounter);
        checkGamemode(playerShipLocations, enemyShipLocations, roundCounter, gameMode, playerSunkCheck, enemySunkCheck);
        roundCounter++;

        do {
            whoShootsMsg(roundCounter, player1Name, player2Name);
            DisplayEnemyBoard(playerShipLocations, enemyShipLocations, roundCounter, player1Name, player2Name);
            isHit = Shoot(roundCounter, playerShipLocations, playerSunkCheck, enemySunkCheck, enemyShipLocations, gameMode, botDifficulty);
            checkForSunk(playerShipLocations, enemyShipLocations, playerSunkCheck, enemySunkCheck, roundCounter);
            DisplayOwnBoard(playerShipLocations, enemyShipLocations, roundCounter);
            infoMessage();
            roundCounter = roundCounter(isHit, roundCounter);
            hits = checkHits(playerShipLocations, enemyShipLocations, playerRecevedHits, enemyRecevedHits, roundCounter);
            playerRecevedHits = hits[0];
            enemyRecevedHits = hits[1];
        } while (playerRecevedHits < 30 && enemyRecevedHits < 30);

        whoWon(playerRecevedHits, player1Name, player2Name);
    }

    private static int roundCounter(boolean isHit, int roundCounter) {
        if (!isHit) {
            return roundCounter + 1;
        } else {
            return roundCounter;
        }
    }


    private static void infoMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter when you're ready");
        scanner.nextLine();
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private static void checkForSunk(int[][] playerShipLocations, int[][] enemyShipLocations, String[][] playerSunkCheck, String[][] enemySunkCheck, int roundCounter) {
        for (int num = 0; num <= 9; num++) {
            boolean numberFound = false;

            for (String[] row : enemySunkCheck) {
                for (String element : row) {
                    if (element != null && element.equals(String.valueOf(num))) {
                        numberFound = true;
                        break;
                    }
                }
                if (numberFound) {
                    break;
                }
            }
            if (!numberFound) {
                String searchString = num + "S";
                for (int i = 0; i < enemySunkCheck.length; i++) {
                    for (int j = 0; j < enemySunkCheck[i].length; j++) {
                        if (enemySunkCheck[i][j] != null && enemySunkCheck[i][j].equals(searchString)) {
                            enemyShipLocations[i][j] = 5;
                        }
                    }
                }
            }
        }
        for (int num = 0; num <= 9; num++) {
            boolean numberFound = false;

            for (String[] row : playerSunkCheck) {
                for (String element : row) {
                    if (element != null && element.equals(String.valueOf(num))) {
                        numberFound = true;
                        break;
                    }
                }
                if (numberFound) {
                    break;
                }
            }
            if (!numberFound) {
                String searchString = num + "S";
                for (int i = 0; i < playerSunkCheck.length; i++) {
                    for (int j = 0; j < playerSunkCheck[i].length; j++) {
                        if (playerSunkCheck[i][j] != null && playerSunkCheck[i][j].equals(searchString)) {
                            playerShipLocations[i][j] = 5;
                        }
                    }
                }
            }
        }


    }


    private static void whoWon(int playerRecevedHits, String player1Name, String player2Name) {
        if (playerRecevedHits < 30) {
            System.out.println("All Ships Sunk!!");
            System.out.println(player1Name + " won!");

        } else {
            System.out.println("All Ships Sunk!!");
            System.out.println(player2Name + " won!");
        }

    }

    // Game Initialization
    private static void introduction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to start the Game");
        scanner.nextLine();
    }

    private static void tutorial() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        char userSelection;
        System.out.println("Would you like to read the tutorial?");
        while (!exit) {
            userSelection = scanner.next().toLowerCase().charAt(0);
            if (userSelection == 'y') {
                System.out.println("Welcome to Battleship! In this game, you play against another player or a computer. Begin by entering your name. You can choose to manually place your ships on the board or let the game do it automatically. The gameplay involves taking turns to guess the location of your opponent's ships by specifying a row and column. You'll get feedback on whether it's a hit or a miss. The first to sink all of the opponent's ships wins. Remember to watch for patterns in your opponent's ship placement and adjust your strategy. Enjoy the game!l");
                exit = true;
            } else if (userSelection == 'n') {
                System.out.println("The game will Start now");
                exit = true;
            } else {
                System.out.println("Please just type [yes] or [no]");
            }
        }
    }

    private static int gameMode() {
        Scanner scanner = new Scanner(System.in);
        int gameMode = 0;
        boolean exit = false;
        char userSelection;
        System.out.println("Would you like to play against a robot[R] or another player[P]?");
        while (!exit) {
            userSelection = scanner.next().toLowerCase().charAt(0);
            if (userSelection == 'r') {
                gameMode = 1;
                exit = true;
            } else if (userSelection == 'p') {
                gameMode = 2;
                exit = true;
            } else {
                System.out.println("Please just type [R] for the robot or [P] for another player");
            }
        }
        return gameMode;
    }

    private static int botDifficulty(int gameMode) {
        Scanner scanner = new Scanner(System.in);
        int botDifficulty = 0;
        if (gameMode == 1) {
            System.out.println("What difficulty would you like the bot to be?");
            System.out.println("Easy[1]");
            System.out.println("Medium[2]");
            System.out.println("Hard[3]");

            botDifficulty = scanner.nextInt();
            while (!(botDifficulty == 1 | botDifficulty == 2 | botDifficulty == 3)) {
                System.out.println("Please just type [1] for Easy, [2] for Medium and [3] Hard");
                botDifficulty = scanner.nextInt();
            }
        }
        return botDifficulty;
    }

    private static String definePlayerName(int gameMode, int roundCounter) {
        Scanner scanner = new Scanner(System.in);
        if (gameMode == 2) {
            if (roundCounter % 2 == 0) {
                System.out.println("Player 1, define your name for this game");
            } else {
                System.out.println("Player 2, define your name for this game");
            }
        } else {
            if (roundCounter % 2 == 0) {
                System.out.println("Define your name for this Game");
            } else {
                System.out.println("Define the Bot's name fot this Game");
            }
        }
        return scanner.nextLine();
    }

    private static int[][] ArrayStartups() {
        return new int[10][10];
    }

    private static String[][] Array2Startups() {
        return new String[10][10];
    }


    // Ship Placement
    private static void checkGamemode(int[][] PlayerShips, int[][] enemyShipLocations, int roundCounter, int gameMode, String[][] playerSunkCheck, String[][] enemySunkCheck) {
        if (gameMode == 2) {
            autoOrManual(PlayerShips, enemyShipLocations, roundCounter, playerSunkCheck, enemySunkCheck);
        } else {
            AutomaticPlacement(PlayerShips, enemyShipLocations, roundCounter, playerSunkCheck, enemySunkCheck);
        }
    }

    private static void autoOrManual(int[][] PlayerShips, int[][] enemyShipLocations, int roundCounter, String[][] playerSunkCheck, String[][] enemySunkCheck) {
        Scanner scanner = new Scanner(System.in);
        char manualOrRandom;
        System.out.println("Would you like to place your ships manually or randomly?");
        manualOrRandom = scanner.next().toLowerCase().charAt(0);
        while (manualOrRandom != 'm' && manualOrRandom != 'r') {
            System.out.println("Please just type [M] or [R]");
            manualOrRandom = scanner.next().toLowerCase().charAt(0);
        }
        if (manualOrRandom == 'm') {
            ManualPlacement(PlayerShips, enemyShipLocations, roundCounter, playerSunkCheck, enemySunkCheck);
        } else {
            AutomaticPlacement(PlayerShips, enemyShipLocations, roundCounter, playerSunkCheck, enemySunkCheck);
        }
    }

    private static void AutomaticPlacement(int[][] PlayerShips, int[][] enemyShipLocations, int roundCounter, String[][] playerSunkCheck, String[][] enemySunkCheck) {
        Scanner scanner = new Scanner(System.in);
        int[] shipLengths = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        Random random = new Random();
        int row, col, tryCount = 0, nextShip = 1;
        boolean placed;

        for (int length : shipLengths) {
            placed = false;
            while (!placed) {
                char direction = random.nextBoolean() ? 'h' : 'v';
                row = random.nextInt(10);
                col = random.nextInt(10);
                tryCount++;
                if (tryCount > 400) {
                    if (roundCounter % 2 == 0) {
                        PlayerShips = new int[10][10];
                    } else {
                        enemyShipLocations = new int[10][10];
                    }
                }

                if (placementConfirmation(row, col, direction, length, PlayerShips, enemyShipLocations, roundCounter)) {
                    if (roundCounter % 2 == 0) {
                        for (int j = 0; j < length; j++) {
                            if (direction == 'v') {
                                PlayerShips[row + j][col] = 1;
                                switch (nextShip) {
                                    case 1:
                                        playerSunkCheck[row + j][col] = "1";
                                        break;
                                    case 2:
                                        playerSunkCheck[row + j][col] = "2";
                                        break;
                                    case 3:
                                        playerSunkCheck[row + j][col] = "3";
                                        break;
                                    case 4:
                                        playerSunkCheck[row + j][col] = "4";
                                        break;
                                    case 5:
                                        playerSunkCheck[row + j][col] = "5";
                                        break;
                                    case 6:
                                        playerSunkCheck[row + j][col] = "6";
                                        break;
                                    case 7:
                                        playerSunkCheck[row + j][col] = "7";
                                        break;
                                    case 8:
                                        playerSunkCheck[row + j][col] = "8";
                                        break;
                                    case 9:
                                        playerSunkCheck[row + j][col] = "9";
                                        break;
                                    case 10:
                                        playerSunkCheck[row + j][col] = "0";
                                        break;
                                }
                            } else {
                                PlayerShips[row][col + j] = 1;
                                switch (nextShip) {
                                    case 1:
                                        playerSunkCheck[row][col + j] = "1";
                                        break;
                                    case 2:
                                        playerSunkCheck[row][col + j] = "2";
                                        break;
                                    case 3:
                                        playerSunkCheck[row][col + j] = "3";
                                        break;
                                    case 4:
                                        playerSunkCheck[row][col + j] = "4";
                                        break;
                                    case 5:
                                        playerSunkCheck[row][col + j] = "5";
                                        break;
                                    case 6:
                                        playerSunkCheck[row][col + j] = "6";
                                        break;
                                    case 7:
                                        playerSunkCheck[row][col + j] = "7";
                                        break;
                                    case 8:
                                        playerSunkCheck[row][col + j] = "8";
                                        break;
                                    case 9:
                                        playerSunkCheck[row][col + j] = "9";
                                        break;
                                    case 10:
                                        playerSunkCheck[row][col + j] = "0";
                                        break;
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < length; j++) {
                            if (direction == 'v') {
                                enemyShipLocations[row + j][col] = 1;
                                switch (nextShip) {
                                    case 1:
                                        enemySunkCheck[row + j][col] = "1";
                                        break;
                                    case 2:
                                        enemySunkCheck[row + j][col] = "2";
                                        break;
                                    case 3:
                                        enemySunkCheck[row + j][col] = "3";
                                        break;
                                    case 4:
                                        enemySunkCheck[row + j][col] = "4";
                                        break;
                                    case 5:
                                        enemySunkCheck[row + j][col] = "5";
                                        break;
                                    case 6:
                                        enemySunkCheck[row + j][col] = "6";
                                        break;
                                    case 7:
                                        enemySunkCheck[row + j][col] = "7";
                                        break;
                                    case 8:
                                        enemySunkCheck[row + j][col] = "8";
                                        break;
                                    case 9:
                                        enemySunkCheck[row + j][col] = "9";
                                        break;
                                    case 10:
                                        enemySunkCheck[row + j][col] = "0";
                                        break;
                                }
                            } else {
                                enemyShipLocations[row][col + j] = 1;
                                switch (nextShip) {
                                    case 1:
                                        enemySunkCheck[row][col + j] = "1";
                                        break;
                                    case 2:
                                        enemySunkCheck[row][col + j] = "2";
                                        break;
                                    case 3:
                                        enemySunkCheck[row][col + j] = "3";
                                        break;
                                    case 4:
                                        enemySunkCheck[row][col + j] = "4";
                                        break;
                                    case 5:
                                        enemySunkCheck[row][col + j] = "5";
                                        break;
                                    case 6:
                                        enemySunkCheck[row][col + j] = "6";
                                        break;
                                    case 7:
                                        enemySunkCheck[row][col + j] = "7";
                                        break;
                                    case 8:
                                        enemySunkCheck[row][col + j] = "8";
                                        break;
                                    case 9:
                                        enemySunkCheck[row][col + j] = "9";
                                        break;
                                    case 10:
                                        enemySunkCheck[row][col + j] = "0";
                                        break;
                                }
                            }
                        }
                    }
                    nextShip++;
                    placed = true;
                }
            }
        }
        DisplayOwnBoard(PlayerShips, enemyShipLocations, roundCounter);


        System.out.println("This is your board, press enter to continue");
        scanner.nextLine();
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private static void ManualPlacement(int[][] PlayerShips, int[][] enemyShipLocations, int roundCounter, String[][] playerSunkCheck, String[][] enemySunkCheck) {
        int[] ShipLength = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
        boolean placement;
        char direction;
        int row, col, nextShip = 1;
        String ShipType;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the locations for your ships");

        for (int i = 0; i < ShipLength.length; i++) {
            ShipType = ShipLengthToName(ShipLength[i]);
            placement = false;
            while (!placement) {
                System.out.println("You will now place your " + ShipType);
                System.out.println("Do you want your ship to be vertical or horizontal?");
                System.out.println("Vertical will always go down and horizontal will always go right");
                direction = scanner.next().toLowerCase().charAt(0);

                while (direction != 'v' && direction != 'h') {
                    System.out.println("Please just type [V] or [H]");
                    direction = scanner.next().toLowerCase().charAt(0);
                }

                System.out.println("What will be the starting row of your ship (1-10)?");
                row = scanner.nextInt() - 1;
                System.out.println("What will be the starting column of your ship (1-10)?");
                col = scanner.nextInt() - 1;

                placement = placementConfirmation(row, col, direction, ShipLength[i], PlayerShips, enemyShipLocations, roundCounter);
                if (placement) {
                    if (roundCounter % 2 == 0) {
                        for (int j = 0; j < ShipLength[i]; j++) {
                            if (direction == 'v') {
                                PlayerShips[row + j][col] = 1;
                                switch (nextShip) {
                                    case 1:
                                        playerSunkCheck[row + j][col] = "1";
                                        break;
                                    case 2:
                                        playerSunkCheck[row + j][col] = "2";
                                        break;
                                    case 3:
                                        playerSunkCheck[row + j][col] = "3";
                                        break;
                                    case 4:
                                        playerSunkCheck[row + j][col] = "4";
                                        break;
                                    case 5:
                                        playerSunkCheck[row + j][col] = "5";
                                        break;
                                    case 6:
                                        playerSunkCheck[row + j][col] = "6";
                                        break;
                                    case 7:
                                        playerSunkCheck[row + j][col] = "7";
                                        break;
                                    case 8:
                                        playerSunkCheck[row + j][col] = "8";
                                        break;
                                    case 9:
                                        playerSunkCheck[row + j][col] = "9";
                                        break;
                                    case 10:
                                        playerSunkCheck[row + j][col] = "0";
                                        break;
                                }
                            } else {
                                PlayerShips[row][col + j] = 1;
                                switch (nextShip) {
                                    case 1:
                                        playerSunkCheck[row][col + j] = "1";
                                        break;
                                    case 2:
                                        playerSunkCheck[row][col + j] = "2";
                                        break;
                                    case 3:
                                        playerSunkCheck[row][col + j] = "3";
                                        break;
                                    case 4:
                                        playerSunkCheck[row][col + j] = "4";
                                        break;
                                    case 5:
                                        playerSunkCheck[row][col + j] = "5";
                                        break;
                                    case 6:
                                        playerSunkCheck[row][col + j] = "6";
                                        break;
                                    case 7:
                                        playerSunkCheck[row][col + j] = "7";
                                        break;
                                    case 8:
                                        playerSunkCheck[row][col + j] = "8";
                                        break;
                                    case 9:
                                        playerSunkCheck[row][col + j] = "9";
                                        break;
                                    case 10:
                                        playerSunkCheck[row][col + j] = "0";
                                        break;
                                }
                            }
                        }
                    } else {
                        for (int j = 0; j < ShipLength[i]; j++) {
                            if (direction == 'v') {
                                enemyShipLocations[row + j][col] = 1;
                                switch (nextShip) {
                                    case 1:
                                        enemySunkCheck[row + j][col] = "1";
                                        break;
                                    case 2:
                                        enemySunkCheck[row + j][col] = "2";
                                        break;
                                    case 3:
                                        enemySunkCheck[row + j][col] = "3";
                                        break;
                                    case 4:
                                        enemySunkCheck[row + j][col] = "4";
                                        break;
                                    case 5:
                                        enemySunkCheck[row + j][col] = "5";
                                        break;
                                    case 6:
                                        enemySunkCheck[row + j][col] = "6";
                                        break;
                                    case 7:
                                        enemySunkCheck[row + j][col] = "7";
                                        break;
                                    case 8:
                                        enemySunkCheck[row + j][col] = "8";
                                        break;
                                    case 9:
                                        enemySunkCheck[row + j][col] = "9";
                                        break;
                                    case 10:
                                        enemySunkCheck[row + j][col] = "0";
                                        break;
                                }
                            } else {
                                enemyShipLocations[row][col + j] = 1;
                                switch (nextShip) {
                                    case 1:
                                        enemySunkCheck[row][col + j] = "1";
                                        break;
                                    case 2:
                                        enemySunkCheck[row][col + j] = "2";
                                        break;
                                    case 3:
                                        enemySunkCheck[row][col + j] = "3";
                                        break;
                                    case 4:
                                        enemySunkCheck[row][col + j] = "4";
                                        break;
                                    case 5:
                                        enemySunkCheck[row][col + j] = "5";
                                        break;
                                    case 6:
                                        enemySunkCheck[row][col + j] = "6";
                                        break;
                                    case 7:
                                        enemySunkCheck[row][col + j] = "7";
                                        break;
                                    case 8:
                                        enemySunkCheck[row][col + j] = "8";
                                        break;
                                    case 9:
                                        enemySunkCheck[row][col + j] = "9";
                                        break;
                                    case 10:
                                        enemySunkCheck[row][col + j] = "0";
                                        break;
                                }
                            }
                        }
                    }
                    nextShip++;
                } else {
                    System.out.println("Invalid placement, try again.");
                }

            }
            DisplayOwnBoard(PlayerShips, enemyShipLocations, roundCounter);
            System.out.println("This is your board, press enter to continue");
            scanner.nextLine().charAt(0);
            for (int m = 0; m < 100; m++) {
                System.out.println();
            }
        }
    }

    private static boolean placementConfirmation(int row, int col, char direction, int length, int[][] playerShips, int[][] enemyShips, int roundCounter) {
        if (roundCounter % 2 == 0) {
            if (direction == 'v') {
                if (row + length > 10) return false;
                for (int i = row - 1; i <= row + length; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                            if (playerShips[i][j] == 1) return false;
                        }
                    }
                }
            } else { // Horizontal
                if (col + length > 10) return false;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + length; j++) {
                        if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                            if (playerShips[i][j] == 1) return false;
                        }
                    }
                }
            }
        } else {
            if (direction == 'v') {
                if (row + length > 10) return false;
                for (int i = row - 1; i <= row + length; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                            if (enemyShips[i][j] == 1) return false;
                        }
                    }
                }
            } else { // Horizontal
                if (col + length > 10) return false;
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + length; j++) {
                        if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                            if (enemyShips[i][j] == 1) return false;
                        }
                    }
                }
            }
        }


        return true;
    }

    private static String ShipLengthToName(int length) {
        return switch (length) {
            case 2 -> "Patrol Boat[2]";
            case 3 -> "Submarine[3]";
            case 4 -> "Battleship[4]";
            case 5 -> "Aircraft Carrier[5]";
            default -> "Unknown Ship";
        };
    }

    // GamePlay
    private static void whoShootsMsg(int playerTurn, String playerName1, String playerName2) {
        if (playerTurn % 2 == 0) {
            System.out.println("Its " + playerName1 + "'s turn");
        } else {
            System.out.println("Its " + playerName2 + "'s turn");
        }
    }

    private static boolean Shoot(int roundCounter, int[][] playerShipLocations, String[][] playerSunkCheck, String[][] enemySunkCheck, int[][] enemyShipLocations, int gameMode, int botDifficulty) {
        boolean hitOrMiss = false;
        if ((!(roundCounter % 2 == 0)) && gameMode == 1) {

            switch (botDifficulty) {
                case 1:
                    hitOrMiss = Bot.easyBot(roundCounter, playerShipLocations);
                case 2:
                    hitOrMiss = Bot.MediumBot(roundCounter, playerShipLocations, hitOrMiss);
                case 3:
                    hitOrMiss = Bot.HardBot(roundCounter, playerShipLocations, hitOrMiss);
            }

            return hitOrMiss;
        } else {

            Scanner scanner = new Scanner(System.in);
            int row = 11, col = 11;

            System.out.println("What row would you like to shoot in (1-10)?");
            row = scanner.nextInt() - 1;
            System.out.println("What column would you like to shoot in (1-10)?");
            col = scanner.nextInt() - 1;

            if (roundCounter % 2 == 0) {
                while (row > 9 || row < 0 || col > 9 || col < 0 || enemyShipLocations[row][col] == 2 || enemyShipLocations[row][col] == 3) {
                    if (row > 9 | row < 0 | col > 9 | col < 0) {
                        System.out.println("That is out of bounds, please type numbers between 1-10");
                    } else {
                        System.out.println("You've already shot here, please choose another spot");
                    }
                    System.out.print("Row:");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column:");
                    col = scanner.nextInt() - 1;
                }
            } else {
                while (row > 9 || row < 0 || col > 9 || col < 0 || playerShipLocations[row][col] == 2 || playerShipLocations[row][col] == 3) {
                    if (row > 9 | row < 0 | col > 9 | col < 0) {
                        System.out.println("That is out of bounds, please type numbers between 1-10");
                    } else {
                        System.out.println("You've already shot here, please choose another spot");
                    }
                    System.out.print("Row:");
                    row = scanner.nextInt() - 1;
                    System.out.print("Column:");
                    col = scanner.nextInt() - 1;
                }
            }

            if (roundCounter % 2 == 0) {
                if (enemyShipLocations[row][col] == 1) {
                    enemyShipLocations[row][col] = 2;
                    System.out.println(CGreen + "Your shot Hit! Good job!" + CReset);
                    enemySunkCheck[row][col] = enemySunkCheck[row][col] + "S";
                    return true; // Hit
                } else {
                    enemyShipLocations[row][col] = 3;
                    System.out.println(CRed + "You missed, try again next turn" + CReset);
                    return false; // Miss
                }
            } else {
                if (playerShipLocations[row][col] == 1) {
                    playerShipLocations[row][col] = 2;
                    System.out.println(CGreen + "Your shot Hit! Good job!" + CReset);
                    playerSunkCheck[row][col] = playerSunkCheck[row][col] + "S";
                    return true; // Hit
                } else {
                    playerShipLocations[row][col] = 3;
                    System.out.println(CRed + "You missed, try again next turn" + CReset);
                    return false; // Miss
                }
            }
        }

    }

    private static int[] checkHits(int[][] playerShipLocations, int[][] enemyShipLocations, int playerRecevedHits, int enemyRecevedHits, int roundCounter) {
        if (roundCounter % 2 == 0) {
            for (int i = 0; i < playerShipLocations.length; i++) {
                for (int j = 0; j < playerShipLocations[i].length; j++) {
                    if (playerShipLocations[i][j] == 2) {
                        playerRecevedHits++;
                    }
                }
            }
        } else {
            for (int i = 0; i < enemyShipLocations.length; i++) {
                for (int j = 0; j < enemyShipLocations[i].length; j++) {
                    if (enemyShipLocations[i][j] == 2) {
                        enemyRecevedHits++;
                    }
                }
            }
        }
        return new int[]{playerRecevedHits, enemyRecevedHits};
    }

    // Game Display
    private static void DisplayOwnBoard(int[][] playerShipLocations, int[][] enemyShipLocations, int roundCounter) {
        Scanner scanner = new Scanner(System.in);
        if (roundCounter % 2 == 0) {
            System.out.println("This is your own board, check where you've been hit");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (playerShipLocations[i][j] == 0) {
                        System.out.print("\uD83D\uDFE6 "); // Water
                    } else if (playerShipLocations[i][j] == 1) {
                        System.out.print("\uD83D\uDD33 "); // Ship
                    } else if (playerShipLocations[i][j] == 2) {
                        System.out.print("\uD83D\uDFE5 "); // Hit
                    } else if (playerShipLocations[i][j] == 3) {
                        System.out.print("⬜ "); // Miss
                    } else if (playerShipLocations[i][j] == 5) {
                        System.out.print("\uD83D\uDFEB "); // Sunk
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("This is your own board, check where you've been hit");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (enemyShipLocations[i][j] == 0) {
                        System.out.print("\uD83D\uDFE6 "); // Water
                    } else if (enemyShipLocations[i][j] == 1) {
                        System.out.print("\uD83D\uDD33 "); // Ship
                    } else if (enemyShipLocations[i][j] == 2) {
                        System.out.print("\uD83D\uDFE5 "); // Hit
                    } else if (enemyShipLocations[i][j] == 3) {
                        System.out.print("⬜ "); // Miss
                    } else if (enemyShipLocations[i][j] == 5) {
                        System.out.print("\uD83D\uDFEB "); // Sunk
                    }
                }
                System.out.println();
            }
        }
    }

    private static void DisplayEnemyBoard(int[][] playerShipLocations, int[][] enemyShipLocations, int roundCounter, String Player1Name, String Player2Name) {
        if (roundCounter % 2 == 0) {
            System.out.println("This is where you've shot");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (enemyShipLocations[i][j] == 0 | enemyShipLocations[i][j] == 1) {
                        System.out.print("\uD83D\uDFE6 "); // Water
                    } else if (enemyShipLocations[i][j] == 2) {
                        System.out.print("\uD83D\uDFE5 "); // Hit
                    } else if (enemyShipLocations[i][j] == 3) {
                        System.out.print("⬜ "); // Miss
                    }else if (enemyShipLocations[i][j] == 5) {
                        System.out.print("\uD83D\uDFEB"); // Sunk
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("This is where you've shot");
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (playerShipLocations[i][j] == 0 | playerShipLocations[i][j] == 1) {
                        System.out.print("\uD83D\uDFE6 "); // Water
                    } else if (playerShipLocations[i][j] == 2) {
                        System.out.print("\uD83D\uDFE5 "); // Hit
                    } else if (playerShipLocations[i][j] == 3) {
                        System.out.print("⬜ "); // Miss
                    }else if (playerShipLocations[i][j] == 5) {
                        System.out.print("\uD83D\uDFEB "); // Sunk
                    }
                }
                System.out.println();
            }
        }
    }
}
