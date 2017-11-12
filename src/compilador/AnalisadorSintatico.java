package compilador;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author gustavofabro
 */
public class AnalisadorSintatico {

    private final AnalisadorLexico lexico;
    private final AnalisadorSemantico semantico;
    private final Tabelas tabelas;
    private final ArrayList<Integer> pilha = new ArrayList<>();
    private ArrayList<Integer> regrasRevers = new ArrayList<>();
    private int x;
    private int numParmCFunction;
    private String pilhaDebug;
    private String currentFuntion;
    private String currentFuntionChamada;
    private boolean isChamandoFuncao = false;

    public AnalisadorSintatico(AnalisadorLexico lexico) {
        this.lexico = lexico; //recebe a instância original
        tabelas = new Tabelas();
        semantico = new AnalisadorSemantico();
    }

    public void inicializaSintatico(Token a) {
        pilhaDebug = "";
        numParmCFunction = 0;
        currentFuntion = "";
        isChamandoFuncao = false;
        semantico.reset();
        pilha.clear();

        pilha.add(54); //$ (EOF)
        pilha.add(58); //símbolo inicial
        addPilhaDebug(pilha);
        analisar(a);
    }

    public void analisar(Token a) {
        if (a == null) {
            return;
        }

        x = pilha.get(pilha.size() - 1);

        while (true) {

            while (x > 199 && x < 207) { //se for ação semântica
                if (!analisaSemantico(x, pilha)) {
                    return;
                }

                pilha.remove(pilha.size() - 1);
                x = pilha.get(pilha.size() - 1);
            }

            if (x == 10) {
                pilha.remove(pilha.size() - 1);
                x = pilha.get(pilha.size() - 1);
                addPilhaDebug(pilha);

            } else if (x <= 57) {
                if (x == a.getCodigo()) {
                    pilha.remove(pilha.size() - 1);
                    addPilhaDebug(pilha);

                    if (x == 54) {
                        JOptionPane.showMessageDialog(null, "Sucesso");
                        break;
                    }

                    analisar(lexico.getNextToken());
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "Linha: " + a.getLinha(),
                            "Erro Sintático", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else
            if (tabelas.tabParsing[x][a.getCodigo()] != 0) {
                pilha.remove(pilha.size() - 1);

                regrasRevers
                        = tabelas.getRegras(tabelas.tabParsing[x][a.getCodigo()] - 1);

                for (int i = 0; i < regrasRevers.size(); i++) {
                    pilha.add(regrasRevers.get(i));
                }

                x = pilha.get(pilha.size() - 1);

                addPilhaDebug(pilha);

            } else {
                JOptionPane.showMessageDialog(null, "Linha: " + a.getLinha(),
                        "Erro Sintático", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    public void addPilhaDebug(ArrayList<Integer> pilha) {
        for (int i = 0; i < pilha.size(); i++) {
            pilhaDebug += pilha.get(i) + " ";
        }
        pilhaDebug += "\n";
    }

    public String getPilha() {
        return this.pilhaDebug;
    }

    public boolean analisaSemantico(int action, ArrayList pilha) {
        boolean isValido = false;
        int posAction;
        int posIdent;
        String ident;
        String cAction = "";

        switch (action) {
            case 200:
                posIdent = lexico.getListTokens().size() - 2;
                ident = lexico.getListTokens().get(posIdent).getToken();

                if (!isChamandoFuncao) {
                    posIdent = lexico.getListTokens().size() - 2;
                    ident = lexico.getListTokens().get(posIdent).getToken();

                    isValido = semantico.isDeclaracaoValida(ident, lexico.getLine());
                } else {
                    isValido = semantico.isChamadaValida(ident, 1, lexico.getLine());
                }
                break;
            case 201:
                posAction = lexico.getListTokens().size() - 3;
                cAction = lexico.getListTokens().get(posAction).getToken();

                posIdent = lexico.getListTokens().size() - 2;
                ident = lexico.getListTokens().get(posIdent).getToken();

                if (cAction.equals("chamafuncao")) {
                    isChamandoFuncao = true;
                    currentFuntionChamada = ident;
                    isValido = semantico.isChamadaValida(ident, 2, lexico.getLine());
                } else {
                    isValido = semantico.isChamadaValida(ident, 1, lexico.getLine());
                }

                break;
            case 202:

                if (isChamandoFuncao) {
                    numParmCFunction++;
                }

                isValido = true;
                break;
            case 203:
                if (isChamandoFuncao) {
                    isValido = semantico.isChamadaFuncaoValida(currentFuntionChamada,
                            numParmCFunction, lexico.getLine());
                    numParmCFunction = 0;
                } else {
                    isValido = true;
                }

                isChamandoFuncao = false;

                break;
            case 204:
                isValido = semantico.incrementaParams(currentFuntion);
                break;

            case 206:

                posIdent = lexico.getListTokens().size() - 2;
                currentFuntion = lexico.getListTokens().get(posIdent).getToken();

                isValido = semantico.isDeclaracaoValida(currentFuntion, lexico.getLine()); //2 = é função
                break;
        }

        return isValido;
    }

    public void listar(ArrayList a) {
        for (int j = 0; j < a.size(); j++) {
            System.out.print(a.get(j) + "-");
        }
    }
}

/*
Repita
        Se X=î então
		Retire o elemento do topo da pilha
		X recebe o topo da pilha
	Senão
	Se X é terminal então
		Se X=a então
		   Retire o elemento do topo da pilha
		   Sai do Repita
		Senão

        Erro
   Encerra o programa
       Fim Se
Senão (* X é não-terminal*)
Se M(X,a) <>  então (existe uma regra)
 Retire o elemento do topo da pilha
                        Coloque o conteúdo da regra na pilha
 X recebe o topo da pilha
Senão
Erro
Encerra o programa
Fim Se
Fim Se
Até X=$ (*pilha vazia, análise concluída*)
Fim
 */