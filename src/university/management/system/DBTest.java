package university.management.system;

public class DBTest {
    public static void main(String[] args) {
        try {
            Conn c = new Conn();
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
