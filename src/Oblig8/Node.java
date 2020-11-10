package Oblig8;

class Node {
    Vkant kant1;
    Forgjenger data;
    int nodeNummer;
    double breddegrad;
    double lengdegrad;
    double cosBreddegrad;
    double breddeRad; //breddegrad i radianer
    double lengdeRad; //lengdegrad i radianer
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
        this.breddeRad = breddegrad * Math.PI / 180;
        this.lengdeRad = lengdegrad * Math.PI / 180;
        this.navn = "";

    }

    public void setNavn(String navn){
        this.navn = navn;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    @Override
    public String toString() {
        return lengdegrad + ", " + breddegrad  +", " + navn;
    }
}
