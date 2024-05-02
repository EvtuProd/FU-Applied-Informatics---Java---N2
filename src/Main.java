import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        List<Voter> voters = readVotersFromFile("input.csv");
        Map<Integer, Integer> scores = calculateScores(voters);
        List<Integer> result = condorcetOrder(scores);
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

    private static List<Integer> condorcetOrder(Map<Integer, Integer> scores) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Integer> result = new ArrayList<>();
        int maxScore = list.get(0).getValue();
        for (Map.Entry<Integer, Integer> entry : list) {
            if (entry.getValue() == maxScore) {
                result.add(entry.getKey());
            } else {
                break;
            }
        }
        return result;
    }
}

