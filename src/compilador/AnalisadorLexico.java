package compilador;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 *
 * @author gustavofabro
 */
public class AnalisadorLexico {

    private final int IDENTIFICADOR = 40;
    private final int NUMBER_FLOAT = 38;
    private final int NUMBER_INT = 37;
    private final int LITERAL = 39;
    private final int COMMENT = -1;
    private final int STRING = 36;
    private final int OTHER = -2;
    private final int CHAR = 41;

    private int type;
    private int line;
    private int i;

    private boolean erro;

    private char[] c;

    private String tokenAtual = "";
    private final String regexInden = "[\\w0-9_]";
    private final String regexSim = "[><=+-}|{;:\\]\\[.,*()&$!\\/]";

    private final ArrayList<String> terminais = new ArrayList<String>(Arrays.asList(
            new String[]{"while", "void", "return", "of", "ler", "int", "inicio_programa",
                    "inicio_corpo", "if", "î", "for", "float", "fim_corpo", "exit_success",
                    "escreve", "else", "do", "declaravariaveis", "constante", "char",
                    "chamafuncao", "array", "0", ">=", ">", "==", "=", "<>", "<=", "<", "++",
                    "+", "}", "||", "{", "_string", "_numint", "_numfloat", "_literal", "_identificador",
                    "_char", "]", "[", ";", ":=", ":", "/", "..", ",", "*", ")", "(", "&&", "$", "!", "--", "-"}));

    private ArrayList<Token> tokens = new ArrayList<>();
    private final IDE ide;
    private final AnalisadorSintatico sintatico = new AnalisadorSintatico(this);

    public AnalisadorLexico(IDE ide) {
        this.ide = ide;
    }

    //esta só é chamada na classe IDE quando é solicitado a compilação
    public void analisar(String codigo) {
        c = codigo.toCharArray();
        i = 0;
        line = 1;
        tokens.clear();
        tokenAtual = "";
        sintatico.inicializaSintatico(getNextToken());
        ide.setTokensTable(tokens);
        ide.showPilhaDebug(sintatico.getPilha());
    }

    public Token getNextToken() {
        tokenAtual = "";

        while (i < c.length) {
            type = OTHER;

            if (c[i] == ' ' || c[i] == '\n' || c[i] == 9) {

                if (c[i] == '\n') {
                    line++;
                }

                i++;
            } else {
                if (c[i] == '<' && (i + 1 < c.length && c[i + 1] == ':')) {
                    i = i + 2;
                    if (i < c.length) {
                        do {
                            if (c[i] == ':') {
                                if (i + 1 < c.length && c[++i] == '>') {
                                    i++;
                                    type = COMMENT;
                                    break;
                                }
                            }

                            i++;
                        } while (i < c.length);
                    }
                } else if (c[i] == '>' && (i + 1 < c.length && c[i + 1] == '>')) {
                    type = COMMENT;
                    do {
                        i++;
                    } while (i < c.length && c[i] != '\n');

                } else if (Character.isLetter(c[i])) {
                    do {
                        tokenAtual += c[i];
                        i++;
                    } while (i < c.length && (Character.isLetter(c[i]) || c[i] == '_'));

                } else if (c[i] == '§') {
                    tokenAtual += c[i];

                    if (Character.isLetter(c[++i])) {
                        tokenAtual += c[i++];

                        while (i < c.length && (c[i] + "").matches(regexInden)) {
                            tokenAtual += c[i];
                            i++;
                        }

                        type = IDENTIFICADOR;
                    }

                } else if (Character.isDigit(c[i])) {

                    do {
                        tokenAtual += c[i];
                        i++;
                    } while (i < c.length && Character.isDigit(c[i]));

                    if (i < c.length && c[i] == '.') {
                        tokenAtual += c[i];

                        if (++i < c.length && Character.isDigit(c[i])) {
                            do {
                                tokenAtual += c[i];
                                i++;
                            } while (i < c.length && Character.isDigit(c[i]));

                            type = NUMBER_FLOAT;
                        }
                    } else if (!tokenAtual.equals("0")) {
                        type = NUMBER_INT;
                    }

                } else if (c[i] == '"') {
                    do {
                        tokenAtual += c[i];
                        i++;
                    } while (i < c.length && c[i] != '"');

                    if (i < c.length) {
                        tokenAtual += c[i];
                        i++;
                        type = STRING;
                    }
                } else if (c[i] == '\'') {
                    tokenAtual += c[i];

                    if (++i < c.length) {
                        tokenAtual += c[i];

                        if (++i < c.length && c[i] == '\'') {
                            tokenAtual += c[i];
                            type = CHAR;
                            i++;
                        }
                    }

                } else if (c[i] == '\\') {
                    do {
                        tokenAtual += c[i];
                        i++;

                        if (i < c.length && c[i] == '\\') {
                            type = LITERAL;
                            tokenAtual += c[i];
                            i++;
                            break;
                        }
                    } while (i < c.length);
                } else if (("" + c[i]).matches(regexSim)) {

                    do {
                        tokenAtual += c[i];
                        i++;
                    } while (i < c.length
                            && (("" + c[i]).matches(regexSim)
                            && isTerminalValido(tokenAtual
                            + c[i])));
                }

                if (type != COMMENT) {

                    if (isTerminalValido(tokenAtual) || type != OTHER) {
                        classifica(tokenAtual, line, type);
                        return tokens.get(tokens.size() - 1); //retorna pro sintático
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Token desconhecido: " + tokenAtual + "\nLinha: " + line,
                                "Erro Léxico",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                }
            }
        }

        if (i == c.length) { //Fim da fita
            tokens.add(new Token(54, "EOF", line));
            return tokens.get(tokens.size() - 1); //retorna pro sintático
        }

        return null;
    }

    public void classifica(String token, int linha, int type) {
        int index = type != OTHER ? type : terminais.indexOf(token) + 1;

        tokens.add(new Token(index, token, linha));
    }

    public boolean isTerminalValido(String token) {
        return terminais.contains(token);
    }

    public ArrayList<Token> getListTokens() {
        return this.tokens;
    }

    public ArrayList<String> getTerminais(){
        return this.terminais;
    }

    public int getLine(){
        return this.line;
    }
}