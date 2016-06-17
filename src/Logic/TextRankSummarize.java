package Logic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;

public class TextRankSummarize {

	// private HashMap<String, ArrayList<ArrayList<String>>> summaries;
	private String[] sentences;
	private List<String> words = new ArrayList<String>();
	private final String SENT = "en-sent.bin";
	private final String TAGGER = "en-pos-maxent.bin";
	private HashMap<String, Integer> wordFreq;
	

	private String sample = "Pierre Vinken Vinken Vinken Vinken, 61 years old,"
			+ " will join the board as a nonexecutive director Nov. 29. Mr."
			+ " Vinken is chairman of Elsevier N.V.,the Dutch publishing group."
			+ " Rudolph Agnew, 55 years old and former chairman of Consolidated"
			+ " Gold Fields PLC,was named a director of this British industrial"
			+ " conglomerate.";

	// Sentence detector syntax gotten from
	// https://amalgjose.com/2013/05/09/simple-sentence-detector-using-opennlp/
	public TextRankSummarize() throws IOException {
		
		this.sentences = splitSentences(sample);
		this.words = getAllWordsList(sample);
		this.wordFreq = new HashMap<String, Integer>();
		wordFreq = putAllWordsandCount(words);
		
		
		InputStream modelIn = null;
		modelIn = getClass().getResourceAsStream(TAGGER);
		final POSModel posModel = new POSModel(modelIn);
		modelIn.close();
		 // this tags all the words in the text
		POSTaggerME posTagger = new POSTaggerME(posModel);
		for (int x= 0 ; x<1; x++){
			System.out.println(posTagger.tag(sentences[x]));
			
		}
	}

	public HashMap<String, Integer> putAllWordsandCount(List<String> wordList) {
		HashMap<String, Integer> freq = new HashMap<String, Integer>();
		for (String word : wordList) {
			Integer oldCount = freq.get(word);
			if (oldCount == null) {
				oldCount = 0;
			}
			freq.put(word, oldCount + 1);
		}
		return freq;
	}

	public String[] splitSentences(String text) throws InvalidFormatException,
	IOException {

		SentenceDetector sentenceDetector = null;
		InputStream smodelIn = null;
		smodelIn = getClass().getResourceAsStream(SENT);
		SentenceModel smodel = new SentenceModel(smodelIn);
		sentenceDetector = new SentenceDetectorME(smodel);

		if (smodelIn != null) {
			smodelIn.close();
		}
		return (sentenceDetector.sentDetect(text));
	}

	public List<String> getAllWordsList(String text) {
		ArrayList<String> list = new ArrayList<String>();
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(sample);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}

	@Override
	public String toString() {
		String text = "";
		for (int i = 0; i < sentences.length; i++)
			text += sentences[i] + "\n";

		for (String word : wordFreq.keySet())
			text += (word + " " + wordFreq.get(word)) + "\n";

		return text;
	}

	public static void main(String[] args) throws IOException {
		TextRankSummarize x = new TextRankSummarize();
		System.out.println(x.toString());
	}

}
