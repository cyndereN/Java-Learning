import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IOUtil {
    public static Map<String, PhoneBook> phoneDatas = new HashMap<String, PhoneBook>();
    public static final String DATA_FILE = "data.txt";

    static {
        load();
    }

    public static boolean save(String name,String phoneNum,String email){

        phoneDatas.put(name, new PhoneBook(name,phoneNum,email));
        try {
            FileOutputStream out = new FileOutputStream(DATA_FILE);
            out.write((name+" "+phoneNum+" "+email).getBytes());
            out.flush();
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean load() {
        try {
            FileInputStream in = new FileInputStream(DATA_FILE);
            byte[] bs = new byte[1024];
            int len = 0;
            StringBuilder dataStr = new StringBuilder();

            while ((len = in.read(bs)) != -1) {
                dataStr.append(new String(bs, 0, len));
            }

            String[] dataStrs = dataStr.toString().trim().split("\\s+");

            if (dataStrs.length > 1) {
                for (int i = 0; i < dataStrs.length; i += 3) {
                    phoneDatas.put(dataStrs[i],new PhoneBook(dataStrs[i],dataStrs[i+1],dataStrs[i+2]));
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            try {
                new File(DATA_FILE).createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}