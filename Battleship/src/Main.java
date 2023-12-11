    import java.util.Scanner;

    public class Main {
        public static String[][] grid = new String[10][10];
        public static int enemyShips;

        public static void main(String[] args) {
            //Game Settings
            String player1Name, player2Name;
            int gameMode = 0;
            int[][] playerShipLocations;
            int[][] enemyShipLocations;
            //Game Functionality
            int roundCounter = 0;

            // Game Shots
            int[][] PlayerHits=hitMissArrays();
            int[][] PlayerMisses=hitMissArrays();
            int[][] EnemyHits=hitMissArrays();
            int[][] EnemyMisses=hitMissArrays();


            introduction();
            tutorial();
            gameMode = gameMode(gameMode);
            player1Name = definePlayerName(gameMode);
            playerShipLocations = setPlayerShips();
            player2Name = definePlayerName(gameMode);
            enemyShipLocations = setEnemyShips(gameMode);
           // while (false) {
                whoShootsMsg(roundCounter, player1Name, player2Name);
                //DisplayEnemyBoard();
                Shoot(PlayerHits, PlayerMisses, EnemyHits, EnemyMisses, roundCounter, playerShipLocations, enemyShipLocations);
               // DisplayOwnBoard();


                roundCounter++;
           // }
        }

        private static void Shoot(int[][] playerHits, int[][] playerMisses, int[][] enemyHits, int[][] enemyMisses, int roundCounter, int[][] playerShipLocations, int[][] enemyShipLocations) {
            Scanner scanner= new Scanner(System.in);
            int row=11, col=11;

                System.out.println("What row would you like to shoot in (0-9)?");
                row = scanner.nextInt();
                System.out.println("What will be the starting column of your ship (0-9)?");
                col = scanner.nextInt();
            while (row > 9 | row < 0 | col > 9 |col < 0) {
                System.out.println("That is out of bounds, please type numbers between 0-9");
                System.out.print("Row:");
                row = scanner.nextInt();
                System.out.print("Column:");
                col = scanner.nextInt();
            }




        }

        private static int[][] hitMissArrays(){
            return new int[10][10];
        }

        private static String definePlayerName(int gameMode) {
            Scanner scanner = new Scanner(System.in);
            if (gameMode == 2) {
                System.out.println("Define your name for this Game");
            } else {
                System.out.println("Define your name for this Game and then the Bot's");
            }
            return scanner.nextLine();
        }

        private static void whoShootsMsg(int playerTurn, String playerName1, String playerName2) {
            if (playerTurn % 2 == 0) {
                System.out.println("Its " + playerName1 + "'s turn");
            } else {
                System.out.println("Its " + playerName2 + "'s turn");
            }

        }

        private static int[][] setEnemyShips(int gameMode) {
            int[][] EnemyShips = new int[10][10];
            if (gameMode == 2) {
                EnemyShips = setPlayerShips();
            } else {
                //Automatic bot placements
            }
            return EnemyShips;
        }

        private static int[][] setPlayerShips() {
            int[][] PlayerShips = new int[10][10];
            int[] ShipLength = {5, 4, 4, 3, 3, 3, 2, 2, 2, 2};
            boolean placement;
            char direction;
            int row, col;
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

                    System.out.println("What will be the starting row of your ship (0-9)?");
                    row = scanner.nextInt();
                    System.out.println("What will be the starting column of your ship (0-9)?");
                    col = scanner.nextInt();

                    placement = placementConfirmation(row, col, direction, ShipLength[i], PlayerShips);
                    if (placement) {
                        for (int j = 0; j < ShipLength[i]; j++) {
                            if (direction == 'v') {
                                PlayerShips[row + j][col] = 1;
                            } else {
                                PlayerShips[row][col + j] = 1;
                            }
                        }
                    } else {
                        System.out.println("Invalid placement, try again.");
                    }
                }
            }

            return PlayerShips;
        }

        private static boolean placementConfirmation(int row, int col, char direction, int length, int[][] playerShips) {
            if (direction == 'v') {
                if (row + length > 10) return false;
                for (int i = row; i < row + length; i++) {
                    if (playerShips[i][col] == 1) return false;
                }
            } else {
                if (col + length > 10) return false;
                for (int i = col; i < col + length; i++) {
                    if (playerShips[row][i] == 1) return false;
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

        private static int gameMode(int gameMode) {
            Scanner scanner = new Scanner(System.in);
            gameMode = 0;
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

        private static void tutorial() {
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            char userSelection;
            System.out.println("Would you like to read the tutorial?");
            while (!exit) {
                userSelection = scanner.next().toLowerCase().charAt(0);
                if (userSelection == 'y') {
                    System.out.println("Here I display the tutorial");
                    exit = true;
                } else if (userSelection == 'n') {
                    System.out.println("The game will Start now");
                    exit = true;
                } else {
                    System.out.println("Please just type [yes] or [no]");
                }
            }
        }

        private static void introduction() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press enter to start the Game");
            scanner.nextLine();
        }

        private void initializeAndDisplayGrid() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    System.out.print(grid[i][j] + " ");
                }
                System.out.println();
            }
        }


    }