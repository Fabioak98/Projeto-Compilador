import java.io.*;
import java.util.*;

public class Application {
    static List<Token> lista = new ArrayList<>();
    //static List<SimboloCSD> tabelaSimbolos = new ArrayList<SimboloCSD>();

    static Deque<SimboloCSD> tabelaSimbolos =  new ArrayDeque<>();

    /* Testing functions */

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

    /* Lexical analyser functions */

    private static Container trataDigito(int r, LineNumberReader lr) throws IOException {
        StringBuilder number = new StringBuilder(String.valueOf((char) r));
        r = lr.read();
        while (isDigit(r)) {
            number.append((char) r);
            r = lr.read();
        }
        return new Container(new Token(number.toString(), "snumero"), r);
    }

    private static Container trataPalavra(int r, LineNumberReader lr) throws IOException {
        StringBuilder word = new StringBuilder(String.valueOf((char) r));
        Token token = new Token("", "");
        r = lr.read();
        while (isCharacter(r) || isDigit(r) || (r == 95)) {
            word.append((char) r);
            r = lr.read();
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
        return new Container(token, r);
    }

    private static Container trataAtribuicao(LineNumberReader lr) throws IOException {
        int r = lr.read();
        Token token = new Token("","");
        if (r == 61) {
            token.lexema = ":=";
            token.simbolo = "satribuicao";
            r = lr.read();
        } else {
            token.lexema = ":";
            token.simbolo = "sdoispontos";
        }
        return new Container(token, r);
    }

    private static Container trataAritmetico(int r, LineNumberReader lr) throws IOException {
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
        r = lr.read();
        return new Container(token, r);
    }

    private static Container trataRelacional(int r, LineNumberReader lr) throws IOException {
        Token token = new Token("", "");
        if (r == 33) {
            int temp = r;
            r = lr.read();
            if (r == 61) {
                r = lr.read();
                token.lexema = "!=";
                token.simbolo = "sdif";
            }
            else {
                System.out.println("ERRO 1 " + (char) temp + " " + temp);
                trataErro(temp, "Carácter inválido " + '"' + (char) temp + '"');
            }
        } else if (r == 60) {
            r = lr.read();
            if (r == 61) {
                r = lr.read();
                token.lexema = "<=";
                token.simbolo = "smenorig";
            } else {
                token.lexema = "<";
                token.simbolo = "smenor";
            }
        } else if (r == 61) {
            r = lr.read();
            token.lexema = "=";
            token.simbolo = "sig";
        } else if (r == 62) {
            r = lr.read();
            if (r == 61) {
                r = lr.read();
                token.lexema = ">=";
                token.simbolo = "smaiorig";
            } else {
                token.lexema = ">";
                token.simbolo = "smaior";
            }
        }
        return new Container(token, r);
    }

    private static Container trataPontuacao(int r, LineNumberReader lr) throws IOException {
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
        r = lr.read();
        return new Container(token, r);
    }

    private static void trataErro(int r, String error){
        Token token = new Token(String.valueOf(r),error);
        lista.add(token);
    }

    public static void desempilha(){

    }

    private static Container pegaToken(int r, LineNumberReader lr) throws IOException {
        Container container = new Container(new Token("", ""), r);
        if (isDigit(r)) {
            container = trataDigito(r, lr);
        } else if (isCharacter(r)) {
            container = trataPalavra(r, lr);
        } else if (isColon(r)) {
            container = trataAtribuicao(lr);
        } else if (isArithmeticOp(r)) {
            container = trataAritmetico(r, lr);
        } else if (isRelationalOp(r)) {
            container = trataRelacional(r, lr);
        } else if (isPunctuation(r)) {
            container = trataPontuacao(r, lr);
        } else {
            System.out.println("Carácter inválido " + (char) r + " " + r);
            trataErro(r, "Carácter inválido " + '"' + (char) r + '"');
            container.read = lr.read();
        }
        return container;
    }

    public static Container analisadorLexical(int r, LineNumberReader lr) throws IOException {
        Container container = new Container(new Token("", ""), r);
        int curLine;
        while (r == 123 || r == 32 || r == 13 || r == 10 || r == 9) {
            if (r == 123) {
                curLine = lr.getLineNumber() + 1;
                while (r != 125 && r != -1) {
                    r = lr.read();
                }
                if (r == -1) {
                    trataErro(r, "Comentário não fechado, linha " + curLine);
                    System.out.println("Comentário não fechado, linha " + curLine);
                }
                r = lr.read();
            }
            while (r == 32 || r == 13 || r == 9 || r == 10) {
                r = lr.read();
            }
        }
        if (r != -1) {
            container = pegaToken(r, lr);
        }else{
            container.read = r;
        }
        return container;
    }

    /* Tabela de simbolos CSD */

    public static void insereTabela (String nome, String tipo, String escopo, String memoria) {
        tabelaSimbolos.push(new SimboloCSD(nome, tipo, escopo, memoria));
    }

    public static SimboloCSD consultaTabela (String nome) {
        for (Iterator<SimboloCSD> it = tabelaSimbolos.descendingIterator(); it.hasNext();) {
            SimboloCSD csd = it.next();
            if(csd.nome.equals(nome)){
                return csd;
            }
        }
        return null;
    }

    public static void colocaTipoTabela (String tipo) {
        for (Iterator<SimboloCSD> it = tabelaSimbolos.descendingIterator(); it.hasNext();) {
            SimboloCSD csd = it.next();
            if(csd.tipo.equals("variavel") || csd.tipo.equals("funcao")){
                csd.tipo = csd.tipo + "-" + tipo;
            }else break;
        }
    }

    private static boolean pesquisaDuplicVarTabela(String lexema) {
        for (Iterator<SimboloCSD> it = tabelaSimbolos.descendingIterator(); it.hasNext();) {
            SimboloCSD csd = it.next();
            if (csd.nome.equals(lexema)){
                return false;
            }
            if (csd.escopo.equals("L")){
                return true;
            }
        }
        return true;
    }

    private static boolean analisaTipoSem(List<Token> expressao, String tipo) {
        Token ultimo = expressao.get(expressao.size() - 1);

        if(tipo.equals("variavel-inteira") || tipo.equals("funcao-inteira")){
            tipo = "inteira";
        }
        else if(tipo.equals("variavel-boleana") || tipo.equals("funcao-boleana")){
            tipo = "boleana";
        }
        else {
            System.out.println("Identificador invalido");
            return false;
        }

        if(tipo.equals("inteira")){
            if( ultimo.equals("smais") || ultimo.equals("smenos") || ultimo.equals("sdiv") || ultimo.equals("smult")){
                return true;
            }
            else return false;
        }
        else{
            if( ultimo.equals("smais") || ultimo.equals("smenos") || ultimo.equals("sdiv") || ultimo.equals("smult")){
                return false;
            }
            else return true;
        }
    }

    /* Syntax analyzer functions */

    public static Container analisaEtVariaveis (int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);
        if (token.simbolo.equals("svar")) {
            container = analisadorLexical(r, lr);
            if (container.token.simbolo.equals("sidentificador")) {
                while (container.token.simbolo.equals("sidentificador")) {
                    container =  analisaVariaveis(container.read,container.token,lr);
                    if (container.token.simbolo.equals("sponto_virgula")) {
                        container = analisadorLexical(container.read, lr);
                    } else {
                        System.out.println("Error not spontvirg");
                    }
                }
            }
            else {
                System.out.println("Error not sidentificador");
            }
        }
        return container;
    }

