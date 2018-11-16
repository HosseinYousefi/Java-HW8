import java.util.*;

public class BankDepartment implements Observer {

    List<ATM> atms;
    int lastEmptied = -1;

    public BankDepartment(List<ATM> atms) {
        this.atms = atms;
        for (int i = 0; i < this.atms.size(); ++i)
            this.atms.get(i).addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("ATM #" + ((ATM)o).id + " is out of cash!");
        lastEmptied = ((ATM)o).id;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ArrayList<Integer> denom = new ArrayList();
        denom.add(5);
        denom.add(10);
        denom.add(20);
        denom.add(50);
        denom.add(100);
        ATM atm = new ATM(denom, "€", 10);
        atm.setId(100);
        ATM atm1 = (ATM) atm.clone();
        atm1.setId(200);
        List<ATM> l = new ArrayList<>();
        l.add(atm);
        l.add(atm1);
        BankDepartment bank = new BankDepartment(l);
        l.get(0).withdraw(1855);
        l.get(1).withdraw(1850);

//        System.out.println("ATM's balance is " + atm.getBalance());
//        System.out.println(("ATM1's balance is " + atm1.getBalance()));
    }
}
