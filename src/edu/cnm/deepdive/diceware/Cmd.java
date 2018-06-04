package edu.cnm.deepdive.diceware;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is a basic command line interface for the Generator class.
 */
public class Cmd {

  private static final String DEFAULT_FILENAME = "wordlist.txt";
  private static final int DEFAULT_NUMBER_WORDS = 6;

  /**
   * Takes command line args and prints out random words.
   * @param args Takes two optional arguments. Defaults to wordlist.txt and 6 words total.
   * @throws FileNotFoundException Thrown if there is an issue loading the file.
   */
  public static void main(String[] args) throws FileNotFoundException {
    int numWords = DEFAULT_NUMBER_WORDS;
    String fileName = DEFAULT_FILENAME;
    if (args.length > 0) {
      fileName = args[0];
      if (args.length > 1) {
        numWords = Integer.parseInt(args[1]);
      }
    }
    String[] words = readWordList(fileName);
    Random rng = new SecureRandom();
    Generator generator = new Generator(words, rng);
    String[] passPhrase = generator.next(numWords);
    for (String word : passPhrase) {
      System.out.print(word + " ");
    }
    System.out.println("");
  }

  /**
   * Reads in a wordlist and places them into an array to be passed to the Generator class.
   * @param filename Name of file to be loaded.
   * @return Returns an array of the wordlist.
   * @throws FileNotFoundException Thrown if there is an issue loading the file.
   */
  private static String[] readWordList(String filename) throws FileNotFoundException {
    try (Scanner scanner = new Scanner(new File(filename))) {
      List<String> words = new LinkedList<>();
      scanner.useDelimiter("\\s*\\d+\\s+");
      while (scanner.hasNext("\\S+")) {
        String word = scanner.next("\\S+");
        words.add(word);
      }
      return words.toArray(new String[words.size()]);
    }
  }

}
