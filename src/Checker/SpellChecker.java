package Checker;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

public class SpellChecker {
    /**
     The following content was used in the file inputted by user to test code's logic:
     many words
     will exist for sure
     others, will not html
     hashset treeset isempty href filein
     txt filein
     pre html hasnext
     wordlist getinputfilenamefromuser
     jfilechooser filedialog filedialog
     setdialogtitle int
     */
    public static void main(String[] args) throws FileNotFoundException {
        printSuggestions(loadDictionary());
    }

    static HashSet<String> loadDictionary() throws FileNotFoundException {
        // Please, make sure to replace the following file path for the one in your computer.
        Scanner filein = new Scanner(new File("src/SpellChecker/words.txt"));
        HashSet<String> dictionary = new HashSet<>();

        while (filein.hasNext()) {
            String tk = filein.next();
            dictionary.add(tk.toLowerCase());
        }

        assert dictionary.size() == 72875; // asserts that dictionary has same qty of items as provided file for lab
        return dictionary;
    }

    private static void printSuggestions(HashSet<String> dictionary) throws FileNotFoundException {
        TreeSet<String> deduplicatedWords = getInputWords();

        for (String word : deduplicatedWords) {
            if (!dictionary.contains(word)) {
                TreeSet<String> corrections = corrections(word, dictionary);
                String serializedOutput = serializeOutput(corrections);
                System.out.println(word + ": " + serializedOutput);
            }
        }
    }

    static TreeSet<String> getInputWords() throws FileNotFoundException {
        // no treatment added to deal with user cancelling action because it was not required for this assignment
        Scanner in = new Scanner(getInputFileNameFromUser());
        in.useDelimiter("[^a-zA-Z]+");
        TreeSet<String> deduplicatedWords = new TreeSet<>();

        while (in.hasNext()) {
            String tk = in.next();
            deduplicatedWords.add(tk.toLowerCase());
        }
        return deduplicatedWords;
    }

    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();
    }

    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary) {
        TreeSet<String> suggestions = new TreeSet<>();
        checkDeletedLetter(badWord, dictionary, suggestions);
        checkReplacedLetter(badWord, dictionary, suggestions);
        checkInsertLetter(badWord, dictionary, suggestions);
        checkSwapLetters(badWord, dictionary, suggestions);
        checkSpace(badWord, dictionary, suggestions);

        return suggestions;
    }

    private static void checkDeletedLetter(String badWord, HashSet<String> dictionary, TreeSet<String> suggestions) {
        for (int i = 0; i < badWord.length(); i++) {
            String s = badWord.substring(0, i) + badWord.substring(i + 1);
            if (dictionary.contains(s)) {
                suggestions.add(s);
            }
        }
    }

    private static void checkReplacedLetter(String badWord, HashSet<String> dictionary, TreeSet<String> suggestions) {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            for (int i = 0; i < badWord.length(); i++) {
                String s = badWord.substring(0, i) + ch + badWord.substring(i + 1);
                if (dictionary.contains(s)) {
                    suggestions.add(s);
                }
            }
        }
    }

    private static void checkInsertLetter(String badWord, HashSet<String> dictionary, TreeSet<String> suggestions) {
        for (char ch = 'a'; ch <= 'z'; ch++) {
            for (int i = 0; i < badWord.length(); i++) {
                String s = badWord.substring(0, i) + ch + badWord.substring(i);
                if (dictionary.contains(s)) {
                    suggestions.add(s);
                }
            }
        }
    }

    private static void checkSwapLetters(String badWord, HashSet<String> dictionary, TreeSet<String> suggestions) {
        for (int i = 0; i < badWord.length() - 1; i++) {
            String s = badWord.substring(0, i) + badWord.substring(i + 1, i + 2) + badWord.substring(i, i + 1) + badWord.substring(i + 2);
            if (dictionary.contains(s)) {
                suggestions.add(s);
            }
        }
    }

    private static void checkSpace(String badWord, HashSet<String> dictionary, TreeSet<String> suggestions) {
        for (int i = 0; i < badWord.length(); i++) {
            String s1 = badWord.substring(0, i);
            String s2 = badWord.substring(i);

            if (dictionary.contains(s1) && dictionary.contains(s2)) {
                suggestions.add(s1 + " " + s2);
            }
        }
    }

    static String serializeOutput(TreeSet<String> suggestions) {
        String removedSquareBrackets = suggestions.toString().substring(1, suggestions.toString().length() - 1);
        return removedSquareBrackets.equals("")? "(no suggestions)": removedSquareBrackets;
    }
}
