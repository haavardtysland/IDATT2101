package Oblig8;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.sql.SQLOutput;
import java.util.*;

/**
 * Klassen graf som representerer grafen med alle nodene og kantene
 */

public class Graf {
    private int N, K, P; //N = antall noder K= antall kanter, P = antall interessepunkter
    private Node[] node;

    PriorityQueue<Node> priorityQueue;

    public Graf(BufferedReader nodefil, BufferedReader kartfil, BufferedReader interessepunkt) throws IOException {
        fyllNodefil(nodefil); //Henter info fra nodefilen
        fyllKartfil(kartfil); //Henter info fra kartfil
        fyllInteressepunkt(interessepunkt); //Henter info fra interessepunktfilen
    }

    /**
     * Metode for å resette grafen, ettersom man gjerne vil gjøre flere ting med grafen i en kjøring for å spare tid
     */
    public void reset() {
        for(Node node : node) {
            node.sluttNode = false;
            node.data = new Forgjenger();
            node.besokt = false;
        }
    }

    /**
     * Metode for å fylle grafen med nodefilen
     * @param br en fil med nodenr, breddegrad, lengdegrad
     * @throws IOException
     */
    public void fyllNodefil(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        node = new Node[N];

        for (int i = 0; i < N; ++i) {
            st = new StringTokenizer(br.readLine());
            int nodenr = Integer.parseInt(st.nextToken());
            double breddegrad = Double.parseDouble(st.nextToken());
            double lengdegrad = Double.parseDouble(st.nextToken());
            node[i] = new Node(nodenr, breddegrad, lengdegrad);
            node[i].data = new Forgjenger();
            node[i].cosBreddegrad = Math.cos(breddegrad);
        }
    }

    /**
     * Metode for å fylle grafen med kantfil
     * @param br en fil med franode, tilnode, kjøretid, lengde, fartsgrense
     * @throws IOException
     */
    public void fyllKartfil(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        K = Integer.parseInt(st.nextToken());
        for (int i = 0; i < K; ++i) {
            st = new StringTokenizer(br.readLine());
            int franode = Integer.parseInt(st.nextToken());
            int tilnode = Integer.parseInt(st.nextToken());
            int kjoeretid  = Integer.parseInt(st.nextToken());
            int lengde = Integer.parseInt(st.nextToken());
            int fartsgrense = Integer.parseInt(st.nextToken());
            Vkant k = new Vkant(node[tilnode], (Vkant)node[franode].kant1, kjoeretid, lengde, fartsgrense);
            node[franode].kant1 = k;
        }
    }

    /**
     * En metode for å fylle interresepunktfilen
     * @param br en fil med nodenr, kode, navn
     * @throws IOException
     */
    public void fyllInteressepunkt(BufferedReader br) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        P = Integer.parseInt(st.nextToken());

