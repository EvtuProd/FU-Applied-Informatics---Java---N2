import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Voter> voters = readVotersFromFile("input.csv");
        Map<Integer, Integer> scores = calculateScores(voters);
        List<List<Integer>> result = condorcetOrder(scores);
        writeResultToFile(result, "result.txt");
        System.out.println("Voting Results:");
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            System.out.println("Option " + entry.getKey() + ": " + entry.getValue() + " votes");
        }
        System.out.println("Condorcet Order: " + result);
    }

    private static List<Voter> readVotersFromFile(String fileName) {
        List<Voter> voters = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Integer> preferences = new ArrayList<>();
                String[] preferenceStrings = line.split(",");
                for (String preferenceString : preferenceStrings) {
                    preferences.add(Integer.parseInt(preferenceString));
                }
                voters.add(new Voter(preferences));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return voters;
    }

    private static Map<Integer, Integer> calculateScores(List<Voter> voters) {
        Map<Integer, Integer> scores = new HashMap<>();
        for (Voter voter : voters) {
            List<Integer> preferences = voter.getPreferences();
            for (int i = 0; i < preferences.size(); i++) {
                int option = preferences.get(i);
                scores.put(option, scores.getOrDefault(option, 0) + preferences.size() - i);
            }
        }
        return scores;
    }

    private static List<List<Integer>> condorcetOrder(Map<Integer, Integer> scores) {
        Map<Integer, List<Integer>> scoreGroups = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            int score = entry.getValue();
            scoreGroups.computeIfAbsent(score, k -> new ArrayList<>()).add(entry.getKey());
        }

        List<List<Integer>> result = new ArrayList<>(scoreGroups.values());
        result.sort((group1, group2) -> Integer.compare(scores.get(group2.get(0)), scores.get(group1.get(0))));
        return result;
    }

    private static void writeResultToFile(List<List<Integer>> result, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (List<Integer> group : result) {
                if (result.indexOf(group) > 0) {
                    writer.write(",");
                }
                if (group.size() == 1) {
                    writer.write(String.valueOf(group.get(0)));
                } else {
                    writer.write("[");
                    for (int i = 0; i < group.size(); i++) {
                        if (i > 0) {
                            writer.write(",");
                        }
                        writer.write(String.valueOf(group.get(i)));
                    }
                    writer.write("]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}