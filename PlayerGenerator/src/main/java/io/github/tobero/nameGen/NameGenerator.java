package io.github.tobero.nameGen;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import io.github.tobero.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class NameGenerator {

    private int[][][] probabilities;
    private ArrayList<Character> characters;
    private TreeMap<String, String> input;
    private TreeMap<String, String> results;
    private boolean initialized;

    private static final char TERMINATOR = '\0';
    private static final Character[] defaultChars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', TERMINATOR };

    // constructors ------------------------------------------------------------------------------------------------

    public NameGenerator() {
        probabilities = new int[defaultChars.length][defaultChars.length][defaultChars.length];
        characters = new ArrayList<Character>();
        for (Character s : defaultChars)
            characters.add(s);
        results = new TreeMap<String, String>();
        input = new TreeMap<String, String>();
        initialized = false;
    }

    // NameGenerator created from save file
    public NameGenerator(File source) {
        // TODO write constructor to load from save file
    }

    // private methods ---------------------------------------------------------------------------------------------

    private String[] readNamesFromInput(File input) {
        try (Scanner scan = new Scanner(input)) {
            LinkedList<String> list = new LinkedList<String>();
            while (scan.hasNext())
                list.add(scan.next());
            return list.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addStringToProbability(String name) {
        String lowerName = name.toLowerCase();
        input.put(lowerName, lowerName);
        char last1 = TERMINATOR, last2 = TERMINATOR;
        int index = 0;
        while (index < lowerName.length()) {
            if (charIndex(lowerName.charAt(index)) != -1) {
                char current = lowerName.charAt(index);
                probabilities[charIndex(last1)][charIndex(last2)][charIndex(current)]++;
                last1 = last2;
                last2 = current;
                index++;
            } else {
                index++;
            }
        }
        char current = TERMINATOR;
        probabilities[charIndex(last1)][charIndex(last2)][charIndex(current)]++;
    }

    // chooses a character from the probability matrix based on the previous two chars
    // precondition: $last1 and $last2 are recognized characters that have previously appeared in that order
    private char nextCharByLast(char last1, char last2) {
        int total = 0;
        for (int i : probabilities[charIndex(last1)][charIndex(last2)]) {
            total += i;
        }
        total = (new Random()).nextInt(total);
        int index = 0, subTotal = 0;
        do {
            subTotal += probabilities[charIndex(last1)][charIndex(last2)][index++];
        } while (subTotal <= total);
        return (characters.get(--index));
    }

    // returns the index in $chars for $c
    // Note: if the character is a letter it must be lower case
    private int charIndex(char c) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).equals(c))
                return i;
        }
        return -1;
    }

    // public methods ----------------------------------------------------------------------------------------------

    // generates the probabilities matrix from $source
    // precondition: $source is a correctly formated .txt file
    public void generateProbabilities(File input) {
        String[] sourceNames = readNamesFromInput(input);
        for (String name : sourceNames) {
            addStringToProbability(name);
        }
        initialized = true;
    }

    public String generateName() {
        String result = "";
        char last1 = TERMINATOR, last2 = TERMINATOR;
        do {
            char temp = nextCharByLast(last1, last2);
            last1 = last2;
            last2 = temp;
            if (last2 != TERMINATOR)
                result += Character.toString(last2);
        } while (last2 != TERMINATOR);
        return result;
    }

    // generates names until one that is not already in $results is found
    public String generateNextUnique() {
        String name = generateName();
        while (results.containsKey(name))
            name = generateName();
        results.put(name, name);
        return name;
    }

    // generates names until one that is not already in $results is found
    // stops once
    public String generateNextUnique(int maxAttempts) {
        String name = generateName();
        int count = 0;
        while (results.containsKey(name) && count++ < maxAttempts)
            name = generateName();
        results.put(name, name);
        if (count < maxAttempts)
            return name;
        return null;
    }

    // returns true if $name has already been added to $results
    public boolean hasName(String name) {
        return results.containsKey(name) || input.containsKey(name);
    }

    public void addName(String name) {
        if (!results.containsKey(name))
            results.put(name, name);
    }

    public String[] listOfNames() {
        ArrayList<String> names = new ArrayList<String>();
        Iterator<String> it = results.keySet().iterator();
        while (it.hasNext())
            names.add(it.next());
        Collections.sort(names);
        return names.toArray(new String[0]);
    }

    // adds $c to the list of recognized characters
    // automatically re-generates the probability matrix
    // precondition: $c is not already in $characters
    public void addCharacter(char c) {
        characters.add(c);
        resetProbabilities();
    }

    // removes $c from the list of recognized characters
    // automatically re-generates the probability matrix
    // precondition: $c is in $characters
    public void removeCharacter(char c) {
        int index = -1;
        for (int i = 0; i < characters.size(); i++)
            if (characters.get(i).equals(c))
                index = i;
        characters.remove(index);

        resetProbabilities();
    }

    // returns true if the name generator recognizes $c
    public boolean recognizesCharacter(char c) {
        for (Character character : characters)
            if (character.equals(c))
                return true;
        return false;
    }

    // writes the probability matrix to the output directory
    public void saveProbabilities() {
        // TODO write save method
    }

    public void resetProbabilities() {
        probabilities = new int[characters.size()][characters.size()][characters.size()];
        results = new TreeMap<String, String>();
        initialized = false;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public static List<String> getProcedualNames(int namesNeeded, int minNameLength, int maxNameLength, int protection) {
        List<String> finalNames = new ArrayList<>();

        List<String> firstNames = _getProcedualNames(namesNeeded, minNameLength, maxNameLength, protection);
        List<String> lastNames = _getProcedualNames(namesNeeded, minNameLength, maxNameLength, protection);

        for (int i = 0; i < namesNeeded; i++) {
            String firstName = firstNames.get(Utils.getRandomNumber(0, firstNames.size()-1));
            String lastName = lastNames.get(Utils.getRandomNumber(0, lastNames.size()-1));

            finalNames.add(firstName + " " + lastName);
        }

        return finalNames;
    }

    private static List<String> _getProcedualNames(int namesNeeded, int minNameLength, int maxNameLength, int protection) {
        NameGenerator names = new NameGenerator();
        names.resetProbabilities();
        names.generateProbabilities(new File("C:\\Users\\Tobias\\Desktop\\Development\\Android\\FootballManager\\PlayerGenerator\\first_names.txt"));

        List<String> foundNames = new ArrayList<String>();
        int _protection = 0;
        while (foundNames.size() <= namesNeeded) {
            String n = names.generateName();
            if (n.length() < maxNameLength && n.length() > minNameLength) {
                foundNames.add(n);
            }
            if (_protection++ >= protection) {
                break;
            }
        }

        return foundNames;
    }

    public static List<String> getSampledNames(int namesNeeded) {
        File firstNamesFile = new File("C:\\Users\\Tobias\\Desktop\\Development\\Android\\FootballManager\\PlayerGenerator\\first_names.txt");
        File lastNamesFile = new File("C:\\Users\\Tobias\\Desktop\\Development\\Android\\FootballManager\\PlayerGenerator\\last_names.txt");

        try {
            String firstNamesText = Files.asCharSource(firstNamesFile, Charsets.UTF_8).read();
            String lastNamesText = Files.asCharSource(lastNamesFile, Charsets.UTF_8).read();

            List<String> firstNamesList = new ArrayList<>(Arrays.asList(firstNamesText.split("\\r?\\n")));
            List<String> lastNamesList = new ArrayList<>(Arrays.asList(lastNamesText.split("\\r?\\n")));

            List<String> finalNames = new ArrayList<>();

            for (int i = 0; i < namesNeeded; i++) {
                String firstName = firstNamesList.get(Utils.getRandomNumber(0, firstNamesList.size()-1));
                String lastName = lastNamesList.get(Utils.getRandomNumber(0, lastNamesList.size()-1));

                finalNames.add(firstName + " " + lastName);
            }

            return finalNames;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
