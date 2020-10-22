package Oblig7;

public class Node implements Comparable<Node> {
  char character;
  int count;
  Node leftChild;
  Node rightChild;
  String code;


  public Node(char character, int count, Node left, Node right) {
    this.character = character;
    this.count = count;
    this.leftChild = left;
    this.rightChild = right;
    code = "";
  }

  private void addCode(char c) {
    this.code = this.code + c;
  }

  public Node(char character, int count) {
    this.character = character;
    this.count = count;
    this.leftChild = null;
    this.rightChild = null;
    code = "";
  }

  public boolean isLeaf() {
    if (leftChild == null && rightChild == null) {
      return true;
    }
    return false;
  }





  @Override
  public int compareTo(Node n) {
    int frequencyComparison = Integer.compare(this.count, n.count);
    if(frequencyComparison != 0) return frequencyComparison;
    return Integer.compare(this.character, n.character);
  }
}
