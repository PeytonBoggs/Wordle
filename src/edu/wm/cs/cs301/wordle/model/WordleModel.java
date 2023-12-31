package edu.wm.cs.cs301.wordle.model;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import edu.wm.cs.cs301.wordle.controller.ReadWordsRunnable;

public class WordleModel {
	
	private char[] currentWord, guess;
	
	private int columnCount, maximumRows;
	private int currentColumn, currentRow;
	private String difficulty = "medium";
	
	private List<String> wordList;
	
	private final Random random;
	
	private final Statistics statistics;
	
	private WordleResponse[][] wordleGrid;
	
	public WordleModel() {
		this.currentColumn = -1;
		this.currentRow = 0;
		setGridSize();
		this.random = new Random();
		
		createWordList();
		
		this.wordleGrid = initializeWordleGrid();
		this.guess = new char[columnCount];
		this.statistics = new Statistics();
	}
	
	private void createWordList() {
		ReadWordsRunnable runnable = new ReadWordsRunnable(this);
		new Thread(runnable).start();
	}
	
	public void initialize() {
		this.currentColumn = -1;
		this.currentRow = 0;
		setGridSize();
		
		createWordList();
		
		this.wordleGrid = initializeWordleGrid();
		this.guess = new char[columnCount];
	}

	public void generateCurrentWord() {
		String word = getCurrentWord();
		this.currentWord = word.toUpperCase().toCharArray();
		
		//uncomment line below to show secret word on the console for debugging
		System.out.println("Current word set to " + word);
	}

	private String getCurrentWord() {
		return wordList.get(getRandomIndex());
	}

	private int getRandomIndex() {
		int size = wordList.size();
		return random.nextInt(size);
	}
	
	private WordleResponse[][] initializeWordleGrid() {
		WordleResponse[][] wordleGrid = new WordleResponse[maximumRows][columnCount];

		for (int row = 0; row < wordleGrid.length; row++) {
			for (int column = 0; column < wordleGrid[row].length; column++) {
				wordleGrid[row][column] = null;
			}
		}

		return wordleGrid;
	}
	
	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}
	
	public void setCurrentWord() {
		int index = getRandomIndex();
		currentWord = wordList.get(index).toCharArray();
	}
	
	public void setCurrentColumn(char c) {
		currentColumn++;
		currentColumn = Math.min(currentColumn, (columnCount - 1));
		guess[currentColumn] = c;
		wordleGrid[currentRow][currentColumn] = new WordleResponse(c,
				Color.WHITE, Color.BLACK);
	}
	
	public void backspace() {
		if (this.currentColumn > -1) { //only backspace if there's room
			wordleGrid[currentRow][currentColumn] = null;
			guess[currentColumn] = ' ';
			this.currentColumn--;
			this.currentColumn = Math.max(currentColumn, -1);
		}
	}
	
	public WordleResponse[] getCurrentRow() {
		return wordleGrid[getCurrentRowNumber()];
	}
	
	public int getCurrentRowNumber() {
		return currentRow - 1;
	}
	
	public boolean setCurrentRow() {
		for (int column = 0; column < guess.length; column++) {
			Color backgroundColor = AppColors.GRAY;
			Color foregroundColor = Color.WHITE;
			if (guess[column] == currentWord[column]) {
				backgroundColor = AppColors.GREEN;
			} else if (contains(currentWord, guess, column)) {
				backgroundColor = AppColors.YELLOW;
			}
			
			wordleGrid[currentRow][column] = new WordleResponse(guess[column],
					backgroundColor, foregroundColor);
		}
		
		currentColumn = -1;
		currentRow++;
		guess = new char[columnCount];
		
		return currentRow < maximumRows;
	}
	
	private boolean contains(char[] currentWord, char[] guess, int column) {
		for (int index = 0; index < currentWord.length; index++) {
			if (index != column && guess[column] == currentWord[index]) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkValidWord() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("resources/usa.txt"));
		WordleResponse[] guessArray = wordleGrid[currentRow];
		
		String guess = "";
		for (WordleResponse wordleResponse : guessArray) {
			guess += wordleResponse.getChar();
		}
		guess = guess.toLowerCase();
		
		String line = "";
		while (line != null) {
			if (line.equals(guess)) {
				br.close();
				return true;
			}
			line = br.readLine();
		}
		
		br.close();
		return false;
	}
	
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	
	public void setGridSize() {
		switch(difficulty) {
		case("easy"):
			this.columnCount = 4;
			this.maximumRows = 5;
			break;
		case("hard"):
			this.columnCount = 6;
			this.maximumRows = 7;
			break;
		default:
			this.columnCount = 5;
			this.maximumRows = 6;
			break;
		}
	}

	public WordleResponse[][] getWordleGrid() {
		return wordleGrid;
	}
	
	public int getMaximumRows() {
		return maximumRows;
	}

	public int getColumnCount() {
		return columnCount;
	}
	
	public int getCurrentColumn() {
		return currentColumn;
	}

	public int getTotalWordCount() {
		return wordList.size();
	}

	public Statistics getStatistics() {
		return statistics;
	}
	
	public char[] getMysteryWord() {
		return currentWord;
	}

}