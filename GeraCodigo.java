import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GeraCodigo {
    private BufferedWriter bw;
    public GeraCodigo(File filename) throws IOException {
        String fname = filename.getName();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        File file = new File(fname + ".obj");

        if(file.exists())
            file.delete();
        file.createNewFile();

        this.bw = new BufferedWriter(new FileWriter(file,true));
    }
    public void gera(String rotulo, String comando, String parm1, String param2) throws IOException {
        String Stringformat = String.format("%-4s%-8s%-4s%-4s", rotulo, comando, parm1, param2);
        System.out.println(Stringformat);
        bw.write(Stringformat+ "\n");
    }

    public void closeBw() throws IOException {
        bw.close();
    }
}
