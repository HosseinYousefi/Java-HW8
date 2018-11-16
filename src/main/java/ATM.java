import java.util.*;

public class ATM extends Observable implements Iterable<CurrencyDispenser>, Cloneable, Observer {

    private CurrencyDispenser[] currencyDispensers;
    int id;
    Observer bankDepartment;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        bankDepartment = o;
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        bankDepartment.update(this, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("We're out of " + ((CurrencyDispenser)o).value + ((CurrencyDispenser)o).currency + " bills.");
    }

    public ATM(List<Integer> denominations, String currency, int initialBills) {
        currencyDispensers = new CurrencyDispenser[denominations.size()];
        denominations.sort((a, b) -> b - a);
        for (int i = 0; i < denominations.size(); ++i) {
            currencyDispensers[i] = new CurrencyDispenser(currency, denominations.get(i), initialBills);
            currencyDispensers[i].addObserver(this);
        }
        for (int i = 0; i + 1 < denominations.size(); ++i)
            currencyDispensers[i].setNextChain(currencyDispensers[i + 1]);
    }

    public boolean withdraw(int amount) {
        boolean res = currencyDispensers[0].dispense(amount);
        if (getBalance() == 0)
            notifyObservers();
        return res;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ATM clone = (ATM)super.clone();

        clone.currencyDispensers = new CurrencyDispenser[currencyDispensers.length];
        for (int i = 0; i < currencyDispensers.length; ++i) {
            clone.currencyDispensers[i] = (CurrencyDispenser) currencyDispensers[i].clone();
            clone.currencyDispensers[i].addObserver(this);
        }
        for (int i = 0; i + 1 < currencyDispensers.length; ++i)
            clone.currencyDispensers[i].setNextChain(clone.currencyDispensers[i + 1]);
        clone.id = id;

        return clone;
    }

    public class CDIterator implements Iterator {
        int position;

        public CDIterator(int position) {
            this.position = position;
        }

        @Override
        public boolean hasNext() {
            return position < currencyDispensers.length;
        }

        @Override
        public Object next() {
            return currencyDispensers[position++];
        }
    }

    public int getBalance() {
        int balance = 0;
        for (CurrencyDispenser cd: this)
            balance += cd.billsLeft * cd.value;
        return balance;
    }

    @Override
    public Iterator iterator() {
        return new CDIterator(0);
    }

}