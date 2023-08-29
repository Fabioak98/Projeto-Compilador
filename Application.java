import java.io.*;
import java.util.List;

public class Application {
    public class Token{
        String lexema;
        String simbolo;
    }

    List<Token> lista;
    public static void main(String[] args) throws IOException {
        var filep  = new File("programa.txt");
        BufferedReader br=new BufferedReader(new FileReader(filep));
        var filew = new File("test.txt");
        BufferedWriter buffer = new BufferedWriter(new FileWriter(filew));
        int r = br.read();
        System.out.println((char)r);
        while(r!=-1){
            while ((r == 123 || r == 32) && r !=-1 ) {
                if(r == 123){
                    while (r!=125 && r != -1){
                        r= br.read();
                    }
                    r = br.read();
                }
                while(r == 32 && r!= -1){
                    r= br.read();
                }
            }
            if(r != -1){
                buffer.write(r);
                r= br.read();
                //Inseri lista
            }
        }
        br.close();
        buffer.close();
    }
}