import java.util.ArrayList;

public class Board {
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

    public void doubleLetter(int x, int y) {
        if (x == -1) return;
        getTile(x, y).p *= 2;
    }

    public void tripleLetter(int x, int y) {
        if (x == -1) return;
        getTile(x, y).p *= 3;
    }

    public void doubleWord(int x, int y) {
        if (x == -1) return;
        getTile(x, y).dw = true;
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


    public Path findBestPath(int x, int y, Path prefix, Dictionary dic) {

        Board nb = new Board();
        this.copyTo(nb);

        Tile thisTile = nb.removeTile(x, y);
        Path word = new Path(prefix.word + thisTile.l,
                prefix.p + thisTile.p);

        word.wm = Integer.max(word.wm, prefix.wm);
        if (thisTile.dw)
            word.wm = 2;


        Dictionary subDic = dic.subDic(word.word);

        if (subDic.length() == 0) { // No POTENTIAL words found!
            return null;
        } else {

            ArrayList<Path> paths = new ArrayList<>();

            if (subDic.isWord(word.word)) {
                paths.add(word);
            }

            for (int nx = -1 ; nx <= 1 ; nx++) {
                for (int ny = -1 ; ny <= 1 ; ny++) {
                    if (
                            x + nx >= 0 && x + nx <= 4 &&
                                    y + ny >= 0 && y + ny <= 4
                    ) { // if in bounds
                        if (nb.hasTile(x + nx, y + ny)) { // If the tile isn't already part of the word

                            Path newPath = nb.findBestPath(x + nx, y + ny, word, subDic);
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

    public Path findBestWord(Dictionary dic) {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {

                Path newPath = findBestPath(x, y, new Path("", 0), dic);
                if (newPath != null)
                    paths.add(newPath);
            }
        }

        if (paths.isEmpty())
            return null;
        return paths.stream().max(Path::compareTo).get();
    }

    public Path findBestSwap(Dictionary dic) {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {

                char orig = getTile(x, y).l;

                for (char c : Main.L26.toCharArray()) {

                    if (c == orig) {

                    } else {

                        Board nb = new Board();
                        copyTo(nb);

                        Tile ot = nb.removeTile(x, y); // Old Tile
                        Tile nt = new Tile(x, y, c, 0); // New Tile
                        nt.dw = ot.dw; // Copy Double Word
                        nb.addTile(nt);

                        Path newPath = nb.findBestWord(dic);
                        if (newPath != null) {
                            paths.add(newPath);
                        }

                    }

                }
            }
        }
        return paths.stream().max(Path::compareTo).get();
    }



    public Path findBestDS(Dictionary dic) {
        ArrayList<Path> paths = new ArrayList<>();
        for (int y = 0 ; y < 5 ; y++) {
            for (int x = 0 ; x < 5 ; x++) {

                char orig = getTile(x, y).l;

                for (char c : Main.L26.toCharArray()) {

                    if (c == orig) {

                    } else {

                        Board nb = new Board();
                        copyTo(nb);

                        Tile ot = nb.removeTile(x, y); // Old Tile
                        Tile nt = new Tile(x, y, c, 0); // New Tile
                        nt.dw = ot.dw; // Copy Double Word
                        nb.addTile(nt);

                        Path newPath = nb.findBestSwap(dic);
                        if (newPath != null) {
                            paths.add(newPath);
                        }

                    }

                }
            }
        }
        return paths.stream().max(Path::compareTo).get();
    }


}