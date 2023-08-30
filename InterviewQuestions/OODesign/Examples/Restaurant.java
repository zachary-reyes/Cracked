package Examples;
// Singleton Class Example from Cracking the Coding Interview, 6th Edition
public class Restaurant {
    private static Restaurant _instance = null;

    protected Restaurant() {}

    public static Restaurant getInstance() {
        if (_instance == null) {
            _instance = new Restaurant();
        }
        return _instance;
    }
}