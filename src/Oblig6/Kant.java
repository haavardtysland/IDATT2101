package Oblig6;

class Kant {
  Kant neste;
  Node til;
  public Kant(Node n, Kant nst) {
    til = n;
    neste = nst;
  }
}