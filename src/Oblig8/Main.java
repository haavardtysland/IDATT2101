package Oblig8;

import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {
        URL nodefil = new URL("http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/island/noder.txt");
        URL kartfil = new URL("http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/island/kanter.txt");
        URL interessepunkt = new URL("http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/island/interessepkt.txt");
        Graf graf = new Graf(nodefil, kartfil,interessepunkt);
        graf.findNearestByTypeWithDijkstra(0, 2);

    }
}
