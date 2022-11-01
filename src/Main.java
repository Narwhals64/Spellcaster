import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Board board;
    private static String[] dic;
    private static final String L25 = "ABCDEFGHIJKLMNOPQRSTUVWXY";
    private static final String T1 =  "CATXFXXXXXXXXXXXXXXXXXXXX";
    private static final String T2 =  "CATXFLMNOPAATAAAAALAAAAAY";
    private static final String T3 =  "CATXFLMNOPAATAAPAALAAAAAY";

    public static void main(String[] args) throws FileNotFoundException {
        initialize();
        makeBoard(T3);
        tripleLetter(-1, -1);
        doubleWord(-1, -1);

        board.print();

        Path word = findBestWord();
        System.out.println(word.word + " = " + word.getPoints());

    }

    public static void initialize() throws FileNotFoundException {
        ArrayList<String> dicList = new ArrayList<>();

        File dicFile = new File("src/dictionary.txt");
        Scanner reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word);
        }
        dic = dicList.toArray(new String[0]);
    }


    public static void makeBoard(String letStr) {
        board = new Board();

        int i = 0;
        for (char c : letStr.toCharArray()) {
            board.addTile(new Tile(i%5, i/5, c));
            i++;
        }

        // T3:
        //board.getTile(0,3).dw = false;
    }

    public static void tripleLetter(int x, int y) {
        if (x == -1) return;
        board.getTile(x, y).p *= 2;
    }

    public static void doubleWord(int x, int y) {
        if (x == -1) return;
        board.getTile(x, y).dw = true;
    }





    public static boolean isWord(String word) {
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

    public static void listSubDic(String prefix) {
        String[] newDic = subDic(prefix);
        for (String s : newDic) {
            System.out.println(s);
        }
    }



    public static Path findBestWord() {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {
                Path newPath = findBestPath(board, x, y, new Path("", 0));
                if (newPath != null)
                    paths.add(newPath);
            }
        }
        return paths.stream().max(Path::compareTo).get();
    }

    public static Path findBestPath(Board b, int x, int y, Path prefix) {

        Tile thisTile = b.removeTile(x, y);
        Path word = new Path(prefix.word + thisTile.l,
                prefix.p + thisTile.p);

        if (thisTile.dw) {
            word.wm = 2;
        }


        Board nb = new Board();
        b.copyTo(nb);
        nb.removeTile(x, y);

        String[] subDic = subDic(word.word);

        if (subDic.length == 0) { // No POTENTIAL words found!
            return null;
        } else {

            ArrayList<Path> paths = new ArrayList<>();

            if (isWord(word.word)) {
                paths.add(word);
            }

            for (int nx = -1 ; nx <= 1 ; nx++) {
                for (int ny = -1 ; ny <= 1 ; ny++) {
                    if (
                        x + nx >= 0 && x + nx <= 4 &&
                        y + ny >= 0 && y + ny <= 4
                    ) { // if in bounds
                        if (nb.hasTile(x + nx, y + ny)) { // If the tile isn't already part of the word

                            Path newPath = findBestPath(nb, x + nx, y + ny, word);
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
            return this.p * wm;
        }

        @Override
        public int compareTo(Path o) {
            return this.p * this.wm - o.p * o.wm;
        }
    }





}
