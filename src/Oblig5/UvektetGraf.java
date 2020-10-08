package Oblig5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

public class UvektetGraf {
    int N, K;
    Node node[];

    public UvektetGraf() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        UvektetGraf graf = new UvektetGraf();
        URL url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g6");
        graf.printSterkSammenhengende(url);
    }



    public void printSterkSammenhengende(URL url) throws IOException {
        Stack stack = new Stack();
        boolean[] visited = new boolean[N];

        fraFilListe(url);

        for(int i = 0; i < N; i++) {
            dfs(node[i]);
            stack.push(i);
        }

        fraFilTransposeListe(url);

        for(int i = 0; i < N; i++) {
            dfs(node[i]);
            stack.push(i);
        }
        while(stack.empty() == false) {
        }
    }

    public void fraFilListe(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];
        for(int i = 0; i< N; ++i) node[i] = new Node();
        K = Integer.parseInt(st.nextToken());
        for(int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            Kant k = new Kant(node[til], node[fra].kant1);
            node[fra].kant1 = k;
        }
    }

    public void fraFilTransposeListe(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];
        for(int i = 0; i< N; ++i) node[i] = new Node();
        K = Integer.parseInt(st.nextToken());
        for(int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int fra = Integer.parseInt(st.nextToken());
            int til = Integer.parseInt(st.nextToken());
            Kant k = new Kant(node[fra], node[til].kant1);
            node[til].kant1 = k;
        }
    }
    public void dfs_init() {
        for(int i = N; i --> 0;) {
            node[i].d = new Dfs_forgj();
        }
        Dfs_forgj.null_tid();
    }

    public void df_sok(Node n) {
        Dfs_forgj nd = (Dfs_forgj)n.d;
        nd.funnet_tid = Dfs_forgj.les_tid();
        for(Kant k = n.kant1; k!= null; k = k.neste) {
            Dfs_forgj md = (Dfs_forgj)k.til.d;
            if(md.funnet_tid == 0) {
                md.forgj = n;
                md.dist = nd.dist +1;
                df_sok(k.til);
            }
        }
        nd.ferdig_tid = Dfs_forgj.les_tid();
    }

    public void dfs(Node s) {
        dfs_init();
        ((Dfs_forgj) s.d).dist = 0;
        df_sok(s);
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

class Stack {
    ArrayList<Integer> stack;

    Stack() {
        stack = new ArrayList<>();
    }

    void push(Integer i ) {
        stack.add(i);
    }

    void pop() {
        stack.remove(stack.size() -1);
    }

    Object next() {
        Object x = stack.get(0);
        stack.remove(0);
        return x;
    }

    boolean empty() {
        return stack.size() == 0;
    }

    ArrayList<Integer> getStack() {
        return stack;
    }
}



