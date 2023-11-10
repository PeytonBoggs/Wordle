package edu.wm.cs.cs301.wordle;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import edu.wm.cs.cs301.wordle.model.WordleModel;

class WordleModelTest {
	
	private WordleModel testModel;
	
	@BeforeEach
	void setUp() throws Exception {
		testModel = new WordleModel();
	}
	
	@AfterEach
	void tearDown() throws Exception {
		testModel = null;
	}
	
	@Test
	void setDifficultyExistsTest() {
		try {
			testModel.getClass().getMethod("setDifficulty", WordleModel.class);
		} catch (NoSuchMethodException e) {
			fail("setDifficulty method not yet defined");
		}
	}
	
	@Test
	void defaultColumnTest() {
		assertEquals(5, testModel.getColumnCount(), "Default model should have five columns");
	}
	
	@Test
	void defaultRowTest() {
		assertEquals(6, testModel.getMaximumRows(), "Default model should have six rows");
	}
	
	@Test
	void easyColumnTest() {
		testModel.setDifficulty("easy");
		assertEquals(4, testModel.getColumnCount(), "Model should have four columns on easy difficulty");
	}
	
	@Test
	void easyRowTest() {
		testModel.setDifficulty("easy");
		assertEquals(5, testModel.getMaximumRows(), "Model should have five rows on easy difficulty");
	}
	
	@Test
	void mediumColumnTest() {
		testModel.setDifficulty("medium");
		assertEquals(5, testModel.getColumnCount(), "Model should have five columns on medium difficulty");
	}
	
	@Test
	void mediumRowTest() {
		testModel.setDifficulty("medium");
		assertEquals(6, testModel.getMaximumRows(), "Model should have six rows on medium difficulty");
	}
	
	@Test
	void hardColumnTest() {
		testModel.setDifficulty("hard");
		assertEquals(6, testModel.getColumnCount(), "Model should have six columns on hard difficulty");
	}
	
	@Test
	void hardRowTest() {
		testModel.setDifficulty("hard");
		assertEquals(7, testModel.getMaximumRows(), "Model should have seven rows on hard difficulty");
	}
	
	@Test
	void abortOnDifficultyChangeTest() {
		testModel.setCurrentColumn('h');
		testModel.setCurrentColumn('e');
		testModel.setCurrentColumn('l');
		testModel.setCurrentColumn('l');
		testModel.setCurrentColumn('o');
		testModel.setCurrentRow();
		
		testModel.setDifficulty("hard");
		
		assertEquals(-1, testModel.getCurrentRowNumber(), "Model should reset after changing difficulty");
		
	}
}
