package Oblig3;

import javax.lang.model.element.Element;
import java.util.LinkedList;

public class OrdSearchTree {

    OrdNode rot;

    public OrdSearchTree() {
        rot = null;
    }

    private OrdNode leggTil(OrdNode rot, String nokkel) {
        if(rot == null) {
            rot = new OrdNode(nokkel);
            return rot;
        }
        if(nokkel.compareToIgnoreCase(rot.nokkel) < 0) {
            rot.venstre = leggTil(rot.venstre,nokkel);
        } else if(nokkel.compareToIgnoreCase(rot.nokkel) > 0) {
            rot.hoyre = leggTil(rot.hoyre,nokkel);
        }
        return rot;
    }

    public void settInn(String key) {
        rot = leggTil(rot, key);
    }

    private void printTreFormat(OrdNode rot) {
        if (rot == null) return;

        LinkedList<OrdNode> queue = new LinkedList<>();
        queue.add(rot);

        int hoyde = finnHoyde(rot);
        boolean finished = false;
        int i = 0;

        while(i++ < hoyde && !finished) {
            //For å skirve ut mellomrom
            for(int j=0; j < hoyde-i; j++) {
                System.out.println("   ");
            }
            int nodeCount = queue.size();
            if(nodeCount == 0) finished = true;

            while(nodeCount > 0) {
                OrdNode node = queue.peek();
                System.out.println(node.nokkel + " ");
                queue.remove();
                if(node.venstre != null) queue.add(node.venstre);
                if(node.hoyre != null) queue.add(node.hoyre);
                nodeCount--;
            }

            System.out.println();
        }
    }

    public void printTreFormat() {
        printTreFormat(rot);
    }

    private int finnHoyde(OrdNode rot) {
        if(rot != null) {
            return 1 + Math.max(finnHoyde(rot.venstre), finnHoyde(rot.hoyre));
        }
        return 0;
    }

    
}
