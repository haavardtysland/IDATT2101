package Oblig7;


import java.io.*;
import java.util.*;

  public class Huffman {
    String codedString = "";

    public Huffman() {
    }

    public void huffmannCompression(File file, String outputPath) throws IOException {
      DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
      DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(outputPath)));
      byte[] array = new byte[dis.available()];
      dis.readFully(array);
      int[] frequencies =  frequencies(array);
      Node root = buildHuffmanTree(frequencies);
      String[] st = new String[256];
      buildCode(st, root, "");
      for(int i = 0; i < array.length; i++) {
        if(array[i] >= 0 && array[i] <=256) {
          int j = array[i];
          String characterCode = st[j];
          codedString += characterCode;
        }
      }
      for (int i  = 0; i < frequencies.length; i++) {
        dos.write(frequencies[i]);
      }
      dos.write(codedString.getBytes());
      dos.close();
    }

    public void huffmannDecompression(File file, String outputPath) throws IOException {
      DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
      DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(outputPath)));
      byte[] array = new byte[dis.available()];
      dis.readFully(array);
      Node root = buildHuffmanTree(frequenciesDecompression(array));
      String[] st = new String[256];
      buildCode(st, root, "");
      int hore = 0;
      for(int i = 0; i < array.length; i++) {
        if(array[i] >= 0 && array[i] <= 256){
          int j = array[i];
          String characterCode = st[j];
          codedString += characterCode;
        }
      }
      dos.write(codedString.getBytes());
      dos.close();
    }

    public int[] frequenciesDecompression(byte[] array) {
      int [] frequencies = new int[256];
      for(int i = 0; i < 256; i++) {
        frequencies[i] = array[i];
      }
      return frequencies;
    }


    private static void buildCode(String[] st, Node x, String s) {
      if (!x.isLeaf()) {
        buildCode(st, x.leftChild,  s + '0');
        buildCode(st, x.rightChild, s + '1');
      }
      else {
        st[x.character] = s;
      }
    }


    private Node buildHuffmanTree(int[] frequencies) {
      PriorityQueue<Node> pq = new PriorityQueue<>();
      for (int i = 0; i < frequencies.length; i++) {
        if (frequencies[i] != 0) {
          char c = (char) i;
          Node node = new Node(c, frequencies[i]);
          pq.add(node);
        }
      }
      if(pq.size() == 1) pq.add(new Node('\0', 1));
      while (pq.size() > 1) {
        Node left = pq.poll();
        Node right = pq.poll();
        Node parent = new Node('\0', left.count + right.count, left, right);
        pq.add(parent);
      }
      return pq.poll();
    }

    private static int[] frequencies(byte[] array) {
      int frekvenser[] = new int[256];
      for (int i = 0; i < array.length; i++) {
        if(array[i] > 0 && array[i] <=256) {
          int j = array[i];
          frekvenser[j]++;
        }
      }
      return frekvenser;
    }


  public static void main(String[] args) throws IOException {
    Huffman huff = new Huffman();
    File uncompressed = new File("test.txt");
    huff.huffmannCompression(uncompressed, "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\ferdigtest.txt");
    File compressed = new File("ferdigtest.txt");
    huff.huffmannDecompression(compressed, "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\dekomprimert.txt");

  }
}
