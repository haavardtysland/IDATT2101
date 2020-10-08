package Oblig6;

class Vkant extends Kant {
  int distanse;
  public Vkant(Node n, Oblig6.Vkant nst, int distanse) {
    super(n, nst);
    this.distanse = distanse;
  }
}