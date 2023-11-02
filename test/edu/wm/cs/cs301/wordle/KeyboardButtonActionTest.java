package edu.wm.cs.cs301.wordle;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import edu.wm.cs.cs301.wordle.model.WordleModel;
import edu.wm.cs.cs301.wordle.view.WordleFrame;

class KeyboardButtonActionTest {

	@Test
	void testValidWord() throws IOException {
//		WordleModel testModel = new WordleModel();
//		new WordleFrame(testModel);
//		
//		testModel.setCurrentWord();
		
//		ActionEvent testEvent = new ActionEvent();
		
//		System.out.println(testModel.getCurrentRow());

		BufferedReader br = new BufferedReader(new FileReader("resources/usa.txt"));
			
		String currentWord = "";
		String inputWord = "adieu";
		boolean match = false;
			
		while (currentWord != null) {
			if (currentWord.equals(inputWord)) {
				match = true;
			}
			currentWord = br.readLine();
		}
			
		br.close();
		
		assertTrue(match, "The input word should be in the word list.");
	}

}
