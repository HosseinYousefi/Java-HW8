public interface DispenseChain {
    void setNextChain(DispenseChain nextChain);
    DispenseChain nextChain();
    boolean dispense(int remainder);
}
