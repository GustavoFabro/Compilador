package util;

/**
 *
 * @author gustavofabro
 */
public class Simbolos {

    private String identificador;
    private int qtdParametros;

    public Simbolos(String identificador, int qtdParametros) {
        this.identificador = identificador;
        this.qtdParametros = qtdParametros;
    }

    public Simbolos(String identificador) {
        this.identificador = identificador;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getQtdParametros() {
        return qtdParametros;
    }

    public void setQtdParametros(int qtdParametros) {
        this.qtdParametros = qtdParametros;
    }
}