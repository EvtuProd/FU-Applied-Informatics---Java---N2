import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String csvFile = "input.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                List<String> preferences = Arrays.asList(line.split(","));
                // Создание объекта голосующего
                Voter voter = new Voter(preferences);
                // Обработка предпочтений голосующего (здесь можно вызвать алгоритм Кондорсе)
                processVoterPreferences(voter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для обработки предпочтений голосующего (здесь можно реализовать алгоритм Кондорсе)
    private static void processVoterPreferences(Voter voter) {
        // Вывод предпочтений голосующего для демонстрации
        System.out.println("Voter preferences: " + voter.getPreferences());
    }
}
