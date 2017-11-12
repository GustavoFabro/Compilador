package compilador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Simbolos;

public class AnalisadorSemantico {

    private ArrayList<Simbolos> simbolos = new ArrayList<>();

    public boolean isDeclaracaoValida(String ident, int line) {
        if (getSimByIdent(ident)) {
            showMessageError("Variável já declarada: " + ident + "\nLinha: " + line);
            return false;
        }

        simbolos.add(new Simbolos(ident));
        return true;
    }

    public boolean isChamadaValida(String ident, int type, int line) {
        if (getSimByIdent(ident)) {
            return true;
        }

        showMessageError((type == 1? "Variável" : "Função") + " não declarada: " + ident
                + "\nLinha: " + line);
        return false;
    }

    public boolean isChamadaFuncaoValida(String function, int numParam, int line) {
        for (int i = 0; i < simbolos.size(); i++) {
            if (simbolos.get(i).getIdentificador().equals(function)
                    && simbolos.get(i).getQtdParametros() == numParam) {
                return true;
            }
        }
        showMessageError("Número de parâmetros errado na chamada da função: " + function
                + "\nLinha: " + line);
        return false;
    }

    public boolean incrementaParams(String ident) {
        increment(ident);

        return true;
    }

    private void increment(String ident) {
        for (int i = 0; i < simbolos.size(); i++) {
            if (simbolos.get(i).getIdentificador().equals(ident)) {
                simbolos.get(i).setQtdParametros(
                        simbolos.get(i).getQtdParametros() + 1);
            }
        }
    }

    public boolean getSimByIdent(String cIdent) {
        for (int i = 0; i < simbolos.size(); i++) {
            if (simbolos.get(i).getIdentificador().equals(cIdent)) {
                return true;
            }
        }

        return false;
    }

    public void showMessageError(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "Erro Semântico", JOptionPane.ERROR_MESSAGE);
    }

    public void reset() {
        simbolos.clear();
    }
}