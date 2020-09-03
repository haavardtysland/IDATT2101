package Oblig2;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Random;
import java.util.function.BiFunction;

public class Sortering {

    public static void main(String[] args) {
        /*int[] random = randomArray(100000000);
        System.out.println("Random tabell:");
        System.out.println("Før sortering sum: " +sjekkSum(random));
        System.out.println("Før sortering rekkefølge: " + rekkefolgeTest(random));
        testTidSinglePivot(random, 0, random.length - 1);
        System.out.println("Etter sortering sum: " + sjekkSum(random));
        System.out.println("Etter sortering rekkefølge: " + rekkefolgeTest(random));*/

        int[] random = randomArray(10000000);
        System.out.println("Før sortering sum: " +sjekkSum(random));
        System.out.println("Før sortering rekkefølge: " + rekkefolgeTest(random));
        testTidDualPivot(random, 0, random.length -1);
        System.out.println("Etter sortering sum: " + sjekkSum(random));
        System.out.println("Etter sortering rekkefølge: " + rekkefolgeTest(random));

    }

    //sjekker summen av en array
    public static int sjekkSum(int[] array) {
        int sum = 0;
        for(int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    //sjekker at rekkefølgen er riktig frem til alle utenom det siste elementet i tabellen
    public static boolean rekkefolgeTest(int[] array) {
        for(int i = 0; i < array.length-2; i++) {
            if(array[i+1] < array[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] randomArray(int length) {
        Random random = new Random();
        int[] array = new int[length];
        for(int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
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
