import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class Functions {
    private File html;
    private String baseUri;
    private Document doc;

    Functions(String htmlFile, String baseUri) throws IOException
    {
        html = new File(htmlFile);

        doc = Jsoup.parse(html, null, baseUri);

        this.baseUri = baseUri;
    }

    private String getTitle()
    {
        String title = doc.title();
        System.out.println("Titlul site-ului: " + title);
        return title;
    }

    private String getKeywords() // preia cuvintele cheie
    {
        Element keywords = doc.selectFirst("meta[name=keywords]");
        String keywordsString = "";
        if (keywords == null) {
            System.out.println("Nu exista tag-ul <meta name=\"keywords\">!");
        } else {
            keywordsString = keywords.attr("content");
            System.out.println("Cuvintele cheie sunt preluate!");
        }
        return keywordsString;
    }

    private String getDescription()
    {
        Element description = doc.selectFirst("meta[name=description]");
        String descriptionString = "";
        if (description == null) {
            System.out.println("Nu exista tag-ul <meta name=\"description\">!");
        } else {
            descriptionString = description.attr("content");
            System.out.println("Descrierea site-ului a fost preluata!");
        }
        return descriptionString;
    }

    private String getRobots()
    {
        Element robots = doc.selectFirst("meta[name=robots]");
        String robotsString = "";
        if (robots == null) {
            System.out.println("Nu exista tag-ul <meta name=\"robots\">!");
        } else {
            robotsString = robots.attr("content");
            System.out.println("Lista de robots a site-ului a fost preluata!");
        }
        return robotsString;
    }

    public String getTextFromHTML() throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getTitle()); // titlul
        sb.append(System.lineSeparator());
        sb.append(getKeywords()); // cuvintele cheie
        sb.append(System.lineSeparator());
        sb.append(getDescription());
        sb.append(System.lineSeparator());
        sb.append(doc.body().text());
        String text = sb.toString();

        StringBuilder textFileNameBuilder = new StringBuilder(html.getAbsolutePath());
        textFileNameBuilder.replace(textFileNameBuilder.lastIndexOf(".") + 1, textFileNameBuilder.length(), "txt");
        String textFileName = textFileNameBuilder.toString();

        FileWriter fw = new FileWriter(new File(textFileName), false);
        fw.write(text);
        fw.close();

        return textFileName;
    }


    public HashMap<String, Integer> processText(String fileName) throws IOException
    {
        HashMap<String, Integer> hashText = new HashMap<String, Integer>();

        FileReader inputStream = null;
        inputStream = new FileReader(fileName);

        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = inputStream.read()) != -1)
        {
            if (!Character.isLetterOrDigit((char)c))
            {
                String newWord = sb.toString();
                if (hashText.containsKey(newWord))
                {
                    hashText.put(newWord, hashText.get(newWord) + 1);
                }
                else
                {
                    hashText.put(newWord, 1);
                }
                sb.setLength(0);
            }
            else
            {
                sb.append((char)c);
            }
        }

        hashText.remove("");

        inputStream.close();
        return hashText;
    }
}
