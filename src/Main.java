import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static Board board;
    private static String[] dic;
    private static Dictionary dic2of12;
    private static Dictionary dic2of4;
    private static Dictionary dicmwords;
    public static final String L25 = "ABCDEFGHIJKLMNOPQRSTUVWXY";
    public static final String L26 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String T1 =  "CATXFXXXXXXXXXXXXXXXXXXXX";
    private static final String T2 =  "CATXFLMNOPAATAAAAALAAAAAY";
    private static final String T3 =  "CATXFLMNOPAATAAPAALAAAAAY";
    private static final String T4 =  "CATXFLMNOPAATAAPAALAAAAAA";
    private static final String T5 =  "BJWKVTRVKCJTDVBYGCUGYTVKS";
    private static final String T6 =  "ANOQINRNTRZOODYATIATEGWRU";

    private static ArrayList<String> unDic;

    public static void main(String[] args) throws FileNotFoundException {
        unDic = new ArrayList<>();

        String grid = "OAAUEDVUA*NINNITXVUYB3TRAHA";

        initialize();
        makeBoard(grid);

        board.print();
        System.out.println("\n");

        findWords(board, dic2of12);
        //findWords(board, dic2of4);
        //findWords(board, dicmwords);


    }

    public static void findWords(Board board, Dictionary dic) {
        double m1 = System.currentTimeMillis();
        Path word = board.findBestWord(dic);
        double m2 = System.currentTimeMillis();
        Path swap = board.findBestSwap(dic);
        double m3 = System.currentTimeMillis();
        //Path ds = findBestDS(board, dic);
        //double m4 = System.currentTimeMillis();
        System.out.println(word.word + " = " + word.getPoints());
        System.out.println("(" + (m2-m1) + " millis)");
        System.out.println(swap.word + " = " + swap.getPoints());
        System.out.println("(" + (m3-m2) + " millis)");
        //System.out.println(ds.word + " = " + ds.getPoints());
        //System.out.println("(" + (m4-m3) + " millis)");
    }

    public static void initialize() throws FileNotFoundException {
        dic2of4 = new Dictionary("src/2of4.txt");
        dic2of12 = new Dictionary("src/2of12.txt");
        dicmwords = new Dictionary("src/mwords.txt");
    }

    public static void makeBoard(String letStr) {
        board = new Board();

        int x = 0;
        int y = 0;

        for (int i = 0 ; i < letStr.length() ; i++) {
            char c = letStr.charAt(i);
            if (c == '*') {
                board.addTile(new Tile(x, y, letStr.charAt(i+1)));
                board.getTile(x, y).dw = true;
                i++;
            } else if (c == '2') {
                board.addTile(new Tile(x, y, letStr.charAt(i+1)));
                board.getTile(x, y).p *= 2;
                i++;
            } else if (c == '3') {
                board.addTile(new Tile(x, y, letStr.charAt(i+1)));
                board.getTile(x, y).p *= 3;
                i++;
            } else {
                board.addTile(new Tile(x, y, c));
            }

            x++;
            if (x == 5){
                x = 0;
                y++;
            }
        }

    }

    public static String randomString() {
        String output = "";
        Random r = new Random();
        for (int i = 0 ; i < 25 ; i++) {
            output += L26.charAt(r.nextInt(26));
        }
        return output;
    }

}
