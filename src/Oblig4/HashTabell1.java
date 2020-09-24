package Oblig4;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//oppgave 1
public class HashTabell1 {
  private HashNode[] arr;
  private int kollisjon;

  public HashTabell1(int length) {
    arr = new HashNode[length/2];
    kollisjon = 0;
  }

  public int hashFunc(String s) {
    int sum = 0;
    int counter = 1;
    for (char c : s.toCharArray()) {
      sum += c * counter;
      counter++;
    }
    return sum % arr.length;
  }

  public void push(String s) {
    int index = hashFunc(s);
    if (arr[index] == null) {
      arr[index] = new HashNode(s);
    } else {
      HashNode temp = arr[index];
      String funnet = "Kollisjon på index " + index + ":" + temp.data + " -> ";
      kollisjon ++;
      while (temp.neste != null) {
        temp = temp.neste;
        kollisjon ++;
        funnet += temp.data + " -> ";
      }
      System.out.println(funnet + s);
      temp.setNeste(new HashNode(s));
    }
  }

  public HashNode find(String s) {
    try {
      int index = hashFunc(s);
      HashNode temp = arr[index];
      String funnet = "Person funnet på index " + index + ": ";
      while (!temp.data.equals(s)) {
        funnet +=  temp.data+ ", ";
        temp = temp.neste;
      }
      System.out.println(funnet + ", " + temp.data + " <- Funnet!!");
      return temp;
    } catch (NullPointerException e) {
      return null;
    }
  }

  public String analyse() {
    int counter = 0;
    for (HashNode node : arr) {
      if (node != null) {
        counter++;
      }
    }
    int pers = finnAntallPers();
    return counter + " av " + arr.length + " er brukt. Lastfaktor er: " + (double) counter/arr.length +". \nNr av kollisjoner: " + kollisjon + ", nr av personer: " + pers + " , gjennomsnitt: " + (double)kollisjon/pers;
  }

  public int finnAntallPers() {
    int pers = 0;
    for(HashNode h : arr) {
      if(h != null){
        pers ++;
        while(h.neste != null){
          h = h.neste;
          pers ++;
        }
      }
    }
    return pers;
  }

  public static void main(String[] args) {
    ArrayList<String> temp = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\haava\\OneDrive\\Dokumenter\\Progging\\Øvinger\\AlgDatØvinger\\IDATT2101\\src\\Oblig4\\navn20.txt")));
      String line;
      while ((line = br.readLine()) != null) {
        temp.add(line);
      }
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    HashTabell1 ht = new HashTabell1(temp.size());
    for (String s : temp) {
      ht.push(s);
    }
    HashNode find = ht.find("Lars Brodin,Østby");
    if (find != null) {
      System.out.println(find.data);
    } else {
      System.out.println("Person ikke funnet");
    }


    System.out.println(ht.analyse());
    System.out.println(ht.arr[16].neste.data);
  }
}