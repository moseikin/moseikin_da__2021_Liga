
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    public static String removeSeparators(String s) {
        return s.replaceAll("\n", "").replaceAll("\r", "");
    }

}