package Oblig8;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Graf {
    private int N, K; //N = antall noder K= antall kanter
    private Node[] node;

    PriorityQueue<Node> priorityQueue;

    public Graf(URL nodefil, URL kartfil, URL interessepunkt) throws IOException {
        fyllNodefil(nodefil); //Henter info fra nodefilen
        fyllKartfil(kartfil); //Henter info fra kartfil
        fyllInteressepunkt(interessepunkt); //Henter info fra interessepunktfilen
    }

    public void fyllNodefil(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
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
        }
    }

    public void fyllKartfil(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
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


    public void fyllInteressepunkt(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int antall = Integer.parseInt(st.nextToken());

        for (int i = 0; i < antall; i++) {
            st = new StringTokenizer(br.readLine());
            int nodenr = Integer.parseInt(st.nextToken());
            int kode = Integer.parseInt(st.nextToken());
            String navn = "";
            while (st.hasMoreTokens()) {
                navn +=  st.nextToken() + " ";
            }
            Node n = node[nodenr];
            n.setNavn(navn);
            n.setKode(kode);
        }
    }

    private PriorityQueue<Node> getDijkstraPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.distanse)));
    }

    private PriorityQueue<Node> getAstarPriorityQueue() {
        return new PriorityQueue<>(Comparator.comparingInt(a -> (a.data.fullDistanse)));
    }

    private Node[] dijkstraNearestByType(Node startNode, int type) {
        startNode.data.distanse = 0;
        PriorityQueue<Node> queue = getDijkstraPriorityQueue();
        queue.add(startNode);
        Node[] nearest = new Node[10];
        int noFound = 0;
        for (int i = N; i > 1 && !queue.isEmpty(); --i) {
            Node node = queue.poll();
            if (!node.besokt && (node.kode == type || ((type == 2 || type == 4) && node.kode == 6))) {
                nearest[noFound] = node;
                noFound++;
                node.besokt = true;
            }
            if (noFound == 10) break;
            for (Vkant edge = node.kant1; edge != null; edge = (Vkant) edge.neste) {
                forkort(node, edge, queue);
            }
        }
        return nearest;
    }

    public void findNearestByTypeWithDijkstra(int startNodeNr, int type) {
        Node[] nearestNodes = dijkstraNearestByType(this.node[startNodeNr], type);
        for (Node node : nearestNodes) {
            if (node != null) System.out.println(node.navn + " " + node.kode);
        }
        System.out.println("Locations: ");
        for (Node node : nearestNodes) {
            if (node != null)
                System.out.println(node.breddegrad * (180 / Math.PI) + ", " + node.lengdegrad * (180 / Math.PI));
        }
    }

    private int dijkstraFromTo(Node startNode, Node endNode) {
        startNode.data.distanse = 0;
        endNode.sluttNode = true;
        PriorityQueue<Node> queue = getDijkstraPriorityQueue();
        queue.add(startNode);
        int count = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            count++;
            if (node.sluttNode) return count;
            for (Vkant edge = node.kant1; edge != null; edge = (Vkant)edge.neste) {
                forkort(node, edge, queue);
            }
        }
        return -1;
    }

    private int astarFromTo(Node startNode, Node endNode) {
        startNode.data.distanse = 0;
        startNode.data.distanseTilSlutt = finnDistanse(startNode, endNode);
        startNode.data.fullDistanse = startNode.data.distanseTilSlutt;
        endNode.sluttNode = true;
        PriorityQueue<Node> queue = getAstarPriorityQueue();
        queue.add(startNode);
        int count = 0;
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            count++;
            if (node.sluttNode) return count;
            for (Vkant edge = node.kant1; edge != null; edge = (Vkant)edge.neste) {
                forkort(node, edge, endNode, queue);
            }
        }
        return -1;
    }

    public void findRouteWithDijkstra(int from, int to) {
        Node startNode = this.node[from];
        Node endNode = this.node[to];
        long startTime = System.nanoTime();
        int checked = dijkstraFromTo(startNode, endNode);
        long time = System.nanoTime() - startTime;
        System.out.println("Dijkstra: " + checked + " nodes checked, " + (double) time / 1000000 + "ms taken.");
        printRoute(startNode, endNode);
    }

    public void findRouteWithAstar(int from, int to) {
        Node startNode = this.node[from];
        Node endNode = this.node[to];
        long startTime = System.nanoTime();
        int checked = astarFromTo(startNode, endNode);
        long time = System.nanoTime() - startTime;
        System.out.println("Astar: " + checked + " nodes checked, " + (double) time / 1000000 + "ms taken.");
        printRoute(startNode, endNode);
    }

    private void printRoute(Node startNode, Node endNode) {
        String startName = startNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String endName = endNode.navn.replaceAll("[^a-zA-Z0-9]", "");
        String fileName = startName + "-" + endName + ".txt";
        try (FileWriter outputStream = new FileWriter(fileName)) {
            Node node = endNode;
            int milliseconds = node.data.distanse * 10;
            int seconds = (milliseconds / 1000) % 60;
            int minutes = ((milliseconds / (1000 * 60)) % 60);
            int hours = ((milliseconds / (1000 * 60 * 60)) % 24);
            System.out.println("Time: " + String.format("%02d hours, %02d min, %02d sec", hours, minutes, seconds));
            while (node != null) {
                outputStream.write(node.toString() + "\n");
                node = node.data.forgjenger;
            }
            System.out.println("The route has been printed to the file: " + fileName);
        } catch (IOException e) {
            System.out.println("ERROR: Couldn't find/print route.");
        }
    }


    private int finnDistanse(Node node1, Node node2) {
        double sinLat = Math.sin((node1.lengdegrad - node2.lengdegrad) / 2.0);
        double sinLng = Math.sin((node1.breddegrad - node2.breddegrad) / 2.0);
        return (int) (35285538.46153846153846153846 * Math.asin(Math.sqrt(
                sinLat * sinLat + node1.cosBreddegrad * node2.cosBreddegrad * sinLng * sinLng)));
    }

    void forkort(Node node, Vkant kant, PriorityQueue<Node> queue) {
        Forgjenger nodeForgjenger = node.data;
        Forgjenger nesteForgjenger = kant.til.data;
        if (nesteForgjenger.distanse > nodeForgjenger.distanse + kant.kjoeretid) {
            nesteForgjenger.distanse = nodeForgjenger.distanse + kant.kjoeretid;
            nesteForgjenger.forgjenger = node;
            queue.add(kant.til);
        }
    }

    void forkort(Node start, Vkant kant, Node slutt, PriorityQueue<Node> queue) {
        Forgjenger startForgjenger = start.data;
        Forgjenger sluttForgjenger = kant.til.data;
        if(sluttForgjenger.distanseTilSlutt == -1) {
            int distanse = finnDistanse(kant.til, slutt);
            sluttForgjenger.distanseTilSlutt = distanse;
            sluttForgjenger.fullDistanse = distanse + sluttForgjenger.distanse;
        }
        if(sluttForgjenger.distanse > startForgjenger.distanse + kant.kjoeretid) {
            sluttForgjenger.distanse = startForgjenger.distanse + kant.kjoeretid;
            sluttForgjenger.forgjenger = start;
            sluttForgjenger.fullDistanse = sluttForgjenger.distanse + sluttForgjenger.distanseTilSlutt;
            queue.add(kant.til);
        }
    }


}
