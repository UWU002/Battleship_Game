public class BattleshipGame {
//https://emojipedia.org/search?q=square
    public static void main(String[] args) {
        int[][] array = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 2, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        String[][] grid = new String[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (array[i][j]==1){
                    System.out.print("⬜ ");
                }
                if (array[i][j]==0){
                    System.out.print("\uD83D\uDFE6 ");
                }
                if (array[i][j]==2){
                    System.out.print("\uD83D\uDFE5 ");
                }

            }
            System.out.println();
        }
    }
}
