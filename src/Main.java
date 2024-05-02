import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Чтение данных из файла и создание списка голосующих
        List<Voter> voters = readVotersFromFile("input.csv");

        // Вычисление количества голосов за каждый вариант
        Map<Integer, Integer> scores = calculateScores(voters);

        // Определение порядка предпочтений по методу Кондорсё
        List<Integer> result = condorcetOrder(scores);

        // Запись результата в файл
        writeResultToFile(result, "result.txt");

        // Вывод результатов голосования в консоль
        System.out.println("Voting Results:");
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            System.out.println("Option " + entry.getKey() + ": " + entry.getValue() + " votes");
        }
        System.out.println("Condorcet Order: " + result);
    }

    // Метод для чтения данных из файла и создания списка голосующих
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

    // Метод для вычисления количества голосов за каждый вариант
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

    // Метод для определения порядка предпочтений по методу Кондорсё
    private static List<Integer> condorcetOrder(Map<Integer, Integer> scores) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            result.add(entry.getKey());
        }
        return result;
    }

    // Метод для записи результата в файл
    private static void writeResultToFile(List<Integer> result, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < result.size(); i++) {
                if (i > 0) {
                    writer.write(",");
                }
                writer.write(String.valueOf(result.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
