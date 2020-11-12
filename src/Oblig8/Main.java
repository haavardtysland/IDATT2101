package Oblig8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        String noder = "nodefil.txt";
        String kanter = "kantfil.txt";
        String interessepunkt = "interessepunkt.txt";

        int v = 6198111; //VÆRNES
        int k = 6013683; //Kårvåg
        int g = 6225195; //Gjemnes
        int t = 2399829; //Trondheim
        int h = 1221382; //Helsinki
        int r = 1119181; //Røros


        try {
            BufferedReader nodeLeser = new BufferedReader(new FileReader(new File(noder)));
            BufferedReader kantLeser = new BufferedReader(new FileReader(new File(kanter)));
            BufferedReader interesseLeser = new BufferedReader(new FileReader(new File(interessepunkt)));

            Graf graf = new Graf(nodeLeser, kantLeser, interesseLeser);

            Scanner sc = new Scanner(System.in);
            boolean run = true;

            while(run) {
                System.out.println("Skriv inn det tallet som hører til det du ønsker å gjøre");
                System.out.println("1. Rakeste vei fra et punkt til et annet med Dijkstra \n2. Raskeste vei fra et punkt til et annet med Astar\n3. Finne 10 nærmeste av en type fra et sted\n4. Avslutt");
                int action = sc.nextInt();
                if(action == 1) {
                    graf.finnRuteMedDijkstra(k, g);
                    graf.reset();
                    graf.finnRuteMedDijkstra(t, h);
                    graf.reset();
                 } else if(action == 2) { ;
                    graf.finnRuteMedAstar(k, g);
                    graf.reset();
                    graf.finnRuteMedAstar(t, h);
                    graf.reset();
                } else if(action == 3) {
                    graf.finnNaermesteDijkstra(v, 2);
                    graf.reset();
                    graf.finnNaermesteDijkstra(r, 4);
                } else if(action == 4) {
                    System.out.println("Avslutter...");
                    run =false;
                } else {
                    System.out.println("Skriv inn ett tall mellom 1 og 4");
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
