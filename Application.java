import java.io.*;

public class Application {
    public static void main(String[] args) throws IOException {
        var file  = new File("programa.txt");
        BufferedReader br=new BufferedReader(new FileReader(file));
        int r = br.read();
        while(r!=-1){
            while ("{".equals((char)r) || " ".equals((char)r) || r !=1 ) {
                if("}".equals((char)r)){
                    while ()
                }
            }
        }
    }
}