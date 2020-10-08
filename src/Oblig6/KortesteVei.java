package Oblig6;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

public class KortesteVei {



    public static void main(String[] args) throws IOException {

        URL url = new URL("http://www.iie.ntnu.no/fag/_alg/v-graf/vg1");
        Graf graf = new Graf(url);
        graf.lag_heap();
        for(int i = 0; i < graf.node.length; i++) {
            if(graf.node[i].kant1 == null) {
                System.out.println("start");
            } else if(graf.node[i] != null) {
                System.out.println("vet ikke");
            }
            System.out.println(graf.node[i].kant1.vekt);
        }
        //MinHeap heap = new MinHeap(graf);
        //heap.lag_heap();


    }
}

class Graf {
    int N, K;
    int len;
    Node[] node;

    public Graf(URL url) throws IOException {
        ny_vgraf(url);
    }

    public void ny_vgraf(URL url) throws IOException {
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
            int vekt = Integer.parseInt(st.nextToken());
            Vkant k = new Vkant(node[til], (Vkant)node[fra].kant1, vekt);
            node[fra].kant1 = k;
        }
    }

    public int over(int i){
        return (i-1) >> 1;
    }
    public int venstre(int i){
        return (i << 1) + 1;
    }
    public int hoyre(int i){
        return (i + 1) << 1;
    }

    public void bytt(Node[] node ,int i, int m){
        int nodeI = node[i].kant1.vekt;
        int nodeM = node[m].kant1.vekt;
        node[i].kant1.vekt = nodeM;
        node[m].kant1.vekt = nodeI;

    }
    public void lag_heap(){
        int i = len;
        while(i --> 0){
            fiks_heap(i);
        }
    }

    public void sett_inn(int x){
        int i = len++;
        node[i].kant1.vekt = x;
        prio_ned(i,0);
    }

    public void fiks_heap(int i){
        int m = venstre(i);
        if(m < len){
            int h = m+1;
            if(h < len && node[h].kant1.vekt < node[m].kant1.vekt) {
                m = h;
            }
            if(node[m].kant1.vekt < node[i].kant1.vekt){
                bytt(node,i,m);
                fiks_heap(m);
            }
        }
    }

    public int hent_min(){
        int min = node[0].kant1.vekt;
        node[0] = node[--len];
        fiks_heap(0);
        return min;
    }

    public void prio_ned(int i, int p){
        node[i].kant1.vekt -= p;
        fiks_heap(i);
    }

    public  void prio_opp(int i, int p){
        int f;
        node[i].kant1.vekt += p;
        while(i > 0 && node[i].kant1.vekt > node[f = over(i)].kant1.vekt){
            bytt(node,i,f);
            i=f;
        }
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
    Vkant kant1;
    Forgj forgj;
    int nodeNummer;
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
