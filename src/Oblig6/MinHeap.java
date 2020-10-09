//package Oblig6;

public class MinHeap {
    int len;
    int[] node;

    public MinHeap(int[] graf) {
        len = graf.length;
        node = graf;
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

    public void bytt(int[] node ,int i, int m){
        int nodeI = node[i];
        int nodeM = node[m];
        node[i] = nodeM;
        node[m] = nodeI;

    }
    public void lag_heap(){
        int i = len;
        while(i --> 0){
            fiks_heap(i);
        }
    }

    public void sett_inn(int x){
        int i = len++;
        node[i] = x;
        prio_ned(i,0);
    }

    public void fiks_heap(int i){
        int m = venstre(i);
        if(m < len){
            int h = m+1;
            if(h < len && node[h] < node[m]) {
                m = h;
            }
            if(node[m] < node[i]){
                bytt(node,i,m);
                fiks_heap(m);
            }
        }
    }

    public int hent_min(){
        int maks = node[0];
        node[0] = node[--len];
        fiks_heap(0);
        return maks;
    }

    public void prio_ned(int i, int p){
        node[i] -= p;
        fiks_heap(i);
    }

    public  void prio_opp(int i, int p){
        int f;
        node[i] += p;
        while(i > 0 && node[i] > node[f = over(i)]){
            bytt(node,i,f);
            i=f;
        }
    }
}