package Oblig3;

public class Node {

    double element;
    Node neste;
    Node forrige;

    public Node(double e, Node n, Node f) {
        element = e;
        neste = n;
        forrige = f;
    }

    public double finnElement() {
        return element;
    }

    public Node finnNeste() {
        return neste;
    }

    public Node finnForrige() {
        return forrige;
    }

}
