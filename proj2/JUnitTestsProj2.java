package edu.iastate.cs228.proj2;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;
import org.junit.Rule;

/**
 * @author Cory Smith
 * @author Lillian Krohn
 * 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitTestsProj2 {
	
	@Rule
	public ExpectedException ex = ExpectedException.none();
	
	@Test
	public void test01_LexiconImplConstructor() {
		char[] chars = new char[] {'q', 'z', 'a'};
		LexiconImpl lex = new LexiconImpl(chars);
		assertEquals(3, lex.characterOrdering.length);
		
		LexiconImpl.CharacterValue[] expected = new LexiconImpl.CharacterValue[] 
				{new LexiconImpl.CharacterValue(2, 'a'), new LexiconImpl.CharacterValue(0, 'q'), new LexiconImpl.CharacterValue(1, 'z')};
		// I can't seem to get assertArrayEquals to work here, so I'm using a for loop instead
		for (int i = 0; i < 3; i++) {
			assertEquals("Error at index: " + i, expected[i].character, lex.characterOrdering[i].character);
			assertEquals("Error at index: " + i, expected[i].value, lex.characterOrdering[i].value);
		}
	}
	
	@Test
	public void test02_LexiconImplCompare() {
		char[] chars = new char[] {'q', 'z', 'a'};
		LexiconImpl lex = new LexiconImpl(chars);
		assertEquals(0, lex.compare("qza", "qza"));
		assertEquals(-1, lex.compare("qza", "zqa"));
		assertEquals(1, lex.compare("qaz", "qza"));
	}
	
	@Test
	public void test03_LexiconImplGetCharacterOrdering() {
		char[] chars = new char[] {'q', 'z', 'a'};
		LexiconImpl lex = new LexiconImpl(chars);
		assertEquals(-1, lex.getCharacterOrdering('.'));
		assertEquals(0, lex.getCharacterOrdering('q'));
		assertEquals(1, lex.getCharacterOrdering('z'));
		assertEquals(2, lex.getCharacterOrdering('a'));
	}
	
	@Test
	public void test04_LexiconImplIsValid() {
		char[] chars = new char[] {'q', 'z', 'a'};
		LexiconImpl lex = new LexiconImpl(chars);
		assertTrue(lex.isValid("aqzzaqzaqzqzaqzaqz"));
		assertFalse(lex.isValid("saazqazqazq"));
		assertFalse(lex.isValid("aazqazqazqs"));
	}
	
	@Test
	// I'll sort these in reverse alphabetical order just for convenience
	public void test05_SelectionSort() {
		char[] chars = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
		String[] words = new String[] {"G", "A", "Q", "W", "X", "B", "F", "A"};
		String[] expected = new String[] {"X", "W", "Q", "G", "F", "B", "A", "A"};
		LexiconImpl lex = new LexiconImpl(chars);
		new SelectionSort().sortHelper(words, lex);
		assertArrayEquals(expected, words);
	}
	
	@Test
	// I'll sort these in reverse alphabetical order just for convenience
	public void test06_QuickSort() {
		char[] chars = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
		String[] words = new String[] {"G", "A", "Q", "W", "X", "B", "F", "A"};
		String[] expected = new String[] {"X", "W", "Q", "G", "F", "B", "A", "A"};
		LexiconImpl lex = new LexiconImpl(chars);
		new QuickSort().sortHelper(words, lex);
		assertArrayEquals(expected, words);
	}
	
	@Test
	// I'll sort these in reverse alphabetical order just for convenience
	public void test07_MergeSort() {
		char[] chars = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
		String[] words = new String[] {"G", "A", "Q", "W", "X", "B", "F", "A"};
		String[] expected = new String[] {"X", "W", "Q", "G", "F", "B", "A", "A"};
		LexiconImpl lex = new LexiconImpl(chars);
		new MergeSort().sortHelper(words, lex);
		assertArrayEquals(expected, words);
	}
	
	@Test
	// This is just making sure that your sort actually starts and stops the timer properly, since the actual sorting was tested earlier
	public void test08_SorterWithStatisticsSort() {
		char[] chars = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
		LexiconImpl lex = new LexiconImpl(chars);
		String[] words = new String[] {"G", "A", "Q", "W", "X", "B", "F", "A"};
		SorterWithStatistics sorter = new MergeSort();
		sorter.sort(words, lex);
		assertTrue(sorter.getTotalTimeToSortWords() > 0);
	}
	
	@Test
	// This makes sure that your report has the right stuff in it
	public void test09_SorterWithStatisticsGetReport() {
		char[] chars = "ZYXWVUTSRQPONMLKJIHGFEDCBA".toCharArray();
		LexiconImpl lex = new LexiconImpl(chars);
		String[] words = new String[] {"G", "A", "Q", "W", "X", "B", "F", "A"};
		SorterWithStatistics sorter = new MergeSort();
		sorter.sort(words, lex);
		assertEquals("MergeSort: ", sorter.getReport().substring(0, 11));
		assertTrue(Double.parseDouble(sorter.getReport().substring(11)) > 0);
		
		sorter = new QuickSort();
		sorter.sort(words, lex);
		assertEquals("QuickSort: ", sorter.getReport().substring(0, 11));
		assertTrue(Double.parseDouble(sorter.getReport().substring(11)) > 0);
		
		sorter = new SelectionSort();
		sorter.sort(words, lex);
		assertEquals("SelectionSort: ", sorter.getReport().substring(0, 15));
		assertTrue(Double.parseDouble(sorter.getReport().substring(15)) > 0);
	}
	
	@Test
	public void test10_EvalSortsReadCharacterOrderingEx() throws FileNotFoundException {
		ex.expect(FileNotFoundException.class);
		try {
			EvalSorts.readCharacterOrdering("VeryFakeFileName");
		} catch (FileConfigurationException e) {
			fail("You shouldn't be getting this exception here");
		}
	}
	
	@Test
	public void test11_EvalSortsReadWordsFileEx() throws FileNotFoundException {
		ex.expect(FileNotFoundException.class);
		LexiconImpl lex = new LexiconImpl(new char[] {'A'});
		try {
			EvalSorts.readWordsFile("VeryFakeFileName", lex);
		} catch (FileConfigurationException e) {
			fail("You shouldn't be getting this exception here");
		}
	} 
}