package misc;

import java.util.ArrayList;
import java.util.List;

public class Container {
    public Token token;
    public int read;
    public List<Token> expressao;

    public Container(Token token, int read) {
        this.token = token;
        this.read = read;
        this.expressao = new ArrayList<>();
    }

    public Boolean equals(String simbolo){
        return this.token.simbolo.equals(simbolo);
    }
}