package Oblig6;

class Kant {
  Oblig6.Kant neste;
  Node til;
  public Kant(Node n, Oblig6.Kant nst) {
    til = n;
    neste = nst;
  }
}