package Oblig6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Graf {
  private int N, K; //N = antall noder, K = antall kanter
  private Node[] node;

  PriorityQueue<Node> priorityQueue;

  public Graf(URL url) throws IOException {
    ny_vgraf(url); //Henter graf fra en url

    priorityQueue = new PriorityQueue<>(N, new Comparator<Node>() {
      @Override
      public int compare(Node node1, Node node2) {
        return (node1.forgjenger.distanse - node2.forgjenger.distanse);
      }
    });
  }

  public void ny_vgraf(URL url) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    node = new Node[N];

    for(int i = 0; i< N; ++i) {
      node[i] = new Node();
      node[i].nodeNummer = i;
    }

    K = Integer.parseInt(st.nextToken());
    for(int i = 0; i < K; ++i) {
      st = new StringTokenizer(br.readLine());
      int fra = Integer.parseInt(st.nextToken());
      int til = Integer.parseInt(st.nextToken());
      int vekt = Integer.parseInt(st.nextToken());
      Vkant k = new Vkant(node[til], (Vkant)node[fra].kant1, vekt);
      node[fra].kant1 = k;
    }
  }

  public void skrivUtGraf(int start) {

    System.out.println("Node  |  Forgjenger  |  Distanse");
    for (int i = 0; i < N; i++) {
      if (i != start) {

        String distanse = "                       nåes ikke";

        if (node[i].forgjenger.finnDistanse() != Forgjenger.uendelig) {
          distanse = "             " + node[i].forgjenger.finnDistanse();
        }

        if (node[i].forgjenger.finn_forgj() != null) {
          System.out.println(node[i].nodeNummer + "            " + node[i].forgjenger.finn_forgj().nodeNummer + distanse);
        } else {
          System.out.println(node[i].nodeNummer + distanse);
        }

      } else {
        System.out.println(start + "          start           0");
      }
    }
  }

  private void initForgjenger(Node s) {
    for(int i = N; i --> 0;) {
      node[i].forgjenger = new Forgjenger();
    }
    s.forgjenger.distanse = 0;
  }

  private void forkort(Node n, Vkant kant) {
    Forgjenger nd = n.forgjenger;
    Forgjenger md = kant.til.forgjenger;

    if(md.distanse > nd.distanse + kant.distanse) {
      md.distanse = nd.distanse + kant.distanse;
      md.forgjenger = n;
      priorityQueue.remove(kant.til);
      priorityQueue.add(kant.til);
    }
  }

  public void dijkstra(int startNummmer) {
    Node node1 = node[startNummmer];
    initForgjenger(node1);

    priorityQueue.addAll(new ArrayList<Node>(Arrays.asList(node)));

    for(int i = N; i > 1; --i) {

      Node n = priorityQueue.poll();
      for(Vkant k = n.kant1; k != null; k = (Vkant)k.neste) {
        forkort(n, k);
      }
    }
    skrivUtGraf(startNummmer);
  }
}