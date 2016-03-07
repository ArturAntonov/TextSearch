import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 24.01.2016.
 */
public class Engine {

    public static void main(String[] args) throws Exception {
        //String fileUser = "C:\\!work\\java\\testData\\sberOptimization\\persons.csv";
        String fileSoft = "C:\\!work\\java\\testData\\sberOptimization\\soft.csv"; // файл с базой софта
        String softOut = "C:\\!work\\java\\testData\\sberOptimization\\softOut.csv"; // вывод результатов
        String opros = "C:\\!work\\java\\testData\\sberOptimization\\consolidation.csv"; // опросник пользовательский


        Parser parser = new Parser("users");
        ArrayList<Person> users = parser.usersParser(opros);
        //System.out.println(users);

        SoftList listAlpha = new SoftList("Софт домена ALPHA");
        SoftList listSigma = new SoftList("Софт домена Sigma");
        SoftList listTer = new SoftList("Софт Терр банков");
        SoftList listShare = new SoftList("Список фаловых шар");
        /*
        for (Person p : users)
        {
            listAlpha.toFill(p.getSoftAlpha());
        }
        */

        for (Person p : users)
        {
            listAlpha.toFill(p.getName(), p.getSoftAlpha());
             listSigma.toFill(p.getName(), p.getSoftSigma());
            listTer.toFill(p.getName(), p.getSoftTer());
            listShare.toFill(p.getName(), p.getSoftShare());
        }

        //System.out.println(listAlpha);

        //listAlpha.toFile("C:\\!work\\java\\testData\\sberOptimization\\listAlpha.csv");
        Dictionary dict = createDict(fileSoft);
        //for (Person p: users)
        ArrayList<String> table = new ArrayList<String>();
        table.add("ФИО;Запрос;Совпадение");
        table.add(";" + listAlpha.getName());
        searchTest(dict, listAlpha.getSoftMap(), table);
        table.add(";" + listShare.getName());
        searchTest(dict, listShare.getSoftMap(), table);
        table.add(";" + listSigma.getName());
        searchTest(dict, listSigma.getSoftMap(), table);
        table.add(";" + listTer.getName());
        searchTest(dict, listTer.getSoftMap(), table);


        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(softOut)));
        for (String line : table) {
            writer.println(line);
        }
        writer.close();
    }

    public static Dictionary createDict(String fileSoft) throws NullPointerException, IOException
    {
        Dictionary dict = new Dictionary();
        BufferedReader reader = new BufferedReader(new FileReader(fileSoft));
        String line = reader.readLine();
        while (line != null)
        {
            String[] names = line.split(";");
            dict.toFill(names);
            line = reader.readLine();
        }

        reader.close();
        return dict;
    }

    public static void searchTest(Dictionary dict,HashMap<String, String> queries, ArrayList<String> table) throws IOException
    {

        for (Map.Entry<String, String>  pair : queries.entrySet())
        {
            String softName = pair.getKey();
            String userName = pair.getValue();

            String result = dict.querySearch(softName);

            if (result.isEmpty())
            {
                table.add(userName + ";" + softName + ";" + result + ";");
                System.out.println(userName + ">" + softName + " >[" + result + "]");
            }
            else {
                table.add(";" + softName + ";" + result + ";");
            }
        }

    }
    public static void searchTest(Dictionary dict,ArrayList<String> queries, String softOut) throws  IOException
    {

        //System.out.println(dict.getKeyWords());
        String[] queries2 = {"Word, Excel, Outlook",
                "Microsoft Lync",
                "Адресная книга (Tелефонный справочник)",
                "СЭОДО",
                "УВХД",
                "Файловый ресурс - OKIP (nam02-avol2) (Y)",
                "Портал самообслуживания Service Manager",
                "АС Заявка на доступ",
                "Портал Iknow",
                "HP Service Manager",
                "МегаЦУКС",
                "Мой Сбербанк (портал Сбербанка)",
                "Xerox - сетевой сканер"};

        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(softOut),Charset.forName("UTF-8")));
        PrintWriter writer = new PrintWriter   // класс с методами записи строк
                (new OutputStreamWriter          // класс-преобразователь
                        (new FileOutputStream         // класс записи байтов в файл
                                (softOut), Charset.forName("UTF-8")));

        writer.println("Запрос;Совпадение");
        for (String query: queries)
        {
            String result = dict.querySearch(query);
            System.out.println(query + " >[" + result + "]");
            writer.println(query + ";" + result + ";");
        }
        writer.close();

    }

    public static void searchTest(Dictionary dict,HashMap<String, String> queries, String softOut) throws IOException
    {

        //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(softOut),Charset.forName("UTF-8")));
        PrintWriter writer = new PrintWriter   // класс с методами записи строк
                (new OutputStreamWriter          // класс-преобразователь
                        (new FileOutputStream         // класс записи байтов в файл
                                (softOut), Charset.forName("UTF-8")));

        writer.println("ФИО;Запрос;Совпадение");
        for (Map.Entry<String, String>  pair : queries.entrySet())
        {
            String softName = pair.getKey();
            String userName = pair.getValue();

            String result = dict.querySearch(softName);

            if (result.isEmpty())
            {
                writer.println(userName + ";" + softName + ";" + result + ";");
                System.out.println(userName + ">" + softName + " >[" + result + "]");
            }
            else {
                writer.println(";" + softName + ";" + result + ";");
            }
        }
        writer.close();

    }
}
