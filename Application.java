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
            System.out.println("caractere " + number);
        }
        Token token = new Token("num", number.toString());
        lista.add(token);
        return r;
    }

    private static int pegaToken(int r, BufferedReader br) throws IOException {
        if (isDigit(r)) {
            System.out.println("digito " + (char) r);
            r = trataDigito(r, br);
        } else if (isCharacter(r)) {
            System.out.println("caractere " + (char) r);
            r = br.read();
            //trata identificador e palavra reservada
        } else if (isColon(r)) {
            System.out.println("dois pontos " + (char) r);
            //trata atribuição
        } else if (isArithmeticOp(r)) {
            System.out.println("operador aritmético " + (char) r);
            //trata operador aritmético
        } else if (isRelationalOp(r)) {
            System.out.println("operador relacional " + (char) r);
            //trata operador relacional
        } else if (isPunctuation(r)) {
            System.out.println("pontuação " + (char) r);
            //trata pontuação
        } else {
            System.out.println("erro " + (char) r);
            // erro
        }
        return r;
    }

    public static void main(String[] args) throws IOException {
        var filep = new File("programa.txt");
        BufferedReader br = new BufferedReader(new FileReader(filep));
        var filew = new File("test.txt");
        BufferedWriter buffer = new BufferedWriter(new FileWriter(filew));
        int r = br.read();
        System.out.println((char) r);
        while (r != -1) {
            while ((r == 123 || r == 32) && r != -1) {
                if (r == 123) {
                    while (r != 125 && r != -1) {
                        r = br.read();
                    }
                    r = br.read();
                }
                while (r == 32 && r != -1) {
                    r = br.read();
                }
            }
            if (r != -1) {
                buffer.write(r);
                r = pegaToken(r, br);
                //r = br.read();
                //Inseri lista
            }
        }
        br.close();
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