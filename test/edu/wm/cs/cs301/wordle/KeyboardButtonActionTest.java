package edu.wm.cs.cs301.wordle;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.junit.jupiter.api.Test;

import edu.wm.cs.cs301.wordle.controller.KeyboardButtonAction;
import edu.wm.cs.cs301.wordle.model.WordleModel;
import edu.wm.cs.cs301.wordle.view.WordleFrame;

class KeyboardButtonActionTest {

	@Test
	void testValidWord() {
		WordleModel testModel = new WordleModel();
		WordleFrame testFrame = new WordleFrame(testModel);
		
		int startingRow = testModel.getCurrentRowNumber();

		char[] guess = ("abcde").toCharArray();
		for (char c : guess) {
			testModel.setCurrentColumn(c);
		}
		
		KeyboardButtonAction testAction = new KeyboardButtonAction(testFrame, testModel);
		JButton dummyButton = new JButton();
		dummyButton.setActionCommand("Enter");
		ActionEvent testEvent = new ActionEvent(dummyButton, ActionEvent.ACTION_PERFORMED, "\n");
		testAction.actionPerformed(testEvent);
		
		int endingRow = testModel.getCurrentRowNumber();
		
		assertEquals(startingRow, endingRow, "An invalid word should not be able to be guessed.");
	}

}
