package Oblig2;

public class Sortering {

    public static void main(String[] args) {
        int[] random = new int[]{1, 6, 4, 8, 3, 2};
        SinglePivot singlePivot = new SinglePivot();
        System.out.println("Før sortering sum: " +sjekkSum(random));
        System.out.println("Før sortering rekkefølge: " + rekkefolgeTest(random));
        singlePivot.quicksort(random, random[0], random.length - 1);
        System.out.println("Etter sortering sum: " + sjekkSum(random));
        System.out.println("Etter sortering rekkefølge: " + rekkefolgeTest(random));

        for (int x : random) {
            System.out.println(x);
        }
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
}
