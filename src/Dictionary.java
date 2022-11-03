import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Dictionary implements Iterable<String> {
    String[] dic;

    public Dictionary() {
        dic = new String[0];
    }

    public Dictionary(String[] dic) {
        this.dic = dic;
    }

    public Dictionary(String path) throws FileNotFoundException {
        ArrayList<String> dicList = new ArrayList<>();

        File dicFile = new File("src/2of4.txt");
        Scanner reader = new Scanner(dicFile);
        while (reader.hasNextLine()) {
            String word = reader.nextLine();
            dicList.add(word.toUpperCase());
        }
        this.dic = dicList.toArray(new String[0]);
    }

    public int length() {
        return dic.length;
    }

    /**
     * Binary search for the given word,
     * return true if the word is present.
     * @param word
     * @return
     */
    public boolean isWord(String word) {
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

    /**
     * Binary search for the given word's
     * first occurrence in the Dictionary.
     * @param word
     * @return
     */
    public int firstOcc(String word) {
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

    /**
     * Binary search for the given word's
     * last occurrence in the Dictionary.
     * @param word
     * @return
     */
    public int lastOcc(String word) {
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
    public Dictionary subDic(String prefix) {
        int start = firstOcc(prefix);
        int end = lastOcc(prefix);

        if (start == -1 || end == -1) {
            return new Dictionary();
        }

        return new Dictionary(Arrays.copyOfRange(dic, start, end+1));
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.stream(this.dic).iterator();
    }
}
