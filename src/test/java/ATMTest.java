import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ATMTest  extends TestCase {
    ArrayList<Integer> denom;
    ATM atm;

    @Before
    public void setUp() throws Exception {
        denom = new ArrayList();
        denom.add(5);
        denom.add(10);
        denom.add(20);
        denom.add(50);
        denom.add(100);
        denom.add(200);
        denom.add(500);
        atm = new ATM(denom, "€", 5);
    }

    @Test
    public void testWithdrawingOK() {
        assertEquals(atm.withdraw(1425), "OK");
    }

    @Test
    public void testWithdrawingRest() {
        assertEquals(atm.withdraw(1427), "ERROR");
    }

    @Test
    public void testWithdrawingTooMuch() {
        assertEquals(atm.withdraw(10000), "ERROR");
    }

    public void testGetBalance() {
        assertEquals(atm.getBalance(), 4425);
        testWithdrawingOK();
        assertEquals(atm.getBalance(), 3000);
    }
}