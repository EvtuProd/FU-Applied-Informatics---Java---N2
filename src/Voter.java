import java.util.List;

public class Voter {
    private List<String> preferences;

    public Voter(List<String> preferences) {
        this.preferences = preferences;
    }

    public List<String> getPreferences() {
        return preferences;
    }
}
