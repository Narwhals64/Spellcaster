public class Path implements Comparable<Path> {
    String word;
    int p;

    int wm; // Word Multiplier (double word)

    public Path(String word, int p) {
        this.word = word;
        this.p = p;
        this.wm = 1;
    }

    int getPoints() {
        int bonus = word.length() >= 7 ? 20 : 0;
        return this.p * this.wm + bonus;
    }

    @Override
    public int compareTo(Path o) {
        return getPoints() - o.getPoints();
    }
}