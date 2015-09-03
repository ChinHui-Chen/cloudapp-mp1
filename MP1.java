import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;


public class MP1 {
    Random generator;
    String userName;
    String inputFileName;
    String delimiters = "\\ |\t|,|;|\\.|\\?|\\!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|\\/";
    String[] stopWordsArray = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its",
            "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that",
            "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while",
            "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before",
            "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each",
            "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than",
            "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
    HashSet<String> stopWordsSet ;

    void initialRandomGenerator(String seed) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(seed.toLowerCase().trim().getBytes());
        byte[] seedMD5 = messageDigest.digest();

        long longSeed = 0;
        for (int i = 0; i < seedMD5.length; i++) {
            longSeed += ((long) seedMD5[i] & 0xffL) << (8 * i);
        }

        this.generator = new Random(longSeed);
    }

    Integer[] getIndexes() throws NoSuchAlgorithmException {
        Integer n = 10000;
        Integer number_of_lines = 50000;
        Integer[] ret = new Integer[n];
        this.initialRandomGenerator(this.userName);
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(number_of_lines);
        }
        return ret;
    }

    public MP1(String userName, String inputFileName) {
        this.userName = userName;
        this.inputFileName = inputFileName;
    }

    public String[] process() throws Exception {
        String[] ret = new String[20];

        //Array to hashSet
        stopWordsSet = new HashSet<String>();
        for(int i=0 ; i<stopWordsArray.length ; i++)
        {
           stopWordsSet.add( stopWordsArray[i] );   
        }
       
        // Get indexes.
        Integer[] indexes = getIndexes();
        Map indexMap = new HashMap();
        for( int i=0 ; i<indexes.length ; i++)
        {
          indexMap.put( indexes[i] , 1 );
        }

        // Read each line.
        try( BufferedReader br = new BufferedReader(new FileReader(inputFileName)) )
        {
          int i=0;
          for(String line; (line = br.readLine()) != null ; i++)
          {
             if( indexMap.containsKey(i) )
             {  
                // Splits(delimiter, trim, stopwords)
                String[] list = line.split( delimiters );
                for(int j=0 ; j<list.length ; j++)
                {
                   String word = list[j].trim();
                   if(! stopWordsSet.contains( word ) && word.length() >= 1 )
                   {
                      System.out.println( word );     
                   }
                }
             }
          }
        }

        return ret;
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1){
            System.out.println("MP1 <User ID>");
        }
        else {
            String userName = args[0];
            String inputFileName = "./input.txt";
            MP1 mp = new MP1(userName, inputFileName);
            String[] topItems = mp.process();
            for (String item: topItems){
                System.out.println(item);
            }
        }
    }
}
