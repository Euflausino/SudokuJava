package br.com.euflausino.model;

public class Space {
    private Integer valAtual;
    private final int expected;
    private final boolean fixed;

    public Space(int expected, boolean fixed) {
        this.expected = expected;
        this.fixed = fixed;
        if(fixed){
            valAtual = expected;
        }
    }

    public void setValAtual(Integer valAtual) {
        if(fixed) return;
        this.valAtual = valAtual;
    }

    public Integer getValAtual() {

        return valAtual;
    }
    public void clearSpace(){
        setValAtual(null);
    }

    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
}