        for (int i = 0; i < P; ++i) {
            st = new StringTokenizer(br.readLine());
            int nodenr = Integer.parseInt(st.nextToken());
            int kode = Integer.parseInt(st.nextToken());
            String navn = "";
            while (st.hasMoreTokens()) {
                navn += st.nextToken() + " ";
            }
            node[nodenr].setNavn(navn);
            node[nodenr].setKode(kode);
        }
    }

    /**
     * Metode for å estimere distanse mellom to noder
     * Gitt i oppgaveteksten
     * @param node1
     * @param node2
     * @return
     */
    private int finnDistanse(Node node1, Node node2) {
        double sinBredde = Math.sin(((node1.breddeRad) - node2.breddeRad) / 2.0);
        double sinLengde = Math.sin((node1.lengdeRad - node2.lengdeRad) / 2.0);
        return (int) (35285538.46153846153846153846 * Math.asin(Math.sqrt(
                sinBredde * sinBredde + node1.cosBreddegrad * node2.cosBreddegrad * sinLengde * sinLengde)));
    }

    /**
     * Metode for å få prioritetskø med dijkstra
     * @return en prioritetskø med Node
     */
    private PriorityQueue<Node> getDijkstraPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.distanse)));
    }

    /**
     * Metode for å få en prioritetskø med Astar
     * @return en prirotietskø med Node
     */
    private PriorityQueue<Node> getAstarPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.fullDistanse)));
    }

    /**
     * Metode for å finne de 10 nærmeste gitt en kode, f.eks 2(bensinstasjon)
     * @param startNode noden vi vil finne de nærmeste fra
     * @param kode koden på den typen sted vi vil finne
     * @return
     */
    private Node[] dijkstraNaermesteVedKode(Node startNode, int kode) {
        startNode.data.distanse = 0;
        PriorityQueue<Node> queue = getDijkstraPriorityQueue();
        queue.add(startNode);
        Node[] naermest = new Node[10];
        int ikkeFunnet = 0;
        for (int i = N; i > 1 && !queue.isEmpty(); --i) {
            Node node = queue.poll();
            if (!node.besokt && (node.kode == kode || ((kode == 2 || kode == 4) && node.kode == 6))) {
                naermest[ikkeFunnet] = node;
                ikkeFunnet++;
                node.besokt = true;
            }
            if (ikkeFunnet == 10) break;
            for (Vkant kant = node.kant1; kant != null; kant = (Vkant) kant.neste) {
                forkort(node, kant, queue);
            }
        }
        return naermest;
    }

    /**
     * Metode for å skrive ut de nærmeste 10
     * @param startNodeNr noden vi vil finne de nærmeste fra
     * @param kode koden på den typen vi vil finne
     */
    public void finnNaermesteDijkstra(int startNodeNr, int kode) {
        Node[] nearmesteNoder = dijkstraNaermesteVedKode(node[startNodeNr], kode);
        String type;
        if(kode == 6) {
            type = "ladestasjoner og bensistasjoner";
        } else if(kode == 1){
            type = "steder";
        } else {
            type = kode == 2 ? "bensinstasjoner" : "ladestasjoner";
        }
        System.out.println("Nærmeste " + type + " ved " + node[startNodeNr].navn);
        for (Node node : nearmesteNoder) {
            if (node != null) System.out.println(node.navn + " " + node.kode);
        }
        System.out.println("Lokasjoner: ");
        for (Node node : nearmesteNoder) {
            if (node != null)
                System.out.println(node.breddegrad + ", " + node.lengdegrad + ", " + node.navn);
        }
    }

    /**
     * Metode for å finne raskeste vei ved å bruke Dijkstras algoritme
     * @param startNode startnoden
     * @param sluttNode sluttnoden
     * @return antall noder besøkt
     */

    private int dijkstraFraTil(Node startNode, Node sluttNode) {
        startNode.data.distanse = 0;
        sluttNode.sluttNode = true;
        PriorityQueue<Node> queue = getDijkstraPriorityQueue();
        queue.add(startNode);
        int noderSjekket = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            noderSjekket++;
            if (node.sluttNode) return noderSjekket;
            for (Vkant kant = node.kant1; kant != null; kant = (Vkant)kant.neste) {
                forkort(node, kant, queue);
            }
        }
        return -1;
    }

    /**
     * Metode for å finne den raskeste veien ved å bruke Astar algoritme
     * @param startNode startnoden
     * @param sluttNode sluttnoden
     * @return antall noder besøkt
     */
    private int astarFraTil(Node startNode, Node sluttNode) {
        startNode.data.distanse = 0;
        startNode.data.distanseTilSlutt = finnDistanse(startNode, sluttNode);
        startNode.data.fullDistanse = startNode.data.distanseTilSlutt;
        sluttNode.sluttNode = true;
        PriorityQueue<Node> queue = getAstarPriorityQueue();
        queue.add(startNode);
        int noderSjekket = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            noderSjekket++;
            if (node.sluttNode) return noderSjekket;
            for (Vkant kant = node.kant1; kant != null; kant = (Vkant)kant.neste) {
                forkort(node, kant, sluttNode, queue);
            }
        }
        return -1;
    }

    /**
     * Metode for å skrive ut den raskeste ruten med Dijkstras algoritme
     * @param fra nr til startnoden
     * @param til nr til sluttnoden
     */
    public void finnRuteMedDijkstra(int fra, int til) {
        Node startNode = node[fra];
        Node sluttNode = node[til];
        long startTid = System.nanoTime();
        int sjekket = dijkstraFraTil(startNode, sluttNode);
        long tid = System.nanoTime() - startTid;
        System.out.println("Dijkstra: " + sjekket + " noder sjekket, " + (double) tid / 1000000 + "ms brukt.");
        printRuteTilFil(startNode, sluttNode);
    }

    /**
     * Metode for å skrive ut den raskeste ruten med Astar algoritme
     * @param fra nr til startnode
     * @param til nr til sluttnode
     */
    public void finnRuteMedAstar(int fra, int til) {
        Node startNode = node[fra];
        Node sluttNode = node[til];
        long startTid = System.nanoTime();
        int sjekket = astarFraTil(startNode, sluttNode);
        long tid = System.nanoTime() - startTid;
        System.out.println("Astar: " + sjekket + " noder sjekket, " + (double) tid / 1000000 + "ms brukt.");
        printRuteTilFil(startNode, sluttNode);
    }


     /**
     * Metode for å skrive ruten til fil, formatet er breddegrad, lengdegrad
     * @param startNode noden i startpunktet
     * @param sluttNode noden i sluttpunktet
     * @throws IOException
     */
    private void printRuteTilFil(Node startNode, Node sluttNode ) {
        String startNavn = startNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String sluttNavn = sluttNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String filNavn = startNavn + "-" + sluttNavn + ".txt";
        Node node = sluttNode;
        try ( FileWriter outputStream = new FileWriter(filNavn)) {
            int millisekund = node.data.distanse * 10;
            int sekund = (millisekund / 1000) % 60;
            int minutt = ((millisekund / (1000 * 60)) % 60);
            int timer = ((millisekund / (1000 * 60 * 60)) % 24);
            System.out.println("Tid: " + String.format("%02d timer, %02d minutt, %02d sekund", timer, minutt, sekund));
            while (node != null) {
                outputStream.write(node.toString() + "\n");
                node = node.data.forgjenger;
            }
            System.out.println("Ruten er skrevet til filen:  " + filNavn + "\n");
            System.out.println("Formatet er slik at filen kan brukes til å plotte inn grafisk ved nettsiden maps.co");
        } catch(IOException e) {
            System.out.println("Kunne ikke finne/printe ruten");
        }
    }

    /**
     * Metode for å forkorte til den korteste vei er funnet
     * @param node går gjennom nodene
     * @param kant denne nodens kant
     * @param queue prioritetskøen, de besøkte nodene blir lagt til her og prioritert ved å sette de raskeste høyest
     */
    void forkort(Node node, Vkant kant, PriorityQueue<Node> queue) {
        Forgjenger nodeForgjenger = node.data;
        Forgjenger nesteForgjenger = kant.til.data;
        if (nesteForgjenger.distanse > nodeForgjenger.distanse + kant.kjoeretid) {
            nesteForgjenger.distanse = nodeForgjenger.distanse + kant.kjoeretid;
            nesteForgjenger.forgjenger = node;
            queue.remove(kant.til);
            queue.add(kant.til);
        }
    }

    /**
     * Metode for å forkorte til den korteste vei er funnet
     * @param startNode startNoden
     * @param kant kanten
     * @param sluttNode sluttNoden
     * @param queue prioritetskøen, de besøkte nodene blir lagt til her og prioritert ved å sette de raskeste høyest
     */
    void forkort(Node startNode, Vkant kant, Node sluttNode, PriorityQueue<Node> queue) {
        Forgjenger startForgjenger = startNode.data;
        Forgjenger sluttForgjenger = kant.til.data;
        if(sluttForgjenger.distanseTilSlutt == -1) {
            int distanse = finnDistanse(kant.til, sluttNode);
            sluttForgjenger.distanseTilSlutt = distanse;
            sluttForgjenger.fullDistanse = distanse + sluttForgjenger.distanse;
        }
        if(sluttForgjenger.distanse > startForgjenger.distanse + kant.kjoeretid) {
            sluttForgjenger.distanse = startForgjenger.distanse + kant.kjoeretid;
            sluttForgjenger.forgjenger = startNode;
            sluttForgjenger.fullDistanse = sluttForgjenger.distanse + sluttForgjenger.distanseTilSlutt;
            queue.add(kant.til);
        }
    }
}
