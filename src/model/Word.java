package model;

public class Word {
	
	private String word;
	private WordType type;
	private int polarity;
	
	//constructor
	public Word(String w, WordType t, int p)
	{
		word = w;
		type = t;
		polarity = p;
		
	}

	//methods
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public WordType getType() {
		return type;
	}

	public void setType(WordType type) {
		this.type = type;
	}

	public int getPolarity() {
		return polarity;
	}

	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public String toString(){
		
		String word = this.getWord() + ", " + this.getType() + ", " + this.getPolarity();
		return word;
		
	}
	
}



