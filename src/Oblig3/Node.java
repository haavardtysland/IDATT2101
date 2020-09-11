package Oblig3;

public class Node {

    int element;
    Node neste;
    Node forrige;

    public Node(int e, Node n, Node f) {
        element = e;
        neste = n;
        forrige = f;
    }

    public int finnElement() {
        return element;
    }

    public Node finnNeste() {
        return neste;
    }

    public Node finnForrige() {
        return forrige;
    }

}
