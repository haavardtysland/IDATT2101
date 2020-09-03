package Oblig2;

public class Sortering {

    public static void main(String[] args) {
        int[] random = new int[]{1, 6, 4, 8, 3, 2};
        SinglePivot singlePivot = new SinglePivot();
        System.out.println("Før sortering: " +sjekkSum(random));
        singlePivot.quicksort(random, random[0], random.length - 1);
        System.out.println("Etter sortering" + sjekkSum(random));
        for (int x : random) {
            System.out.println(x);
        }
    }

    public static int sjekkSum(int[] array) {
        int sum = 0;
        for(int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    public static int rekkefolgeTest(int[] array) {
        
    }
}
