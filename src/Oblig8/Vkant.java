package Oblig8;

/**
 * Klassen Vkant som representerer en vektet kant, arver fra klassen Kant
 */
class Vkant extends Kant {

    int kjoeretid;
    int lengde;
    int fartsgrense;

    public Vkant(Node til, Vkant neste , int kjoeretid, int lengde, int fartsgrense) {
        super(til, neste);
        this.kjoeretid = kjoeretid;
        this.lengde = lengde;
        this.fartsgrense = fartsgrense;
    }
}
