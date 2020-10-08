import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.LinkedList;


class Ugraf {
    private int V;
    private LinkedList<Integer> adj[];


    Ugraf(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void leggTilKant(int v, int k) {
        adj[v].add(k);
    }

    void DFSUtil(int v, boolean visited[]) {
        visited[v] = true;
        System.out.print(v + " ");

        int n;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }

    Ugraf getTranspose() {
        Ugraf g = new Ugraf(V);
        for (int v = 0; v < V; v++) {
            Iterator<Integer> i = adj[v].listIterator();
            while (i.hasNext())
                g.adj[i.next()].add(v);
        }
        return g;
    }

    void iRekkefoelge(int v, boolean visited[], Stack stack) {
        visited[v] = true;

        Iterator<Integer> i = adj[v].iterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                iRekkefoelge(n, visited, stack);
        }


        stack.push(v);
    }

    void printSterkSammenhengende() {
        Stack stack = new Stack();

        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++)
            visited[i] = false;


        for (int i = 0; i < V; i++)
            if (visited[i] == false)
                iRekkefoelge(i, visited, stack);

        Ugraf gr = getTranspose();

        for (int i = 0; i < V; i++)
            visited[i] = false;

        while (stack.empty() == false) {
            int v = (int) stack.pop();

            if (visited[v] == false) {
                gr.DFSUtil(v, visited);
                System.out.println();
            }
        }
    }

    public static void main(String args[]) throws IOException {
        URL url = new URL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g6");
        int antLinjer = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String antallnoder = br.readLine();
        String[] array1 = antallnoder.split(" ", 0);
        int antallNoder = Integer.parseInt(array1[0].trim());
        String line;
        Ugraf g = new Ugraf(antallNoder);

        while ((line = br.readLine()) != null) {
            String[] array = line.split(" ", 0);
            g.leggTilKant(Integer.parseInt(array[0].trim()), Integer.parseInt(array[1].trim()));
        }
        System.out.println("Følgende er sterk sammenhengende komponenter:" + " ");
        g.printSterkSammenhengende();
    }
}
