//package Oblig6;
import java.io.IOException;
import java.net.URL;


public class KortesteVei {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://www.iie.ntnu.no/fag/_alg/v-graf/vg1");
        Graf graf = new Graf(url);
        graf.dijkstra(1);
    }
}


