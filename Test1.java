public class Test1 {

    public static void main(String[] args) {
        StringAVLTreeXtra t = new StringAVLTreeXtra();
        String str;
        int line = 1;
        char action;
        String s = "imaoinaoioaoipaoiqaoilaoikaoikdikgikfikeo";

        // add lines like this when you have delete working
        // s = s + "dkeodkfodpao";

        do {
            action = s.charAt(0);
            if (action == 'i') {   // insert
                str = s.substring(1,3);
                s = s.substring(3, s.length());
                t.insert(str);
            } else if (action == 'n') {  // new tree -- wipe out the tree and start over
                s = s.substring(1, s.length());
                t = new StringAVLTreeXtra();
            } else {  // no other choice, then output the tree
                s = s.substring(1, s.length());
                t.display();
                System.out.println(" - " + line++ + ".\n");
            }
        } while (s.length() != 0);
    }
}
