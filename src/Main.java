import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static Board board;
    private static String[] dic;
    private static String[] dic2of12;
    private static String[] dic2of4;
    private static String[] dicmwords;
    private static final String L25 = "ABCDEFGHIJKLMNOPQRSTUVWXY";
    private static final String L26 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String T1 =  "CATXFXXXXXXXXXXXXXXXXXXXX";
    private static final String T2 =  "CATXFLMNOPAATAAAAALAAAAAY";
    private static final String T3 =  "CATXFLMNOPAATAAPAALAAAAAY";
    private static final String T4 =  "CATXFLMNOPAATAAPAALAAAAAA";
    private static final String T5 =  "BJWKVTRVKCJTDVBYGCUGYTVKS";

    private static ArrayList<String> unDic;

    /*

    ANOQI
    NRNTR
    ZOODY
    ATIAT
    EGWRU

     */


    public static void main(String[] args) throws FileNotFoundException {
        unDic = new ArrayList<>();

        String grid = "ANOQINRNTRZOODYATIATEGWRU";

        initialize();
        makeBoard(grid);
        doubleLetter(-1,3);
        tripleLetter(4,0);
        doubleWord(2,4);


        board.print();
        System.out.println("\n");

        findWords(dic2of12);
        //findWords(dic2of4);
        //findWords(dicmwords);


    }

    public static void findWords(String[] dic) {
        double m1 = System.currentTimeMillis();
        Path word = findBestWord(board, dic);
        double m2 = System.currentTimeMillis();
        Path swap = findBestSwap(board, dic);
        double m3 = System.currentTimeMillis();
        System.out.println(word.word + " = " + word.getPoints());
        System.out.println("(" + (m2-m1) + " millis)");
        System.out.println(swap.word + " = " + swap.getPoints());
        System.out.println("(" + (m3-m2) + " millis)");
    }

    public static void initialize() throws FileNotFoundException {
        ArrayList<String> dicList = new ArrayList<>();

        File dicFile = new File("src/2of4.txt");
        Scanner reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word.toUpperCase());
        }
        dic2of4 = dicList.toArray(new String[0]);

        dicFile = new File("src/2of12.txt");
        reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word.toUpperCase());
        }
        dic2of12 = dicList.toArray(new String[0]);

        dicFile = new File("src/mwords.txt");
        reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word.toUpperCase());
        }
        dicmwords = dicList.toArray(new String[0]);
    }


    public static void makeBoard(String letStr) {
        board = new Board();

        int i = 0;
        for (char c : letStr.toCharArray()) {
            board.addTile(new Tile(i%5, i/5, c));
            i++;
        }

    }

    public static void doubleLetter(int x, int y) {
        if (x == -1) return;
        board.getTile(x, y).p *= 2;
    }

    public static void tripleLetter(int x, int y) {
        if (x == -1) return;
        board.getTile(x, y).p *= 3;
    }

    public static void doubleWord(int x, int y) {
        if (x == -1) return;
        board.getTile(x, y).dw = true;
    }


    public static String randomString() {
        String output = "";
        Random r = new Random();
        for (int i = 0 ; i < 25 ; i++) {
            output += L26.charAt(r.nextInt(26));
        }
        return output;
    }




    public static boolean isWord(String word, String[] dic) {
        int low = 0;
        int high = dic.length - 1;
        int index = -1;

        while (low <= high) {
            index = low  + ((high - low) / 2);

            if (dic[index].equals(word)) {
                return true;
            } else if (dic[index].compareTo(word) < 0) {
                low = index + 1;
            }  else if (dic[index].compareTo(word) > 0) {
                high = index - 1;
            }

        }

        return false;
    }

    public static int firstOcc(String[] dic, String word) {
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

    public static int lastOcc(String[] dic, String word) {
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
    public static String[] subDic(String[] dic, String prefix) {
        int start = firstOcc(dic, prefix);
        int end = lastOcc(dic, prefix);

        if (start == -1 || end == -1) {
            return new String[0];
        }

        return Arrays.copyOfRange(dic, start, end+1);
    }

    public static void listSubDic(String[] dic, String prefix) {
        String[] newDic = subDic(dic, prefix);
        for (String s : newDic) {
            System.out.println(s);
        }
    }



    public static Path findBestWord(Board board, String[] dic) {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {

                Path newPath = findBestPath(board, x, y, new Path("", 0), dic);
                if (newPath != null)
                    paths.add(newPath);
            }
        }

        if (paths.isEmpty())
            return null;
        return paths.stream().max(Path::compareTo).get();
    }

    public static Path findBestSwap(Board board, String[] dic) {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {

                char orig = board.getTile(x, y).l;

                for (char c : L26.toCharArray()) {

                    if (c == orig) {

                    } else {

                        Board nb = new Board();
                        board.copyTo(nb);

                        Tile ot = nb.removeTile(x, y); // Old Tile
                        Tile nt = new Tile(x, y, c, 0); // New Tile
                        nt.dw = ot.dw; // Copy Double Word
                        nb.addTile(nt);

                        Path newPath = findBestWord(nb, dic);
                        if (newPath != null) {
                            paths.add(newPath);
                        }

                    }

                }
            }
        }
        return paths.stream().max(Path::compareTo).get();
    }

    public static Path findBestPath(Board b, int x, int y, Path prefix, String[] dic) {

        Board nb = new Board();
        b.copyTo(nb);

        Tile thisTile = nb.removeTile(x, y);
        Path word = new Path(prefix.word + thisTile.l,
                prefix.p + thisTile.p);

        word.wm = Integer.max(word.wm, prefix.wm);
        if (thisTile.dw)
            word.wm = 2;


        String[] subDic = subDic(dic, word.word);

        if (subDic.length == 0) { // No POTENTIAL words found!
            return null;
        } else {

            ArrayList<Path> paths = new ArrayList<>();

            if (isWord(word.word, dic)) {
                paths.add(word);
            }

            for (int nx = -1 ; nx <= 1 ; nx++) {
                for (int ny = -1 ; ny <= 1 ; ny++) {
                    if (
                        x + nx >= 0 && x + nx <= 4 &&
                        y + ny >= 0 && y + ny <= 4
                    ) { // if in bounds
                        if (nb.hasTile(x + nx, y + ny)) { // If the tile isn't already part of the word

                            Path newPath = findBestPath(nb, x + nx, y + ny, word, subDic);
                            if (newPath != null) {
                                paths.add(newPath);
                            }

                        }
                    }
                }
            }



            // paths now has a bunch of words, find the best one

            if (!paths.isEmpty())
                return paths.stream().max(Path::compareTo).get();

        }


        return null;
    }



    private static class Board {
        private ArrayList<Tile> tiles;

        public Board() {
            tiles = new ArrayList<>();
        }

        public void addTile(Tile t) {
            tiles.add(t);
        }

        public Tile getTile(int x, int y) {
            for (Tile t : tiles) {
                if (t.x == x && t.y == y) {
                    return t;
                }
            }
            return null;
        }

        public Tile removeTile(int x, int y) {
            for (Tile t : tiles) {
                if (t.x == x && t.y == y) {
                    tiles.remove(t);
                    return t;
                }
            }
            return null;
        }

        public boolean hasTile(int x, int y) {
            return getTile(x, y) != null;
        }

        public void copyTo(Board other) {
            for (Tile t : tiles) {
                other.addTile(t);
            }
        }

        public void print() {
            int i = 0;
            for (Tile t : tiles) {
                System.out.print(t.l);
                i++;
                if (i == 5) {
                    System.out.println();
                    i = 0;
                }
            }
        }
    }

    private static class Tile {
        int x;
        int y;
        char l;
        int p;

        boolean dw;

        public Tile(int x, int y, char l) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.p = 1;

            this.dw = false;

            int ind = L26.indexOf(l);
            //                A   B   C   D   E   F   G   H   I   J   K   L   M   N   O   P   Q   R   S   T   U   V   W   X   Y   Z
            //int[] values = {1,  3,  3,  2,  1,  4,  2,  4,  1,  8,  5,  1,  3,  1,  1,  3,  10, 1,  1,  1,  1,  4,  4,  8,  4,  10};
            //                -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   -   Z
              int[] values = {1,  4,  5,  3,  1,  5,  3,  4,  1,  7,  6,  3,  4,  2,  1,  4,  8,  2,  2,  2,  4,  5,  5,  7,  4,  10};
            this.p = values[ind];

        }

        public Tile(int x, int y, char l, int p) {
            this.x = x;
            this.y = y;
            this.l = l;
            this.p = p;

            this.dw = false;
        }

        public void setPoints(int p) {
            this.p = p;
        }
    }

    private static class Path implements Comparable<Path> {
        String word;
        int p;

        int wm; // Word Multiplier (double word)

        public Path(String word, int p) {
            this.word = word;
            this.p = p;
            this.wm = 1;
        }

        int getPoints() {
            return this.p * this.wm;
        }

        @Override
        public int compareTo(Path o) {
            return this.p * this.wm - o.p * o.wm;
        }
    }

}
