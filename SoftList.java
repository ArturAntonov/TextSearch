import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by user on 02.02.2016.
 */
public class SoftList {

    /**
     * состояния (свойства)
     * 2. имя листа
     * 3. список софта (Set)
     */

    /**
     * поведение (метода)
     * 1. заполнение листа
     * 2. вывод в консоль списка
     * 3. вывод в файл
     * 4. показать размер
     */

    private String name;
    private HashSet<String> softList;
    private HashMap<String, String> softMap;

    public SoftList (String name) {
        softList = new HashSet<String>();
        softMap = new HashMap<String, String>();
        this.name = name;
    }

    public void toFill (ArrayList<String> list) {
        for (String soft : list) {
            softList.add(soft);
        }
    }

    public void toFill (String name, ArrayList<String> softs) {
        for (String soft : softs) {
            if (!softMap.containsKey(soft)) {
                softMap.put(soft, name);
            }
            else
            {
                String newValue = softMap.get(soft) + ", " + name;
                softMap.put(soft,newValue);
            }
        }
    }

    public HashSet<String> getSoftList() {
        return softList;
    }

    public HashMap<String, String> getSoftMap() {
        return softMap;
    }

    public ArrayList<String> toArrayList ()
    {
        ArrayList<String> list = new ArrayList<String>();
        for (String soft : softList)
        {
            list.add(soft);
        }
        return list;
    }

    @Override
    public String toString() {
        String result = "Список " + this.name + "\n";
        for (String soft: softList) {
            result += soft + "\n";
        }

        return result;
    }

    public void toFile (String fileName) throws FileNotFoundException
    {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("UTF-8")));
        writer.println(this.name + ";");
        for (String soft : softList)
        {
            writer.println(soft + ";");
        }
        writer.close();
    }

    public String getName() {
        return name;
    }
}
