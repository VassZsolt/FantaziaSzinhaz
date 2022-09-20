import coder.coder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class coderTest {
    private final coder coder=new coder();

    @Test
    void encode(){
        String password="a";
        assertEquals(password.charAt(0) + 1, coder.encode(password).charAt(0));
        password="ab";
        assertEquals(password.charAt(0) + 2, coder.encode(password).charAt(0));
        password="alma";
        assertEquals(password.charAt(1) + 3, coder.encode(password).charAt(1));
        assertEquals('b', coder.encode(password).charAt(3));
        assertEquals('e', coder.encode(password).charAt(0));


        assertNotEquals('c', coder.encode(password).charAt(3));
        assertNotEquals('b', coder.encode(password).charAt(2));
        assertNotEquals('3', coder.encode(password).charAt(3));

    }

    @Test
    void decode(){
        String password="Kiskutya123";
        assertEquals(password, coder.decode(coder.encode(password)));

        password="KiSkUtYa123";
        assertEquals(password, coder.decode(coder.encode(password)));
        assertEquals(password, coder.decode(coder.encode("KiSkUtYa123")));

        password="23Levelespogácsa86";
        assertEquals(password, coder.decode(coder.encode(password)));
        assertTrue(password.equals(coder.decode(coder.encode(password))));
    }
}
