import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ATMTest {
    ArrayList<Integer> denom;
    ATM atm;
    BankDepartment bankDepartment;

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
        atm.setId(111);
        List<ATM> atms = new ArrayList<>();
        atms.add(atm);
        bankDepartment = new BankDepartment(atms);
    }

    @Test
    public void testCloning() throws CloneNotSupportedException {
        ATM atm2 = (ATM)atm.clone();
        atm2.setId(222);
        atm.withdraw(150);
        atm2.withdraw(145);
        assertNotEquals(atm.getBalance(), atm2.getBalance());
    }

    @Test
    public void testWithdrawingOK() {
        assertEquals(true, atm.withdraw(1425));
    }

    @Test
    public void testWithdrawingRest() {
        assertFalse(atm.withdraw(1427));
    }

    @Test
    public void testWithdrawingTooMuch() {
        assertFalse(atm.withdraw(10000));
        assertEquals(4425, atm.getBalance());
    }

    @Test
    public void testEmptyNotif() {
        atm.withdraw(4425);
        assertEquals(111, bankDepartment.lastEmptied);
    }

    @Test
    public void testEmptyNotifNotCalled() {
        atm.withdraw(100);
        assertEquals(-1, bankDepartment.lastEmptied);
    }

    @Test
    public void testGetBalance() {
        assertEquals(4425, atm.getBalance());
        testWithdrawingOK();
        assertEquals(3000, atm.getBalance());
    }
}