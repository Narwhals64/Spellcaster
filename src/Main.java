import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Board board;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> dic = new ArrayList<>();

        File dicFile = new File("src/dictionary.txt");
        Scanner reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dic.add(word);
        }

        String l25 = "ABCDEFGHIJKLMNOPQRSTUVWXY";

        makeBoard(l25);

        board.print();

    }

    public static void makeBoard(String letStr) {
        board = new Board();

        int i = 0;
        for (char c : letStr.toCharArray()) {
            board.addTile(new Tile(i%5, i/5, c));
            i++;
        }
    }




    private static class Board {
        private ArrayList<Tile> tiles;

        public Board() {
            tiles = new ArrayList<>();
        }

        public void addTile(Tile t) {
            tiles.add(t);
        }

        public void print() {
            for (Tile t : tiles) {
                System.out.print(t.l);
            }
        }
    }

    private static class Tile {
        int x;
        int y;
        char l;

        public Tile(int x, int y, char l) {
            this.x = x;
            this.y = y;
            this.l = l;
        }
    }


}
