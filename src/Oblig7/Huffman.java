//package Oblig7;

import java.io.*;
import java.util.Arrays;

public class Huffman {

  int[] frekvenser = new int[256];
  int charAmount;
  BitString[] huffmanTable = new BitString[256];

  byte[] byteArray;


  public Huffman() {}

  public void writeCompressedFile(String path, String outputPath) throws IOException {
    readNewFile(path);
    charAmount = countCharacters();
    generateHuffmanTable();
    byte[] bytesToBeWritten = createCompressedBytes();

    FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
    DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);


    for(int i = 0; i < frekvenser.length; i ++){
      dataOutputStream.writeInt(frekvenser[i]);
    }
    dataOutputStream.write(bytesToBeWritten);
    dataOutputStream.flush();
    dataOutputStream.close();
  }

  public void decodeCompressedFiled(String path, String outputPath) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(path);
    DataInputStream dataInputStream = new DataInputStream(fileInputStream);
    byte[] compressedData;

    for(int i = 0; i < frekvenser.length; i ++){
      frekvenser[i] = dataInputStream.readInt();
    }

    int bytesLeft = fileInputStream.available();
    compressedData = new byte[bytesLeft];
    dataInputStream.read(compressedData);

    charAmount = countCharacters();
    Heap huffmanTree = createHuffmanTree();
    byte[] decompressedText = new byte[Arrays.stream(frekvenser).sum()];

    int byteIndexCounter = 0;

    Node root = huffmanTree.node[0];
    for(int i = 0; i < compressedData.length - 1; i ++){
      int bits;
      if(i == compressedData.length - 2){
        bits = compressedData[compressedData.length-1]-1;
      }else{
        bits = 7;
      }
      for(int b = bits; b > -1; b --){
        int bit = ((compressedData[i] + 256) >> b) & (0b00000001);
        if(bit == 0){
          root = root.left;
          if(root.left == null && root.right == null){
            decompressedText[byteIndexCounter] = (byte) root.character;
            byteIndexCounter++;
            root = huffmanTree.node[0];
          }
        }else{
          root = root.right;
          if(root.left == null && root.right == null){
            decompressedText[byteIndexCounter] = (byte) root.character;
            byteIndexCounter++;
            root = huffmanTree.node[0];
          }
        }
      }
    }

    FileOutputStream output = new FileOutputStream(outputPath);
    DataOutputStream outputStream = new DataOutputStream(output);
    outputStream.write(decompressedText);
    outputStream.flush();
    outputStream.close();
  }


  private byte[] createCompressedBytes(){
    double bitLength = 0;
    for(int i = 0; i < byteArray.length; i ++){
      bitLength += huffmanTable[byteArray[i] & 0xff].bitsAmount;
    }

    int byteSize;
    if((bitLength/8) % 1 == 0){
      byteSize = (int) (bitLength/8);
    }else{
      byteSize = (int) (bitLength/8) + 1;
    }

    byte[] byteToBeSent = new byte[byteSize+1];

    int byteCounter = 0;
    int spaceLeft = 8;
    int lastByteCounter = 0;

    for(int i = 0; i < byteArray.length; i ++) {
      BitString bitString = huffmanTable[byteArray[i] & 0xff];
      for (int b = bitString.bitsAmount - 1; b > -1; b--) {
        byte bitToAdd = (byte) ((bitString.bitString >> b) & 1);
        if (spaceLeft == 0) {
          byteCounter++;
          byteToBeSent[byteCounter] = bitToAdd;
          spaceLeft = 8;
        } else {
          byteToBeSent[byteCounter] = (byte) (byteToBeSent[byteCounter] << 1);
          byteToBeSent[byteCounter] |= bitToAdd;
        }
        if(byteCounter == byteSize - 1){
          lastByteCounter ++;
        }
        spaceLeft--;
      }
    }

    byteToBeSent[byteToBeSent.length-1] = (byte) lastByteCounter;
    return byteToBeSent;
  }

  private Heap createHuffmanTree(){
    Heap huffmanHeap = fillHeap();

    while(huffmanHeap.length != 1){
      Node node1 = huffmanHeap.getMin();
      Node node2 = huffmanHeap.getMin();
      Node newNode = new Node(0, node1.occurrences + node2.occurrences);
      newNode.left = node1;
      newNode.right = node2;
      huffmanHeap.addNode(newNode);
    }
    return huffmanHeap;
  }

  private void generateHuffmanTable(){
    Heap huffmanHeap = createHuffmanTree();
    generateHuffmanTable(huffmanHeap.node[0], "");
  }


  private void generateHuffmanTable(Node root, String s){
    if(root.left == null && root.right == null){
      huffmanTable[root.character] = new BitString(s.length(), root.character, Long.parseLong(s, 2));
      return;
    }

    generateHuffmanTable(root.left, s + "0");
    generateHuffmanTable(root.right, s + "1");
  }


  private void readNewFile(String path) throws IOException {
    resetFrequencyArray();

    InputStream input = new FileInputStream(path);
    DataInputStream dataInputStream = new DataInputStream(input);
    int count = input.available();
    byteArray = new byte[count];

    dataInputStream.read(byteArray);
    for(byte b : byteArray){
      frekvenser[b & 0xff]++;
    }
  }

  private Heap fillHeap(){
    Heap heap = new Heap(charAmount);
    int counter = 0;
    for(int i = 0; i < frekvenser.length; i ++){
      if(frekvenser[i] != 0){
        Node node = new Node(i, frekvenser[i]);
        heap.node[counter] = node;
        counter++;
      }
    }
    heap.createHeap();
    return heap;
  }

  private int countCharacters(){
    int chars = 0;
    for(int i = 0; i < frekvenser.length; i ++){
      if(frekvenser[i] != 0){
        chars++;
      }
    }
    return chars;
  }

<<<<<<< HEAD
  private void resetFrequencyArray(){
    frekvenser = new int[256];
=======
  public static void main(String[] args) throws IOException {
    Huffman huff = new Huffman();
    File uncompressed = new File("test.txt");
    huff.huffmannCompression(uncompressed, "/Users/havardtysland/Documents/Dataing 3. semester/Algoritmer og datastrukturer/AlgDatØvinger/ferdigtest.txt");
    File compressed = new File("ferdigtest.txt");
    //huff.huffmannDecompression(compressed, "C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\dekomprimert.txt");

>>>>>>> 9f65a6d4009ac0af058a6843b78fcb67efdc9e1d
  }

}
