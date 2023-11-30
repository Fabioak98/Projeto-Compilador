package maquinaVitual;

public class Instrucao {
    private String label;
    private String comando;
    private String parameter1;
    private String parameter2;

    public Instrucao(String line) {
        this.label = line.substring(0, 3).strip();
        this.comando = line.substring(4, 11).strip();
        this.parameter1 = line.substring(12, 15).strip();
        this.parameter2 = line.substring(16, 19).strip();

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }
}
