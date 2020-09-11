package Oblig3;

import java.math.BigInteger;

public class Oppgave1 {

    public static void main(String[] args) {
        BigInteger a = new BigInteger("10203040501232312333123");
        BigInteger b = new BigInteger("5010231231231231231312311");

        System.out.println("Lenket liste addisjon:");
        LenketListe resultatAdd = addisjon(a, b);
        resultatAdd.printListe(resultatAdd.finnHode());
        System.out.println("BigInteger metode: ");
        System.out.println(a.add(b));

        System.out.println("Lenket liste subtraksjon: ");
        LenketListe resultatSub = subtraksjon(a, b);
        resultatSub.printListe(resultatSub.finnHode());
        System.out.println(a.subtract(b));


    }

    public static LenketListe addisjon(BigInteger a, BigInteger b){
        LenketListe liste1 = lagListe(a);
        LenketListe liste2 = lagListe(b);
        LenketListe nyListe = new LenketListe();
        Node denne1 = liste1.finnHale();
        Node denne2 = liste2.finnHale();
        int num; //verdien som skal legges inn i listen
        int temp = 0; //en verdi for å finne det 2. sifferet hvis verdien blir større enn 9
        while (denne1 != null || denne2 != null) {
            if(denne1 != null && denne2 != null) { //hvis de er like lange
                num = denne1.finnElement() + denne2.finnElement() + temp;
                temp = 0;
                denne1 = denne1.finnForrige();
                denne2 = denne2.finnForrige();
            } else if(denne1 != null) { //hvis liste1 inneholder et tall som er lengre enn liste2
                num = denne1.finnElement();
                denne1 = denne1.finnForrige();
            } else { //hvis liste 2 inneholder et tall som er lengre enn liste1
                num = denne2.finnElement();
                denne2 = denne2.finnForrige();
            }
            if(num > 9) {
                if(num == 10) {
                    temp = 1;
                    num = 0;
                } else {
                    temp = num % 10;
                    num = (num - temp) / 10;
                }
                nyListe.settInnFremst(num);
            } else {
                nyListe.settInnFremst(num);
            }

        }
        return nyListe;
    }

    public static LenketListe subtraksjon(BigInteger a, BigInteger b) {
        LenketListe liste1 = lagListe(a);
        LenketListe liste2 = lagListe(b);
        LenketListe nyListe = new LenketListe();
        Node denne1 = liste1.finnHale();
        Node denne2 = liste2.finnHale();
        int num; //verdien som skal legges inn i listen
        int temp = 0; //en verdi for hvis et tall blir mindre enn 0, for å fjerne fra tallet foran
        int fortegn = 1;
        while (denne1 != null || denne2 != null) {
            if (denne1 != null && denne2 != null) { //hvis de er like lange
                if(liste1.finnAntall() > liste2.finnAntall()) {
                    num = (denne1.finnElement() - temp) - denne2.finnElement();
                } else {
                    num = (denne2.finnElement() - temp) - denne1.finnElement();
                }
                if( num < 0) { //hvis totale blir negativt
                    num = 10 - Math.abs(num);
                    temp = 1;
                } else {
                    temp = 0;
                }
                denne1 = denne1.finnForrige();
                denne2 = denne2.finnForrige();
                nyListe.settInnFremst(num);
            } else if (denne1 != null) { //hvis liste1 inneholder tall som er lengre enn liste2
                num = denne1.finnElement() - temp;
                denne1 = denne1.finnForrige();
                temp = 0;
                nyListe.settInnFremst(num);
            } else { //hvis liste 2 inneholder et tall som er lengre enn liste1
                num = denne2.finnElement();
                denne2 = denne2.finnForrige();
                temp = 0;
                if(denne2 == null) {
                    fortegn = -1;
                    num = num*fortegn;
                }
                nyListe.settInnFremst(num);
            }
        }
        return nyListe;
    }

    public static LenketListe lagListe(BigInteger x){

        LenketListe liste = new LenketListe();
        BigInteger tall;
        BigInteger ti = new BigInteger("10");
        while(x.signum() == 1) { //BigInteger.signum returnerer 1 hvis x > 0
            tall = x.mod(ti); //får det siste tallet i BigIntegeren ved å ta mod(10)
            liste.settInnFremst(tall.intValueExact()); //legger det til i listen
            x = x.divide(ti); //deler på 10 for å fjerne det siste tallet
        }
        return liste;
    }
}
