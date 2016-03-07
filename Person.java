import java.util.ArrayList;

/**
 * Created by User on 30.01.2016.
 */
public class Person {

    private String name; //ФИО
    private String dept; //отдел
    private String loginAlpha;
    private String nameArm;
    private ArrayList<String> softAlpha;
    private ArrayList<String> softShare;
    private ArrayList<String> softTer;
    private ArrayList<String> softSigma;

    public Person(ArrayList<String> userLine) {
        this.softAlpha = new ArrayList<String>();
        this.softTer = new ArrayList<String>();
        this.softSigma = new ArrayList<String>();
        softShare = new ArrayList<String>();
        userCreate(userLine);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", loginAlpha='" + loginAlpha + '\'' +
                ", nameArm='" + nameArm + '\'' +
                ", softAlpha=" + softAlpha +
                ", softTer=" + softTer +
                ", softSigma=" + softSigma +
                '}';
    }

    /**
     * служебный метод для разбора списка строк, который приходит в конструктор
     * @param userLine список строк, который был пропарсен из экселя. в этом списке все данные на одного юзера
     */
    private void userCreate(ArrayList<String> userLine)
    {
        for (String line : userLine)
        {
            if (!line.startsWith(";"))
            {
                //System.out.println(line);
                String[] words = line.split(";", -1);
                //System.out.println(words.length);
                //for (String word: words)
                    //System.out.println("{" + word+ "}");
                this.name = words[0];
                this.dept = words[2];
                this.loginAlpha = words[4];
                this.nameArm = words[5];
                this.softAlpha.add(words[9]);
                this.softShare.add(words[10]);
                this.softSigma.add(words[11]);
                this.softTer.add(words[12]);
            }
            else
            {
                //System.out.println(line);
                String[] words = line.split(";", -1);
                //System.out.println(words.length);
                //for (String word: words)
                  //
                  // System.out.println("{" + word+ "}");
                // добавил проверку в парсер. убрать проверку тут и проверить
                if (!words[9].isEmpty())
                    this.softAlpha.add(words[9]);
                if (!words[10].isEmpty())
                    this.softShare.add(words[10]);
                if (!words[11].isEmpty())
                    this.softSigma.add(words[11]);
                if (!words[12].isEmpty())
                    this.softTer.add(words[12]);
            }
        }
    }

    public ArrayList<String> getSoftAlpha() {
        return softAlpha;
    }

    public ArrayList<String> getSoftTer() {
        return softTer;
    }

    public ArrayList<String> getSoftSigma() {
        return softSigma;
    }

    public ArrayList<String> getSoftShare() {
        return softShare;
    }

    public String getName() {
        return name;
    }

    public String getDept() {
        return dept;
    }
}
