package Oblig4;

import java.sql.SQLOutput;
import java.util.Hashtable;

//oppgave 2
public class HashTabell2 {
  private int[] arr;
  private int kollisjon;

  public HashTabell2(int length) {
    this.arr = new int[(int) Math.pow(2, Math.ceil(Math.log(length)/Math.log(2)))];
    System.out.println(arr.length);
    kollisjon = 0;
  }

  public int multiHash(int k) {
    double A = k * ((Math.sqrt(5.0) - 1.0) / 2.0);
    A -= (int) A;
    return (int) (arr.length * Math.abs(A));
  }

  public int modHash(int k) {
    return ((2 * Math.abs(k) + 1) % (arr.length - 1));
  }

  public void push(int x) {
    int h1 = multiHash(x);
    if (arr[h1] == 0) {
      arr[h1] = x;
    } else {
      int counter = 1;
      while (counter < arr.length) {
        int h2 = (modHash(x) * counter + h1) % (arr.length - 1);
        if (arr[h2] == 0) {
          break;
        } else {
          counter++;
          kollisjon++;
        }
      }
    }
  }

  public String get(int x) {
    int h1 = multiHash(x);
    if (arr[h1] == x) {
      return arr[h1] + "funnet på index" + h1;
    } else {
      int counter = 1;
      while (counter < arr.length) {
        int h2 = (modHash(x) * counter + h1) % (arr.length - 1);
        if (arr[h2] == x) {
          return arr[h2] + " funnet på index " + h2 + " med " + counter + " hopp ";
        } else {
          counter++;
        }
      }
    }
    return "Ikke funnet";
  }

  public static void main(String[] args) {

    int lengde = 5000000;
    int finn = 10543;
    HashTabell2 hashTabell2 = new HashTabell2(lengde);

    long start;
    long slutt;
    long total = 0;
    int random = 0;
    hashTabell2.push(finn);
    for(int i = 0; i < lengde -1; i++) {
      random = (int)(Math.random()*lengde+1);
      start = System.nanoTime();
      hashTabell2.push(random);
      slutt = System.nanoTime();
      total += slutt - start;
    }
    System.out.println("Tid for å fylle min tabell: " + total/1000000 + "ms");
    System.out.println("Kollisjoner: " + hashTabell2.kollisjon);
    System.out.println(hashTabell2.get(finn));
    total = 0;
    Hashtable<Integer, Integer> hashTabellJava = new Hashtable<Integer, Integer>();
    for(int i = 0; i < lengde -1; i++) {
      random = (int)(Math.random()*lengde+1);
      start = System.nanoTime();
      hashTabellJava.put(i, random);
      slutt = System.nanoTime();
      total += slutt - start;
    }
    System.out.println("Tid for å fylle Java tabell: " + total/1000000 + "ms");
  }
}
