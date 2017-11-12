package util;

import compilador.Token;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author gustavofabro
 */
public class TableTokensModel extends AbstractTableModel {

    private ArrayList<Token> dados;
    private final String[] colunas = {"Cód.", "Token", "Linha"};

    public TableTokensModel() {
        dados = new ArrayList<Token>();
    }

    public void addRow(Token token) {
        this.dados.add(token);
        this.fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return dados.get(linha).getCodigo();
            case 1:
                return dados.get(linha).getToken();
            case 2:
                return dados.get(linha).getLinha();
        }
        return null;
    }

    public void removeRow(int linha) {
        this.dados.remove(linha);
        this.fireTableRowsDeleted(linha, linha); //intervalo

    }

    public void clearTable() {
        this.fireTableRowsDeleted(0, getRowCount());
        this.dados.clear();
    }

    public Token getToken(int linha) {
        return this.dados.get(linha);
    }

    @Override //é chamada automaticamente
    public String getColumnName(int num) {
        return this.colunas[num];
    }

    public String[] getColunas() {
        return colunas;
    }

}