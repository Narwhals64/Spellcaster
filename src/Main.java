import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Board board;
    private static String[] dic;

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> dicList = new ArrayList<>();

        File dicFile = new File("src/dictionary.txt");
        Scanner reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word);
        }

        dic = dicList.toArray(new String[0]);

        String l25 = "ABCDEFGHIJKLMNOPQRSTUVWXY";

        //makeBoard(l25);

        //board.print();

        String[] newDic = subDic("BANAX");
        for (String s : newDic) {
            System.out.println(s);
        }

    }


    public static void makeBoard(String letStr) {
        board = new Board();

        int i = 0;
        for (char c : letStr.toCharArray()) {
            board.addTile(new Tile(i%5, i/5, c));
            i++;
        }
    }


    public static int firstOcc(String word) {
        int low = 0;
        int high = dic.length - 1;
        int index = -1;

        while (low <= high) {
            index = low  + ((high - low) / 2);

            if (dic[index].startsWith(word) && index == 0) {
                return index;
            } else if (dic[index].startsWith(word) && !dic[index-1].startsWith(word)) {
                return index;
            } else if (dic[index].startsWith(word)) {
                high = index - 1;
            } else if (!dic[index].startsWith(word) && dic[index].compareTo(word) < 0) {
                low = index + 1;
            }  else if (!dic[index].startsWith(word) && dic[index].compareTo(word) > 0) {
                high = index - 1;
            }

        }

        return -1;
    }

    public static int lastOcc(String word) {
        int low = 0;
        int high = dic.length - 1;
        int index = -1;

        while (low <= high) {
            index = low  + ((high - low) / 2);

            if (dic[index].startsWith(word) && index == dic.length-1) {
                return index;
            } else if (dic[index].startsWith(word) && !dic[index+1].startsWith(word)) {
                return index;
            } else if (dic[index].startsWith(word)) {
                low = index + 1;
            } else if (!dic[index].startsWith(word) && dic[index].compareTo(word) < 0) {
                low = index + 1;
            }  else if (!dic[index].startsWith(word) && dic[index].compareTo(word) > 0) {
                high = index - 1;
            }

        }

        return -1;
    }

    /**
     * Returns a dictionary of words that
     * start with the given prefix.
     * @param prefix
     * @return
     */
    public static String[] subDic(String prefix) {
        int start = firstOcc(prefix);
        int end = lastOcc(prefix);

        if (start == -1 || end == -1) {
            return new String[0];
        }

        return Arrays.copyOfRange(dic, start, end+1);
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
