public class Tile {
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

        int ind = Main.L26.indexOf(l);
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