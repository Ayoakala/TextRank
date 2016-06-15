package Logic;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
//import opennlp.tools.tokenize.Tokenizer;
//import opennlp.tools.tokenize.TokenizerME;
//import opennlp.tools.tokenize.TokenizerModel;
//import opennlp.model.MaxentModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
//
//
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.DefaultWeightedEdge;
//import org.jgrapht.graph.SimpleGraph;
//import org.jgrapht.graph.SimpleWeightedGraph;

public class TextRankSummarize {

	// private HashMap<String, ArrayList<ArrayList<String>>> summaries;
	private String[] sentences;
	private ArrayList<String> words = new ArrayList<String>();
	private final String SENT = "en-sent.bin";
	private final String TOKEN = "en-token.bin";
	private HashMap<String, Integer> wordFreq;
	private final String PATH ="/Users/KiitanAkala/Desktop/Computer Science/Workspace/CMSC132/TextRank/src/Logic/Untitled";
	
	private String sample= "Pierre Vinken Vinken Vinken Vinken, 61 years old,"
			+ " will join the board as a nonexecutive director Nov. 29. Mr."
			+ " Vinken is chairman of Elsevier N.V.,the Dutch publishing group."
			+ " Rudolph Agnew, 55 years old and former chairman of Consolidated"
			+ " Gold Fields PLC,was named a director of this British industrial"
			+ " conglomerate.";
	// Sentence detector syntax gotten from
	// https://amalgjose.com/2013/05/09/simple-sentence-detector-using-opennlp/
	public TextRankSummarize() throws IOException {

		File f = new File(PATH);
		try(FileInputStream fin=new FileInputStream(f)) {
		    //some input stream handlung here
		}catch(IOException e){e.getMessage();} 

		List<String> uniq = new ArrayList<String>();
		uniq = getDistinctWordList(PATH);
		wordFreq = new HashMap<String, Integer>();
		sentences = splitSentences(sample);
		
//		Tokenizer wordDetector = null;
//		InputStream wmodelIn = null;
//		wmodelIn = getClass().getResourceAsStream(TOKEN);
//		TokenizerModel wmodel = new TokenizerModel(wmodelIn);
//		// modelIn.close();
//		wordDetector = new TokenizerME(wmodel);
//
//		if (wmodelIn != null) {
//			wmodelIn.close();
//		}
//		words = (sentenceDetector.sentDetect(sample));
//		String[] words = sample.split(" ");    
		
		
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(sample);
		while (matcher.find()) {
		    words.add(matcher.group());
		}
		
//		for (int x = 0; x<uniq.size();x++){
////		System.out.println(uniq.get(x));	
//		wordFreq.put(uniq.get(x), 
//				1);
//		//countWord(uniq.get(x), f)
//		}
		
		for ( String word : words ) {
			   Integer oldCount = wordFreq.get(word);
			   if ( oldCount == null ) {
			      oldCount = 0;
			   }
			   wordFreq.put(word, oldCount + 1);
			}
		
		for (int i = 0; i < sentences.length; i++) {
			System.out.println(sentences[i]);
		}

		for (String word : wordFreq.keySet()){
			System.out.println(word +" " + wordFreq.get(word));
		}
	}

	public String[] splitSentences(String text) throws InvalidFormatException, IOException{
	
		
		SentenceDetector sentenceDetector = null;
		InputStream smodelIn = null;
		smodelIn = getClass().getResourceAsStream(SENT);
		SentenceModel smodel = new SentenceModel(smodelIn);
		// modelIn.close();
		sentenceDetector = new SentenceDetectorME(smodel);

		if (smodelIn != null) {
			smodelIn.close();
		}
		return (sentenceDetector.sentDetect(text));
	}
	public List<String> getDistinctWordList(String fileName){
		 
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        List<String> wordList = new ArrayList<String>();
        try {
            fis = new FileInputStream(fileName);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String line = null;
            while((line = br.readLine()) != null){
                StringTokenizer st = new StringTokenizer(line, " ,.;:\"");
                while(st.hasMoreTokens()){
                    String tmp = st.nextToken().toLowerCase();
                    if(!wordList.contains(tmp)){
                        wordList.add(tmp);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try{if(br != null) br.close();}catch(Exception ex){}
        }
        return wordList;
    }
	
	public int countWord(String word, File file) throws FileNotFoundException {
		int count = 0;
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
		    String nextToken = scanner.next();
		    if (nextToken.equalsIgnoreCase(word))
		    count++;
		}
		return count;
		}


	public static void main(String[] args) throws IOException {
		TextRankSummarize x = new TextRankSummarize();

	}

}
