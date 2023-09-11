import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Application {
    static List<Token> lista = new ArrayList<>();

    private static boolean isDigit(int r) {
        return r >= 48 && r <= 57;
    }

    private static boolean isCharacter(int r) {
        return (r >= 65 && r <= 90) || (r >= 97 && r <= 122);
    }

    private static boolean isColon(int r) {
        return r == 58;
    }

    private static boolean isArithmeticOp(int r) {
        return (r == 42) || (r == 43) || (r == 45);
    }

    private static boolean isRelationalOp(int r) {
        return (r == 33) || (r == 60) || (r == 61) || (r == 62);
    }

    private static boolean isPunctuation(int r) {
        return (r == 40) || (r == 41) || (r == 44) || (r == 46) || (r == 59);
    }

    private static int trataDigito(int r, BufferedReader br) throws IOException {
        StringBuilder number = new StringBuilder(String.valueOf((char) r));
        r = br.read();
        while (isDigit(r)) {
            number.append((char) r);
            r = br.read();
        }
        Token token = new Token(number.toString(), "snumero");
        lista.add(token);
        return r;
    }

    private static int trataPalavra(int r, BufferedReader br) throws IOException {
        StringBuilder word = new StringBuilder(String.valueOf((char) r));
        Token token = new Token("", "");
        r = br.read();
        while (isCharacter(r) || isDigit(r) || (r == 95)) {
            word.append((char) r);
            r = br.read();
        }
        switch (word.toString()) {
            case "programa" -> {
                token.lexema = "programa";
                token.simbolo = "sprograma";
            }
            case "se" -> {
                token.lexema = "se";
                token.simbolo = "sse";
            }
            case "entao" -> {
                token.lexema = "entao";
                token.simbolo = "sentao";
            }
            case "senao" -> {
                token.lexema = "senao";
                token.simbolo = "ssenao";
            }
            case "enquanto" -> {
                token.lexema = "enquanto";
                token.simbolo = "senquanto";
            }
            case "faca" -> {
                token.lexema = "faca";
                token.simbolo = "sfaca";
            }
            case "fim" -> {
                token.lexema = "fim";
                token.simbolo = "sfim";
            }
            case "escreva" -> {
                token.lexema = "escreva";
                token.simbolo = "sescreva";
            }
            case "leia" -> {
                token.lexema = "leia";
                token.simbolo = "sleia";
            }
            case "var" -> {
                token.lexema = "var";
                token.simbolo = "svar";
            }
            case "inteiro" -> {
                token.lexema = "inteiro";
                token.simbolo = "sinteiro";
            }
            case "booleano" -> {
                token.lexema = "booleano";
                token.simbolo = "sbooleano";
            }
            case "verdadeiro" -> {
                token.lexema = "verdadeiro";
                token.simbolo = "sverdadeiro";
            }
            case "falso" -> {
                token.lexema = "falso";
                token.simbolo = "sfalso";
            }
            case "procedimento" -> {
                token.lexema = "procedimento";
                token.simbolo = "sprocedimento";
            }
            case "funcao" -> {
                token.lexema = "funcao";
                token.simbolo = "sfuncao";
            }
            case "div" -> {
                token.lexema = "div";
                token.simbolo = "sdiv";
            }
            case "e" -> {
                token.lexema = "e";
                token.simbolo = "se";
            }
            case "ou" -> {
                token.lexema = "ou";
                token.simbolo = "sou";
            }
            case "nao" -> {
                token.lexema = "nao";
                token.simbolo = "snao";
            }
            case "inicio" -> {
                token.lexema = "inicio";
                token.simbolo = "sinicio";
            }
            default -> {
                token.lexema = word.toString();
                token.simbolo = "sidentificador";
            }
        }
        lista.add(token);
        return r;
    }

    private static int trataAtribuicao(BufferedReader br) throws IOException {
        int r = br.read();
        if (r == 61) {
            Token token = new Token(":=", "satribuicao");
            lista.add(token);
            r = br.read();
        } else {
            Token token = new Token(":", "sdoispontos");
            lista.add(token);
        }
        return r;
    }

    private static int trataAritmetico(int r, BufferedReader br) throws IOException {
        Token token = new Token("", "");
        switch (r) {
            case 42 -> {
                token.lexema = "*";
                token.simbolo = "smult";
            }
            case 43 -> {
                token.lexema = "+";
                token.simbolo = "smais";
            }
            case 45 -> {
                token.lexema = "-";
                token.simbolo = "smenos";
            }
        }
        lista.add(token);
        r = br.read();
        return r;
    }

    private static int trataRelacional(int r, BufferedReader br) throws IOException {
        Token token = new Token("", "");
        if (r == 33) {
            int temp = r;
            r = br.read();
            if (r == 61) {
                r = br.read();
                token.lexema = "!=";
                token.simbolo = "sdif";
            }
            else {
                System.out.println("ERRO 1 " + (char) r + " " + r);
                token = trataErro(temp);
            }
        } else if (r == 60) {
            r = br.read();
            if (r == 61) {
                r = br.read();
                token.lexema = "<=";
                token.simbolo = "smenorig";
            } else {
                token.lexema = "<";
                token.simbolo = "smenor";
            }
        } else if (r == 61) {
            r = br.read();
            token.lexema = "=";
            token.simbolo = "sig";
        } else if (r == 62) {
            r = br.read();
            if (r == 61) {
                r = br.read();
                token.lexema = ">=";
                token.simbolo = "smaiorig";
            } else {
                token.lexema = ">";
                token.simbolo = "smaior";
            }
        }
        lista.add(token);
        return r;
    }

    private static int trataPontuacao(int r, BufferedReader br) throws IOException {
        Token token = new Token("", "");
        switch (r) {
            case 40 -> {
                token.lexema = "(";
                token.simbolo = "sabre_parenteses";
            }
            case 41 -> {
                token.lexema = ")";
                token.simbolo = "sfecha_parenteses";
            }
            case 44 -> {
                token.lexema = ",";
                token.simbolo = "svirgula";
            }
            case 46 -> {
                token.lexema = ".";
                token.simbolo = "sponto";
            }
            case 59 -> {
                token.lexema = ";";
                token.simbolo = "sponto_virgula";
            }
        }
        lista.add(token);
        r = br.read();
        return r;
    }

    private static Token trataErro(int r){
        return new Token(String.valueOf(r),"serro");
    }

    private static int pegaToken(int r, BufferedReader br) throws IOException {
        if (isDigit(r)) {
            r = trataDigito(r, br);
        } else if (isCharacter(r)) {
            r = trataPalavra(r, br);
        } else if (isColon(r)) {
            r = trataAtribuicao(br);
        } else if (isArithmeticOp(r)) {
            r = trataAritmetico(r, br);
        } else if (isRelationalOp(r)) {
            r = trataRelacional(r, br);
        } else if (isPunctuation(r)) {
            r = trataPontuacao(r, br);
        } else {
            System.out.println("ERRO 2 " + (char) r + " " + r);
            Token token = trataErro(r);
            lista.add(token);
        }
        return r;
    }

    public static void main(String[] args) throws IOException {
        var filep = new File("programa.txt");
        BufferedReader br = new BufferedReader(new FileReader(filep));
        var filew = new File("test.txt");
        BufferedWriter buffer = new BufferedWriter(new FileWriter(filew));
        int r = br.read();
        while (r != -1) {
            while (r == 123 || r == 32 || r == 13 || r == 10) {
                if (r == 123) {
                    while (r != 125 && r != -1) {
                        r = br.read();
                    }
                    r = br.read();
                }
                while (r == 32 || r == 13 || r == 10) {
                    r = br.read();
                }
            }
            if (r != -1) {
                r = pegaToken(r, br);
            }
        }
        br.close();
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + "\t" + lista.get(i).lexema + "\t" + lista.get(i).simbolo);
            buffer.write(i + "\t" + lista.get(i).lexema + "\t" + lista.get(i).simbolo + "\n");
        }
        buffer.close();
    }

    public static class Token {
        String lexema;
        String simbolo;

        public Token(String lexema, String simbolo) {
            this.lexema = lexema;
            this.simbolo = simbolo;
        }
    }
}