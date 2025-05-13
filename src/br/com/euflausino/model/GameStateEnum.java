package br.com.euflausino.model;

public enum GameStateEnum {
    NON_STARTED("Nao iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("Completo");

    private String label;

     GameStateEnum(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
