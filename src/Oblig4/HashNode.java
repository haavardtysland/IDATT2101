package Oblig4;

public class HashNode {
    String data;
    HashNode neste;

    public HashNode(String data) {
      this.data = data;
    }

    public void setNeste(HashNode neste) {
      this.neste = neste;
    }
}