    private static Container analisaVariaveis(int r, Token token, LineNumberReader lr) throws IOException{
        Container container = new Container(token,r);
        while(!container.token.simbolo.equals("sdoispontos")){
            if (container.equals("sidentificador")){
                if(pesquisaDuplicVarTabela(container.token.lexema)){
                    insereTabela(container.token.lexema,"variavel","","");
                    container = analisadorLexical(container.read,lr);
                    if(container.token.simbolo.equals("svirgula") || container.token.simbolo.equals("sdoispontos")){
                        if (container.token.simbolo.equals("svirgula")){
                            container = analisadorLexical(container.read,lr);
                            if(container.token.simbolo.equals("sdoispontos")){
                                System.out.println("Error not sdoispontos");
                            }
                        }
                    }
                }
                else{
                    System.out.println("Variavel ja declarada");
                }
            }
        }
        container = analisadorLexical(container.read,lr);
        container = analisaTipo(container.read, container.token, lr);
        return container;
    }

    public static Container analisaTipo(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        if (!container.token.simbolo.equals("sinteiro") && !container.token.simbolo.equals("sbooleano")){
            System.out.println("erro tipo invalido");
        }else
            colocaTipoTabela(token.lexema);

        container = analisadorLexical(container.read, lr);

        return container;
    }


