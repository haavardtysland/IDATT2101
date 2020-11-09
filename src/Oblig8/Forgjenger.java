package Oblig8;

class Forgjenger {
    int distanse;
    int fullDistanse;
    int distanseTilSlutt;
    Node forgjenger;
    static final int uendelig = 100000000;
    public int finnDistanse() {return distanse;}
    public Node finnForgjenger() {return forgjenger;}
    public Forgjenger() {
        this.distanse = uendelig;
        this.fullDistanse = uendelig;
        this.distanseTilSlutt = -1;
    }

}
