package Oblig2;

import java.util.Date;
import java.util.Random;

public class Sortering {

    public static void main(String[] args) {

        int[] random = randomArray(100000000);
        int[] random2 = randomArray(100000000);
        System.out.println("RANDOM TABELL");
        System.out.println("Før sortering sum 1: " +sjekkSum(random));
        System.out.println("Før sortering rekkefølge 1: " + rekkefolgeTest(random));
        System.out.println("Før sortering sum 2: " +sjekkSum(random2));
        System.out.println("Før sortering rekkefølge 2: " + rekkefolgeTest(random2));
        testTidDualPivot(random, 0, random.length - 1);
        testTidSinglePivot(random2, 0, random2.length - 1);
        System.out.println("Etter sortering sum 1: " + sjekkSum(random));
        System.out.println("Etter sortering rekkefølge 1: " + rekkefolgeTest(random));
        System.out.println("Ettert sortering sum 2: " +sjekkSum(random2));
        System.out.println("Etter sortering rekkefølge 2: " + rekkefolgeTest(random2));


        System.out.println("\r\nDUPlIKAT TABELL");
        int[] duplikat = duplikatArray(1, 3, 100000000);
        int[] duplikat2 = duplikatArray(1, 3, 100000000);
        System.out.println("Før sortering sum 1: " +sjekkSum(duplikat));
        System.out.println("Før sortering rekkefølge 1: " + rekkefolgeTest(duplikat));
        System.out.println("Før sortering sum 2: " +sjekkSum(duplikat2));
        System.out.println("Før sortering rekkefølge 2: " + rekkefolgeTest(duplikat2));
        testTidDualPivot(duplikat, 0, duplikat.length - 1);
        testTidSinglePivot(duplikat2, 0, duplikat2.length - 1);
        System.out.println("Etter sortering sum 1: " + sjekkSum(duplikat));
        System.out.println("Etter sortering rekkefølge 1: " + rekkefolgeTest(duplikat));
        System.out.println("Ettert sortering sum 2: " +sjekkSum(duplikat2));
        System.out.println("Etter sortering rekkefølge 2: " + rekkefolgeTest(duplikat2));


        System.out.println("\r\nSORTERT TABELL");
        int[] sortert = duplikat;
        int[] sortert2 = duplikat2;
        System.out.println("Før sortering sum 1: " +sjekkSum(sortert));
        System.out.println("Før sortering rekkefølge 1: " + rekkefolgeTest(sortert));
        System.out.println("Før sortering sum 2: " +sjekkSum(sortert2));
        System.out.println("Før sortering rekkefølge 2: " + rekkefolgeTest(sortert2));
        testTidDualPivot(sortert, 0, sortert.length - 1);
        testTidSinglePivot(sortert2, 0, sortert2.length - 1);
        System.out.println("Etter sortering sum 1: " + sjekkSum(sortert));
        System.out.println("Etter sortering rekkefølge 1: " + rekkefolgeTest(sortert));
        System.out.println("Ettert sortering sum 2: " +sjekkSum(sortert2));
        System.out.println("Etter sortering rekkefølge 2: " + rekkefolgeTest(sortert2));

    }

    //sjekker summen av en array
    public static int sjekkSum(int[] array) {
        int sum = 0;
        for(int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    //sjekker at rekkefølgen er riktig frem til alle utenom det siste elementet i tabellen, går da utifra at hvis alle de tidligere elementene er sortert, er også det siste det
    public static boolean rekkefolgeTest(int[] array) {
        for(int i = 0; i < array.length-2; i++) {
            if(array[i+1] < array[i]) {
                return false;
            }
        }
        return true;
    }

    //metode for å lage en tabell med tilfeldige tall
    public static int[] randomArray(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    //Metode for å lage en tabell med mange duplikater
    public static int[] duplikatArray(int a, int b, int length) {
        int[] array = new int[length];
        for(int i = 0; i < array.length; i++) {
            if(i%2 == 0) {
                array[i] = a;
            } else {
                array[i] = b;
            }
        }
        return array;
    }

    private static void testTidSinglePivot(int[] array, int v, int h) {
        SinglePivot singlePivot = new SinglePivot();
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            singlePivot.quicksort(array, v, h);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)(slutt.getTime() - start.getTime()) / runder;
        System.out.println("Tid: " + tid);
    }

    private static void testTidDualPivot(int[] array, int v, int h) {
        DualPivot dualPivot = new DualPivot();
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            dualPivot.quicksort(array, v, h);
            slutt = new Date();
            ++runder;
        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double)(slutt.getTime() - start.getTime()) / runder;
        System.out.println("Tid: " + tid);
    }
}
