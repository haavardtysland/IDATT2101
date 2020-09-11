package Oblig3;

import java.util.Scanner;

public class Oppgave2 {
    public static void main(String[] args) {
        System.out.println("Skriv inn noen ord med mellomrom");
        Scanner sc = new Scanner(System.in);
        String[] array = sc.nextLine().split(" ");
        OrdSearchTree ord = new OrdSearchTree();
        for(int i = 0; i < array.length; i++) {
            ord.settInn(array[i]);
        }
        ord.printTreeFormat();
    }
}

