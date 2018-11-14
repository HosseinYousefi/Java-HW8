import java.util.*;

public class ATM implements Iterable<CurrencyDispenser> {

    private CurrencyDispenser[] currencyDispensers;

    public ATM(List<Integer> denominations, String currency, int initialBills) {
        currencyDispensers = new CurrencyDispenser[denominations.size()];
        denominations.sort((a, b) -> b - a);
        for (int i = 0; i < denominations.size(); ++i)
            currencyDispensers[i] = new CurrencyDispenser(currency, denominations.get(i), initialBills);
        for (int i = 0; i + 1 < denominations.size(); ++i)
            currencyDispensers[i].setNextChain(currencyDispensers[i + 1]);
    }

    public String withdraw(int amount) {
        return currencyDispensers[0].dispense(amount);
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