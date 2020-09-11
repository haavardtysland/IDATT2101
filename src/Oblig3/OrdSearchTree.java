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

    private void printTreeFormat(OrdNode root) {
        if(root == null) return;
        LinkedList<OrdNode> queue = new LinkedList<OrdNode>();
        queue.add(root);
        int height = finnHoyde(root);
        boolean finished = false;
        int i = 0;
        while(i++ < height && !finished) {
            //To print out the spaces.
            for(int j=0; j < height-i; j++) {
                System.out.print("   ");
            }
            int nodeCount = queue.size();
            if(nodeCount == 0)
                finished = true;
            //Nodes from the current level is removed and nodes for the next level is added in the queue.
            while(nodeCount > 0) {
                //To retrieve but not remove the first element in the list.
                OrdNode node = queue.peek();
                System.out.print(node.nokkel + " ");
                queue.remove();
                if(node.venstre != null)
                    queue.add(node.venstre);
                if(node.hoyre != null)
                    queue.add(node.hoyre);
                nodeCount--;
            }
            System.out.println();
        }
    }
    public void printTreeFormat() {
        printTreeFormat(rot);
    }
    private int finnHoyde(OrdNode rot) {
        if(rot != null) {
            return 1 + Math.max(finnHoyde(rot.venstre), finnHoyde(rot.hoyre));
        }
        return 0;
    }

    
}
