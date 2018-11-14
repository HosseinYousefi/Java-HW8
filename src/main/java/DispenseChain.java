public interface DispenseChain {
    void setNextChain(DispenseChain nextChain);
    DispenseChain nextChain();
    String dispense(int remainder);
}
