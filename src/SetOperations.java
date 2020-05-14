import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class SetOperations {
    public static void main(String[] args) {
        String initialMsg = "Please, input valid set operations using the format '[2, 5] <operator> [9,6] in each line:";

        while (true) {
            String input = JOptionPane.showInputDialog(null, initialMsg);
            if (input == null) {
                break;
            }
            System.out.println("Calculating result for input: " + input);
            parseLine(input);
        }

        System.out.println("Exiting program!");
    }

    static void parseLine(String line) {
        try {
            String[] stringSets = line.split("[+\\-*]");

            if (stringSets.length != 2) {
                throw new Exception("Invalid parameters.");
            }

            if (line.contains("+")) {
                System.out.println("Result for sets union :" + setUnion(stringSets));
            } else if (line.contains("-")) {
                System.out.println("Result for sets difference :" + setDifference(stringSets));
            } else if (line.contains("*")) {
                System.out.println("Result for sets intersection :" + setIntersection(stringSets));
            } else {
                throw new Exception("No valid operator was found in given input.");
            }
        } catch (Exception e) {
            System.out.println("Could not parse input: " + line);
            e.printStackTrace();
        }
    }

    static TreeSet<Integer> setUnion(String[] stringSets) throws Exception {
        List<TreeSet<Integer>> treeSets = parseSets(stringSets);
        treeSets.get(0).addAll(treeSets.get(1));

        return treeSets.get(0);
    }

    static TreeSet<Integer> setDifference(String[] stringSets) throws Exception {
        List<TreeSet<Integer>> treeSets = parseSets(stringSets);
        treeSets.get(0).removeAll(treeSets.get(1));

        return treeSets.get(0);
    }

    static TreeSet<Integer> setIntersection(String[] stringSets) throws Exception {
        List<TreeSet<Integer>> treeSets = parseSets(stringSets);
        treeSets.get(0).retainAll(treeSets.get(1));

        return treeSets.get(0);
    }

    static List<TreeSet<Integer>> parseSets(String[] sets) throws Exception {
        TreeSet<Integer> A = parseSetString(sets[0]);
        TreeSet<Integer> B = parseSetString(sets[1]);

        List<TreeSet<Integer>> treeSets = new ArrayList<>();
        treeSets.add(A);
        treeSets.add(B);

        return treeSets;
    }

    static TreeSet<Integer> parseSetString(String set) throws Exception {
        Pattern r = Pattern.compile("\\d*");
        TreeSet<Integer> treeSet = new TreeSet<>();

        if (!set.strip().startsWith("[") || !set.strip().endsWith("]")) {
            throw new Exception("Invalid format for set: " + set.strip());
        }

        String strippedSet = set.strip().substring(1, set.strip().length() - 1);
        String[] splitSet = strippedSet.split(",");

        for (String item: splitSet) {
            String number = item.strip();
            if (r.matcher(number).find()) {
                treeSet.add(Integer.parseInt(number));
            }
        }
        return treeSet;
    }
}
