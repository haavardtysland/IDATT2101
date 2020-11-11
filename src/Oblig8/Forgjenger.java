package Oblig8;

/**
 *  Klassen Forgjenger
 */
class Forgjenger {

    int distanse;
    int fullDistanse;
    int distanseTilSlutt;
    Node forgjenger;
    static final int uendelig = 1000000000;

    public Forgjenger() {
        this.distanse = uendelig;
        this.fullDistanse = uendelig;
        this.distanseTilSlutt = -1;
    }

}
