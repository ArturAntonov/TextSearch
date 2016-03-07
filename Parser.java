import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 31.01.2016.
 */
public class Parser {

    private String name;

    public Parser(String name)
    {
        this.name = name;
    }

    public  ArrayList<Person> usersParser (String fileName) throws IOException
    {
        ArrayList<Person> persons = new ArrayList<Person>();
        ArrayList<String> userLine = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while (line != null) {
            do { //считываю пока не встречу опять полную строку
                if (line.contains(";") && isCorrect(line)) {
                    //System.out.println("reading " + line);
                    userLine.add(line);
                }
                line = reader.readLine();
            } while (line != null && line.startsWith(";"));
            persons.add(new Person(userLine));
            userLine = new ArrayList<String>();
            //System.out.println(userLine);
            //userLine = new ArrayList<String>();
        }
        return persons;
    }

    /**
     * служебный метод для проверки строки на корректность. то есть она содержит символы кроме разделителей
     * это для того, чтобы мусор не собирался в конструктор юзера
     * @param line считываемая строка
     * @return да или нет
     */
    private boolean isCorrect(String line)
    {
        Pattern p = Pattern.compile("[a-zA-Z0-9а-яА-Я ]");
        Matcher m = p.matcher(line);

        return m.find();
    }
}