    public static Container analisaComandos(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        if (container.token.simbolo.equals("sinicio")){
            container = analisadorLexical(container.read, lr);
            container = analisaComandoSimples(container.read,container.token,lr);
            while(!container.equals("sfim")){
                if(container.token.simbolo.equals("sponto_virgula")){
                    container = analisadorLexical(container.read,lr);
                    if(!container.equals("sfim")){
                        container = analisaComandoSimples(container.read,container.token,lr);
                    }
                }
                else{
                    System.out.println("Error not sponto_virgula");
                    container = analisadorLexical(container.read, lr);
                }
            }
            container = analisadorLexical(container.read,lr);
        }
        else{
            System.out.println("Error not sinicio");
        }

        return container;
    }

    public static Container analisaComandoSimples(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        if (container.token.simbolo.equals("sidentificador")){
            container = analisaAtribChProcedimento(container.read,container.token,lr);
        }else if (container.equals("sse")){
            container = analisaSe(container.read, container.token, lr);
        }else if (container.equals("senquanto")){
            container = analisaEnquanto(container.read, container.token, lr);
        }else if (container.equals("sleia")){
            container = analisaLeia(container.read, container.token, lr);
        }else if (container.equals("sescreva")){
            container = analisaEscreva(container.read, container.token, lr);
        }else container = analisaComandos(container.read, container.token, lr);

        return container;
    }

    public static Container analisaAtribChProcedimento(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);
        Token b = container.token;
        container = analisadorLexical(container.read, lr);
        if (container.token.simbolo.equals("satribuicao")){
            container = analisaAtribuicao(container.read, container.token, lr);
            container.expressao = infixToPostfix(container.expressao);

            var csd = consultaTabela(b.lexema);
            if(!analisaTipoSem(container.expressao, csd.tipo)){
                System.out.println("Tipo incompativeis");
            }
        }else container = analisaChamadaProcedimento(container.read, container.token, lr);

