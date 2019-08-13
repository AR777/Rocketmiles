import com.beust.testng.TestNG;

public class Main {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        Class[] classes = new Class[]{SearchTests.class};
        testng.setTestClasses(classes);
        testng.run();

    }
}