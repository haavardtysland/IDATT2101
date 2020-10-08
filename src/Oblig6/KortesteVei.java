package Oblig6;


public class KortesteVei {


    public static void main(String[] args) {

        int[] graf = new int[]{4, 9, 2, 8, 3, 7, 0, 6, 1, 5};

        MinHeap heap = new MinHeap(graf);
        heap.lag_heap();


    }


}

class Vkant extends Kant {
    int vekt;
    public Vkant(Node n, Vkant nst, int vkt) {
        super(n, nst);
        vekt = vkt;
    }
}
class Kant {
    Kant neste;
    Node til;
    public Kant(Node n, Kant nst) {
        til = n;
        neste = nst;
    }
}

class Node {
    Kant kant1;
    Object d;

    public String toString() {
        return " " + d;
    }
}

class Forgj {
    int dist;
    Node forgj;
    static int uendelig = 100000000;
    public int finn_dist() {return dist;}
    public Node finn_forgj() {return forgj;}
    public Forgj() {
        dist = uendelig;
    }
}

class Dfs_forgj extends Forgj {
    int funnet_tid, ferdig_tid;
    static int tid;
    static void null_tid() {
        tid = 0;
    }
    static int les_tid() {
        return ++tid;
    }

    @Override
    public String toString() {
        return "Dfs_forgj{" +
                "funnet_tid=" + funnet_tid +
                ", ferdig_tid=" + ferdig_tid +
                '}';
    }
}
