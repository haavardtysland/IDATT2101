package Oblig1;

import java.util.Date;
import java.util.function.BiFunction;

public class Rekursjon {

    public static void main(String[] args) {

        System.out.println("Test av metodene");
        System.out.println("Oppgave 1:" + oppgave1(2, 10));
        System.out.println("Oppgave 2:" + oppgave2(2, 10));
        System.out.println("Math.pow :" + javaMath(2,10));

        System.out.println("Oppgave 1 tid: ");
        testTid(1.001, 10,Rekursjon::oppgave1);
        testTid(1.001,100, Rekursjon::oppgave1);
        testTid(1.001, 1000, Rekursjon::oppgave1);
        testTid(1.001, 10000,Rekursjon::oppgave1);

        System.out.println("Oppgave 2 tid: ");
        testTid(1.001, 10,Rekursjon::oppgave2);
        testTid(1.001,100, Rekursjon::oppgave2);
        testTid(1.001, 1000, Rekursjon::oppgave2);
        testTid(1.001, 10000,Rekursjon::oppgave2);

        System.out.println("Math sin pow-metodes tid: ");
        testTid(1.001, 10,Rekursjon::javaMath);
        testTid(1.001,100, Rekursjon::javaMath);
        testTid(1.001, 1000, Rekursjon::javaMath);
        testTid(1.001, 10000,Rekursjon::javaMath);

    }




    private static double oppgave1(double x, int n) {
        /*if (n < 0 ) {
            throw new IllegalArgumentException("n kan ikke være negativ");
        }*/
        if (n == 0) {
            return 1;
        }
        return x * oppgave1(x, n-1); 
    }

    private static double oppgave2(double x, int n) {
        /*if (n < 0 ) {
            throw new IllegalArgumentException("n kan ikke være negativ");
        }*/
        if(n == 0) {
            return 1;
        }
        else if(n%2 == 1) {
            return x * oppgave2(x*x, (n-1)/2);
        }
        else {
            return oppgave2(x*x, n/2);
        }
    }

    private static double javaMath(double x, int n) {
        return Math.pow(x, n);
    }

    private static void testTid(double x, int n, BiFunction<Double, Integer, Double> func) {
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            func.apply(x, n);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)(slutt.getTime() - start.getTime()) / runder;
        System.out.println("Tid: " + tid);
    }
}
