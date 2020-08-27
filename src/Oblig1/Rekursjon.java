package Oblig1;

import java.util.Date;

public class Rekursjon {

    public static void main(String[] args) {
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            oppgave1(1.001, 2000);
            //System.out.println(oppgave2(1.01, 1000));
            //System.out.println(Math.pow(1.001, 5000));
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)(slutt.getTime() - start.getTime()) / runder;
        System.out.println("Tid: " + tid);
    }
    public static double oppgave1(double x, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n kan ikke være negativ");
        }
        if (n == 0) {
            return 1;
        }
        return x * oppgave1(x, n-1);
    }

    public static double oppgave2(double x, int n) {
        if (n < 0 ) {
            throw new IllegalArgumentException("n kan ikke være negativ");
        }

        if(n == 0) {
            return 1;
        }
        else if(n%2 != 0) {
            return x * oppgave2(x*x, (n-1)/2);
        }
        else {
            return oppgave2(x*x, n/2);
        }
    }
}
