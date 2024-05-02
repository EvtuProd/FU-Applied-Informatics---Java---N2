import java.util.List;

public class Voter {
    private List<Integer> preferences;

    public Voter(List<Integer> preferences) {
        this.preferences = preferences;
    }

    public List<Integer> getPreferences() {
        return preferences;
    }
}