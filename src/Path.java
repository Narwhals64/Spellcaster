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
        return this.p * this.wm;
    }

    @Override
    public int compareTo(Path o) {
        return this.p * this.wm - o.p * o.wm;
    }
}