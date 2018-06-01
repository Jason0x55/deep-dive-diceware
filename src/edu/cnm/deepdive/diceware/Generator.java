package edu.cnm.deepdive.diceware;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class is used to get a random selection of word(s) from an array of those words. (Diceware)
 */
public class Generator {

  private static final String NULL_RNG_MSG = "Random number generator must not be null.";
  private static final String NULL_WORDS_MSG = "Array of words must not be null.";
  private static final String EMPTY_WORDS_MSG = "Array of words must not be empty.";
  private static final String NEGATIVE_NUMBER_WORDS_MSG = "Number of words to be selected must not be negative.";
  private static final String INSUFFICIENT_WORDS_MSG = "Number of distinct words requested must not exceed number of words in pool.";

  private String[] words;
  private Random rng;

  /**
   * Generator constructor takes an array of words, removes duplicates, and copies those words to an array.
   * @param words Array of strings to be used.
   * @param rng Source of random to be used.
   * @throws NullPointerException Thrown if words or rng is null.
   * @throws IllegalArgumentException Thrown if passed an empty array.
   */
  public Generator(String[] words, Random rng) throws NullPointerException, IllegalArgumentException {
    if (rng == null) {
      throw new NullPointerException(NULL_RNG_MSG);
    }
    if (words == null) {
      throw new NullPointerException(NULL_WORDS_MSG);
    }
    if (words.length == 0) {
      throw new IllegalArgumentException(EMPTY_WORDS_MSG);
    }
    Set<String> pool = new HashSet<>();
    for (String word : words) {
      word = word.toLowerCase();
      if (!pool.contains(word)) {
        pool.add(word);
      }
    }
    this.words = pool.toArray(new String[pool.size()]);
    this.rng = rng;
  }

  /**
   * Returns a random word from the array of words.
   * @return returns a word as a string.
   */
  public String next() {
    return words[rng.nextInt(words.length)];
  }

  /**
   * Creates a list of random words.
   * @param numWords Number of words to be added to list.
   * @param duplicatesAllowed Duplicates allowed or not.
   * @return Returns an array of numWords length.
   * @throws NegativeArraySizeException Thrown if numWords is negative.
   * @throws IllegalArgumentException Thrown if the list of words is too small to create the requested size list.
   */
  public String[] next(int numWords, boolean duplicatesAllowed) throws NegativeArraySizeException, IllegalArgumentException {
    if (numWords < 0) {
      throw new NegativeArraySizeException(NEGATIVE_NUMBER_WORDS_MSG);
    }
    if (!duplicatesAllowed && numWords > words.length) {
      throw new IllegalArgumentException(INSUFFICIENT_WORDS_MSG);
    }
    List<String> selection = new LinkedList<>();
    while (selection.size() < numWords) {
      String pick = next();
      if (duplicatesAllowed || !selection.contains(pick)) {
        selection.add(pick);
      }
    }
    return selection.toArray(new String[selection.size()]);
  }

  /**
   * Calls next() with duplicatesAllowed set to true.
   * @param numWords Number of words to be added to list.
   * @return Returns an array of numWords length.
   * @throws NegativeArraySizeException Thrown if numWords is negative.
   */
  public String[] next(int numWords) throws NegativeArraySizeException {
    return next(numWords, true);
  }
}
