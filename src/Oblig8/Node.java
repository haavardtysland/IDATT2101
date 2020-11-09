package Oblig8;

class Node {
    Vkant kant1;
    Forgjenger data;
    int nodeNummer;
    double breddegrad;
    double lengdegrad;
    double cosBreddegrad;
    boolean besokt;
    boolean sluttNode;
    String navn;
    int kode;

    public Node(int nodeNummer, double breddegrad, double lengdegrad) {
        this.nodeNummer = nodeNummer;
        this.breddegrad = breddegrad;
        this.lengdegrad = lengdegrad;
        this.besokt = false;
        this.cosBreddegrad = Math.cos(breddegrad);

    }

    public void setNavn(String navn){
        this.navn = navn;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

}
