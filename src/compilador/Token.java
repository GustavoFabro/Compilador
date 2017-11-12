package compilador;

/**
 *
 * @author gustavofabro
 */
public class Token {
    private int codigo;
    private String token;
    private int linha;

    public Token(int codigo, String token, int linha) {
        this.codigo = codigo;
        this.token = token;
        this.linha = linha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
}