        return container;
    }


    public static Container analisaAtribuicao(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);
        container = analisaExpressao(container, lr);

        return container;
    }

    public static Container analisaChamadaProcedimento(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);
        return container;
    }

    public static Container analisaChamadaFuncao(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);


        return container;
    }


    public static Container analisaLeia(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);

        if (container.token.simbolo.equals("sabre_parenteses")){
            container = analisadorLexical(container.read, lr);
            if (container.token.simbolo.equals("sidentificador")){
                if(pesquisadeClVarTabela(container.token.lexema)){
                    container = analisadorLexical(container.read, lr);
                    if (container.token.simbolo.equals("sfecha_parenteses")){
                        container = analisadorLexical(container.read, lr);
                    }else System.out.println("erro not sfecha_parenteses");
                }
                else System.out.println("Variavel nao declarada");
            }else System.out.println("erro not sidentificador");
        }else System.out.println("erro not sabre_parenteses");

        return container;
    }

    private static boolean pesquisadeClVarTabela(String lexema) {
        var aux = tabelaSimbolos.stream().filter(t -> t.nome.equals(lexema)).findAny().orElse(null);
        if (aux == null){
            return false;
        }
        return true;
    }

    private static boolean pesquisaDeclVarProcTabela(String lexema){
        var aux = tabelaSimbolos.stream().filter(t -> t.nome.equals(lexema) && (t.tipo.equals("variavel-inteira") || t.tipo.equals("variavel-boleana"))).findAny().orElse(null);

        if (aux == null){
            return false;
        }

        return true;
    }

    public static Container analisaEscreva(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);
        if (container.token.simbolo.equals("sabre_parenteses")){
            container = analisadorLexical(container.read, lr);
            if (container.token.simbolo.equals("sidentificador")){
                if(pesquisaDeclVarProcTabela(container.token.lexema)) {
                    container = analisadorLexical(container.read, lr);
                    if (container.token.simbolo.equals("sfecha_parenteses")) {
                        container = analisadorLexical(container.read, lr);
                    } else System.out.println("erro not sfecha_parenteses");
                }else System.out.println("nao e uma variavel");
            }else System.out.println("erro not sidentificador");
        }else System.out.println("erro not sabre_parenteses");

        return container;
    }

    public static Container analisaEnquanto(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);

        container = analisaExpressao(container, lr);

        if(analisaTipoSem(container.expressao,"variavel-boleana")){
            if (container.token.simbolo.equals("sfaca")){
                container = analisadorLexical(container.read, lr);
                container = analisaComandoSimples(container.read,container.token,lr);
            }else System.out.println("erro not sfaca");
        }else System.out.println("tipos diferentes na linha" + lr);

        return container;
    }

    public static Container analisaSe(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token, r);

        container = analisadorLexical(container.read, lr);

        container = analisaExpressao(container, lr);
        container.expressao = infixToPostfix(container.expressao);


        if (container.token.simbolo.equals("sentao")){
            container = analisadorLexical(container.read, lr);
            container = analisaComandoSimples(container.read,container.token,lr);
            if (container.token.simbolo.equals("ssenao")){
                container = analisadorLexical(container.read, lr);
                container = analisaComandoSimples(container.read,container.token,lr);
            }
        }else System.out.println("erro not sentao");

        return  container;
    }

    public static Container analisaExpressao(Container container, LineNumberReader lr) throws IOException {
        container = analisaExpressaoSimples(container, lr);
        if (container.token.simbolo.equals("smaior") || container.token.simbolo.equals("smaiorig") ||
                container.token.simbolo.equals("smenor") || container.token.simbolo.equals("smenorig")
                || container.token.simbolo.equals("sig") || container.token.simbolo.equals("sdif")){
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
            container = analisaExpressaoSimples(container, lr);
        }

        return container;
    }

    public static Container analisaExpressaoSimples(Container container, LineNumberReader lr) throws IOException{
        if (container.token.simbolo.equals("smais") || container.token.simbolo.equals("smenos")) {
            String unario;
            if(container.equals("smais")){
                unario = "+u";
            }else
                unario ="-u";
            container.expressao.add(new Token(unario ,"sunario"));

            container = analisadorLexical(container.read, lr);
            if(container.equals("snumero") || container.equals("sidentificador"))
                container.expressao.add(container.token);
        }
        container = analisaTermo(container, lr);
        while (container.token.simbolo.equals("smais") || container.token.simbolo.equals("smenos") ||
                container.token.simbolo.equals("sou")){
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
            container = analisaTermo(container, lr);
        }

        return container;
    }

    public static Container analisaTermo(Container container, LineNumberReader lr) throws IOException{
        container = analisaFator(container.read, container.token, lr);
        while (container.token.simbolo.equals("smult") || container.token.simbolo.equals("sdiv") ||
                container.token.simbolo.equals("se")){
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
            container = analisaFator(container.read, container.token, lr);
        }

        return container;
    }

    public static Container analisaFator(int r, Token token, LineNumberReader lr) throws IOException{
        Container container = new Container(token, r);

        if (container.token.simbolo.equals("sidentificador")){
            container.expressao.add(container.token);
            container = analisaChamadaFuncao(container.read, container.token, lr);
        } else if (container.token.simbolo.equals("snumero")) {
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
        } else if (container.token.simbolo.equals("snao")) {
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
            container = analisaFator(container.read, container.token, lr);
        } else if (container.token.simbolo.equals("sabre_parenteses")) {
            container.expressao.add(container.token);

            container = analisadorLexical(container.read, lr);
            container = analisaExpressao(container, lr);
            if (container.token.simbolo.equals("sfecha_parenteses")){
                container.expressao.add(container.token);
                container = analisadorLexical(container.read, lr);
            }else System.out.println("erro not sfecha_parenteses");
        } else if (container.token.lexema.equals("verdadeiro") || container.token.lexema.equals("falso")) {
            container.expressao.add(container.token);
            container = analisadorLexical(container.read, lr);
        }else System.out.println("erro lexema invalido");

        return container;
    }

    public static Container analisaSubrotina(int r, Token token, LineNumberReader lr) throws IOException {
        Container container = new Container(token,r);
        while(container.token.simbolo.equals("sprocedimento") || container.token.simbolo.equals("sfuncao")){
            if(container.token.simbolo.equals("sprocedimento")){
                container = analisaDeclaracaoProcedimento(container.read,lr);
            }
            else{
                container = analisaDeclaracaoFuncao(container.read,lr);
            }
            if(container.token.simbolo.equals("sponto_virgula")){
                container = analisadorLexical(container.read,lr);
            }
            else{
                System.out.println("Error not sponto_virgula");
            }
        }
        return container;
    }

    private static Container analisaDeclaracaoFuncao(int r, LineNumberReader lr) throws IOException {
        Container container = analisadorLexical(r,lr);
        if(container.token.simbolo.equals("sidentificador")){
            container = analisadorLexical(container.read,lr);
            if (container.token.simbolo.equals("sdoispontos")){
                container = analisadorLexical(container.read,lr);
                if(container.token.simbolo.equals("sinteiro") || container.token.simbolo.equals("sbooleano")){
                    container = analisadorLexical(container.read,lr);
                    if (container.token.simbolo.equals("sponto_virgula")){
                        container = analisaBloco(container.read,lr);
                    }
                }
                else{
                    System.out.println("Error not stipo");
                }
            }
            else{
                System.out.println("Error not sdoispontos");
            }
        }
        else{
            System.out.println("Error not sidentificador");
        }
        return container;
    }

    private static Container analisaDeclaracaoProcedimento(int r, LineNumberReader lr) throws IOException{
        Container container = analisadorLexical(r,lr);
        String nivel = "L";
        if(container.token.simbolo.equals("sidentificador")) {
            if (!pesquisaDeclProcTabela(container.token.lexema)){
                insereTabela(container.token.lexema,"procedimento",nivel,"");
                container = analisadorLexical(container.read, lr);
                if (container.token.simbolo.equals("sponto_virgula")) {
                    container = analisaBloco(container.read, lr);
                } else {
                    System.out.println("Error not spontovirgula");
                }
            }
            else{
                System.out.println("Procedimento ja declarado");
            }
        }
        else{
            System.out.println("Error not sidentificador");
        }
        desempilha();
        return container;
    }

    public static Container analisaBloco (int r, LineNumberReader lr) throws IOException {
        Container container = analisadorLexical(r, lr);
        container = analisaEtVariaveis(container.read,container.token,lr);//analisa_et_variaveis
        container = analisaSubrotina(container.read,container.token,lr);//analisa_subrotinas
        container = analisaComandos(container.read,container.token,lr);//analisa_comandos
        return container;
    }

    public static Container analisadorSintatico(LineNumberReader lr) throws IOException {
        int label = 1;
        int r = lr.read();
        Container container = analisadorLexical(r, lr);
        if (container.token.simbolo.equals("sprograma")) {
            container = analisadorLexical(container.read, lr);
            if (container.token.simbolo.equals("sidentificador")) {
                insereTabela(container.token.lexema,"nomedeprograma","","");
                container = analisadorLexical(container.read, lr);
                if (container.token.simbolo.equals("sponto_virgula")) {
                    container = analisaBloco(container.read,lr);
                    if (container.token.simbolo.equals("sponto")) {
                        int curLine = lr.getLineNumber();
                        container = analisadorLexical(container.read, lr);
                        if (container.read == -1 && container.token.lexema.isEmpty()) {
                            System.out.println("SUCCESS");
                        }
                        else {
                            System.out.println("Error not EOF or comment, line: " + curLine);
                        }
                    }
                    else {
                        System.out.println("Error not sponto");
                    }
                }
                else {
                    System.out.println("Error not spontovirgula");
                }
            }
            else {
                System.out.println("Error not sidentificador");
            }
        }
        else {
            System.out.println("Error not sprograma");
        }
        return container;
    }

    public static void main(String[] args) throws IOException {
        var filep = new File("tests/sintatico/sint4.txt");
        LineNumberReader lr = new LineNumberReader(new FileReader(filep));
        var filew = new File("test6.txt");
        BufferedWriter buffer = new BufferedWriter(new FileWriter(filew));
        Container container = analisadorSintatico(lr);
        lr.close();

        for (int i = 0; i < lista.size(); i++) {
            String formattedOutput = String.format("%d\t%-15s%-15s", i, lista.get(i).lexema, lista.get(i).simbolo);
            buffer.write(formattedOutput + "\n");
        }
        buffer.close();
    }


    public static class SimboloCSD {
        String nome;
        String escopo;
        String tipo;
        String memoria;

        public SimboloCSD(String nome, String tipo, String escopo, String memoria) {
            this.nome = nome;
            this.escopo = escopo;
            this.tipo = tipo;
            this.memoria = memoria;
        }
    }

    static int Prec(Token simbolo)
    {
        if(simbolo.equals("sunario")){
            return 7;
        } else if (simbolo.equals("smult") || simbolo.equals("div")) {
            return 6;
        } else if (simbolo.equals("smais") || simbolo.equals("smenos")) {
            return  5;
        } else if (simbolo.equals("smaior") || simbolo.equals("smaiorig") || simbolo.equals("smenor") ||
                   simbolo.equals("smaiorig") || simbolo.equals("sdif") || simbolo.equals("sigual")) {
            return 4;
        } else if (simbolo.equals("snao")) {
            return 3;
        }else if (simbolo.equals("se")){
            return 2;
        }else if (simbolo.equals("sou")){
            return 1;
        }else{
            return -1;
        }
    }

    static List<Token> infixToPostfix(List<Token> exp)
    {
        List<Token> result = new ArrayList<>();

        // Initializing empty stack
        Deque<Token> stack
                = new ArrayDeque<Token>();

        for (Token token: exp) {
            Token t = token;

            // If the scanned character is an
            // operand, add it to output.
            if (t.simbolo.equals("sidentificador") || t.equals("snumero"))
                result.add(t);

            else if (t.lexema.equals("("))
                stack.push(t);

            else if (t.lexema.equals(")")) {
                while (!stack.isEmpty()
                        && stack.peek().equals("(")) {
                    result.add(stack.peek());
                    stack.pop();
                }

                stack.pop();
            }

            // An operator is encountered
            else
            {
                while (!stack.isEmpty()
                        && Prec(t) <= Prec(stack.peek())) {

                    result.add(stack.peek());
                    stack.pop();
                }
                stack.push(t);
            }
        }

        // Pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek().equals("("))
                throw new RuntimeException("Invalid Expression");
            result.add(stack.peek());
            stack.pop();
        }

        return exp;
    }

    public static class Container {
        Token token;
        int read;
        List<Token> expressao;

        public Container(Token token, int read) {
            this.token = token;
            this.read = read;
        }

        public Boolean equals(String simbolo){
            return this.token.simbolo.equals(simbolo);
        }
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