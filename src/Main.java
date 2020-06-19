import java.io.IOException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Functions l1 = new Functions("./aciasi/actuiasi.html", "https://ac.tuiasi.ro/");
        String textFile = l1.getTextFromHTML();
        HashMap<String, Integer> words = l1.processText(textFile);

        System.out.println("\nCuvintele din text sunt:");
        for (HashMap.Entry<String, Integer> word : words.entrySet())
        {
            System.out.println(word.getKey() + " -> " + word.getValue());
        }
    }
}
