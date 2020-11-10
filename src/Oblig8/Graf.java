package Oblig8;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.util.*;

public class Graf {
    private int N, K, P; //N = antall noder K= antall kanter, P = antall interessepunkter
    private Node[] node;
    HashMap<String, Integer> steder = new HashMap<>();

    PriorityQueue<Node> priorityQueue;

    public Graf(BufferedReader nodefil, BufferedReader kartfil, BufferedReader interessepunkt) throws IOException {
        fyllNodefil(nodefil); //Henter info fra nodefilen
        fyllKartfil(kartfil); //Henter info fra kartfil
        fyllInteressepunkt(interessepunkt); //Henter info fra interessepunktfilen
    }

    //Metode for å "resette" grafen, hvis man vil gjøre noe med grafen flere ganger i samme kjøring
    public void reset() {
        for(Node node : node) {
            node.sluttNode = false;
            node.data = new Forgjenger();
            node.besokt = false;
        }
    }

    //Fyller node med info fra nodefilen
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

    //Fyller node med info fra kartfil
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

    //Fyller node med info fra interessepunktfil
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
            steder.put(navn, nodenr);
        }
    }

    private int finnDistanse(Node node1, Node node2) {
        double sinBredde = Math.sin(((node1.breddeRad) - node2.breddeRad) / 2.0);
        double sinLengde = Math.sin((node1.lengdeRad - node2.lengdeRad) / 2.0);
        return (int) (35285538.46153846153846153846 * Math.asin(Math.sqrt(
                sinBredde * sinBredde + node1.cosBreddegrad * node2.cosBreddegrad * sinLengde * sinLengde)));
    }

    private PriorityQueue<Node> getDijkstraPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.distanse)));
    }

    private PriorityQueue<Node> getAstarPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.fullDistanse)));
    }

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

    public void finnNaermesteDijkstra(int startNodeNr, int kode) {
        Node[] nearmesteNoder = dijkstraNaermesteVedKode(node[startNodeNr], kode);
        for (Node node : nearmesteNoder) {
            if (node != null) System.out.println(node.navn + " " + node.kode);
        }
        System.out.println("Lokasjoner: ");
        for (Node node : nearmesteNoder) {
            if (node != null)
                System.out.println(node.breddegrad * (180 / Math.PI) + ", " + node.lengdegrad * (180 / Math.PI));
        }
    }

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

    public void finnRuteMedDijkstra(int fra, int til) {
        Node startNode = node[fra];
        Node sluttNode = node[til];
        long startTid = System.nanoTime();
        int sjekket = dijkstraFraTil(startNode, sluttNode);
        long tid = System.nanoTime() - startTid;
        System.out.println("Dijkstra: " + sjekket + " noder sjekket, " + (double) tid / 1000000 + "ms brukt.");
        printRute(startNode, sluttNode);
    }

    public void finnRuteMedAstar(int fra, int til) {
        Node startNode = node[fra];
        Node sluttNode = node[til];
        long startTid = System.nanoTime();
        int sjekket = astarFraTil(startNode, sluttNode);
        long tid = System.nanoTime() - startTid;
        System.out.println("Astar: " + sjekket + " noder sjekket, " + (double) tid / 1000000 + "ms brukt.");
        printRute(startNode, sluttNode);
    }

    private void printRute(Node startNode, Node sluttNode) {
        String startNavn = startNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String sluttNavn = sluttNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String filNavn = startNavn + "-" + sluttNavn + ".txt";
        try (FileWriter outputStream = new FileWriter(filNavn)) {
            Node node = sluttNode;
            int millisekund = node.data.distanse * 10;
            int sekund = (millisekund / 1000) % 60;
            int minutt = ((millisekund / (1000 * 60)) % 60);
            int timer = ((millisekund / (1000 * 60 * 60)) % 24);
            System.out.println("Tid: " + String.format("%02d timer, %02d minutt, %02d sekund", timer, minutt, sekund));
            while (node != null) {
                outputStream.write(node.toString() + "\n");
                node = node.data.forgjenger;
            }
            System.out.println("Ruten har blitt printet til filen: " + filNavn);
        } catch (IOException e) {
            System.out.println("ERROR: Kunne ikke finne/printe ruten");
        }
    }


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
