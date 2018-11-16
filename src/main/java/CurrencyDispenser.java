import java.util.*;

public class CurrencyDispenser extends Observable implements DispenseChain, Cloneable {
    private DispenseChain chain = null;

    String currency;
    int value;
    int billsLeft;
    Observer atm;

    public CurrencyDispenser(String currency, int value, int billsLeft) {
        this.currency = currency;
        this.value = value;
        this.billsLeft = billsLeft;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        atm = o;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CurrencyDispenser clone = (CurrencyDispenser) super.clone();
        clone.currency = currency;
        clone.value = value;
        clone.billsLeft = billsLeft;
        clone.chain = chain;
        return clone;
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
        atm.update(this, null);
    }

    @Override
    public void setNextChain(DispenseChain nextChain) { this.chain = nextChain; }

    @Override
    public DispenseChain nextChain() { return chain; }

    @Override
    public boolean dispense(int remainder) {
        if (remainder == 0)
            return true;
        if (remainder >= value && billsLeft > 0) {
            int num = remainder / value;
            if (num > billsLeft)
                num = billsLeft;
            int willBecome = remainder - num * value;

            if (willBecome == 0) {
                billsLeft -= num;
                return true;
            }

            if (nextChain() != null) {
                if (nextChain().dispense(willBecome)) {
                    billsLeft -= num;
                    if (billsLeft == 0)
                        notifyObservers();
                    return true;
                }
            }
        }
        if (nextChain() != null)
            return nextChain().dispense(remainder);
        return false;
    }
}
