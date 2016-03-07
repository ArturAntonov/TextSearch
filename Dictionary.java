import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 30.01.2016.
 */
public class Dictionary {

    /**
     * класс для словаря, который будет создаваться из файла со списком названий программ и их синонимов
     * В конструктор будет подаваться массив из слов строки. где первый элемент будет всегда оригинальным названием
     * Все последующие элементы - синонимы.
     * слова, поподающие в словарь будут фильтроваться, неизменным останется только оригинальное название.
     */

    private HashMap<String, String> dict; //тут будут храниться все связки keyword : originalName

    Dictionary ()
    {
        dict = new HashMap<String, String>();
    }


    /**
     * метод для наполнения словаря
     * @param names массив, составленный из всех названий одной программы
     */
    public void toFill(String[] names)
    {
        if (names.length > 0)
        {
            String origName = names[0];
            String[] keyWords = toKeyWords(names);
            keyWords = filter(keyWords);
            for (String keyWord : keyWords) {
                putToDict(keyWord, origName);
            }
        }
    }


    /**
     * служебный метод для фильтрования массива ключевых слов
     * @param keyWords  массив клчюевых слов
     * @return отфильтрованный массив ключевых слов
     */
    private String[] filter (String[] keyWords)
    {
        String result = "";

        String[] stopWords = {"ас","as","ac","aс","аc", "абс", "портал","online", "порталы", "портале", "ресурс", "для", "них", "документы","по","на", "в", "и", "с", "-"};
        ArrayList<String> stopW = new ArrayList<String>();
        Collections.addAll(stopW, stopWords);

        Pattern pNet =  Pattern.compile("[а-яА-Яa-zA-Z0-9 :\\./-_]"); //для фильтра сетевых путей
        Pattern p = Pattern.compile("[а-яА-Яa-zA-Z0-9 \\\\-]");

        for (String word : keyWords)
        {
            if (!stopW.contains(word.toLowerCase()) && !word.isEmpty())
            {
                String wordTemp = "";
                if (word.contains("/"))
                {
                    Matcher m = pNet.matcher(word);
                    while (m.find())
                    {
                        String buff = m.group();
                        wordTemp += buff;
                    }
                }
                else
                {
                    Matcher m = p.matcher(word);
                    while (m.find())
                    {
                        String buff = m.group();
                        wordTemp += buff;
                    }
                }
                result += wordTemp.trim() + ";";
            }
        }
        return result.split(";");
    }

    /**
     * Служебный метод для разбиения всех названий одной программы на отдельные "ключевые" слова
     * @param names массив всех названий
     * @return массив из слов keyWord
     */
    private String[] toKeyWords(String[] names)
    {
        String result = "";
        for (String name : names)
        {
            if (!name.isEmpty())
                result += name.trim() + " ";
        }
        return result.trim().split(" ");
    }

    /**
     * служебный метод для наполнения dict с учетом возможных повторений
     * @param keyWord ключевое слово
     * @param origName полное название программы
     */
    private void putToDict (String keyWord, String origName)
    {
        //System.out.println("another keyWord is " + keyWord);
        if (!keyWord.isEmpty() && keyWord.length() > 2) {
            keyWord = keyWord.toLowerCase(); //приравняли все к одному размеру шрифта
            if (!dict.containsKey(keyWord)) {
                dict.put(keyWord, origName);
            } else {
                String origNames = dict.get(keyWord);
                origNames += ";" + origName;
                dict.put(keyWord, origNames);
            }
        }
    }

    /**
     * метод по поиску совпадений со строкой
     * @param query строка, поиск по которой будет выполняться
     * @return "" - если нет совпадений, строка с разделителями, если есть какие-то совпадения
     */
    public String querySearch (String query) throws IOException
    {
        String result = "";
        ArrayList<String> forResults = new ArrayList<String>(); //массив для результатов
        HashMap<String, Integer> resCounts = new HashMap<String, Integer>(); // сюда найденные строки и их число

        //разбиваю строку на ключевые слова и фильтрую их
        String[] keyWords = query.trim().toLowerCase().split(" ");
        keyWords = filter(keyWords);

        //поиск в самом словаре. "опускание нитки с ключами в емкость словаря". достаю слова с налипшими находками
        for (String keyWord : keyWords)
        {
            //String tempResult = dict.get(keyWord) != null ? dict.get(keyWord) : "" ;
            String tempResult = "";
            for (Map.Entry<String, String> pair : dict.entrySet()) {
                String key = pair.getKey();
                String value = pair.getValue();
                if ( keyWord.equals(key) && !keyWord.isEmpty()) {
                    tempResult = value;
                }
            }
            forResults.add(tempResult);
        }
        //System.out.println(mapResults);
        //System.out.println(forResults);

        //добавляем результаты поиска в мапу с указанием количества нахождений
        for (String res : forResults)
        {
            String[] temp = res.split(";");
            for(String t : temp) {
                if (!resCounts.containsKey(t)) {
                    resCounts.put(t, 1);
                } else {
                    resCounts.put(t, resCounts.get(t) + 1);
                }
            }
        }
        //System.out.println(resCounts);

        //находим в мапе сроки с макисмальным вхождением и добавляем в спиок для вывода ( может лучше в самой мапе найти
        // максимумы и потом вывести ключи? без создания лишних структур
        int MAX = 0;
        ArrayList<String> forMax = new ArrayList<String>();
        for (Map.Entry<String, Integer> pair : resCounts.entrySet())
        {
            String key = pair.getKey();
            Integer value = pair.getValue();
            if (MAX == value)
            {
                forMax.add(key);
            }
            else if(MAX < value)
            {
                forMax = new ArrayList<String>();
                forMax.add(key);
                MAX = value;
            }
        }
        //System.out.println("максимумы >" + forMax);


        //ретурн максимально часто встречающихся элементов
        /*
        for (String s : forMax)
        {
            if (!s.isEmpty()) {
                result += s + ";";
            }
        }
        */
        result = dialog(query, forMax);
        return result;
    }

    /**
     * Служебный метод для определения того, что слово состоит из большинства больших букв
     * @param word слово на проверку
     * @return да или нет
     */
    private boolean isBig (String word)
    {
        int big = 0;
        int low = 0;
        Matcher m = Pattern.compile("[А-ЯA-Z]").matcher(word);
        while (m.find())
        {
            big += 1;
        }
        m = Pattern.compile("[a-zа-я]").matcher(word);
        while(m.find())
        {
            low++;
        }

        return (big - low >= 0) && word.length() >= 2;
    }

    /**
     * Нифига не работает. Слишком неточно
     * @param word слово из запроса
     * @param wordOrig оригинальное слово из словаря
     * @return да нет
     */
    private  boolean fuzzyEqual(String word, String wordOrig) {
        int K = 0; //порог ошибок
        int error = 0;
        char[] wordChar = word.toCharArray();
        char[] wordOrigChar = wordOrig.toCharArray();

        for ( int i = 0; i < wordChar.length; i ++) {
            for (int j = 0; j < wordOrigChar.length; j++) {
                if (wordChar[i] == wordOrigChar[j]) {
                    wordOrigChar[j] = 1;
                    break;
                }
            }
        }
        for (int i = 0; i < wordOrigChar.length; i++) {
            if (wordOrigChar[i] != 1) {
                error += 1;
            }
        }

        return word.length() - wordOrig.length() + error <= K;
    }

    @Override
    public String toString()
    {
        return "Dictionary{" +
                "dict=" + dict +
                '}';
    }

    /**
     * метод для проверки. не используется больше нигде
     * @return строка с переносами
     */
    public String getKeyWords()
    {
        String result = "";
        for (Map.Entry<String, String> pair : dict.entrySet())
        {
            String key = pair.getKey();
            String value = pair.getValue();
            result += key + "{}" + value + "\n";
        }
        return result;
    }


    /**
     * служебный метод для ведения переоворов с пользователем на тот случай, если совпавших вариантов несколько
     * @param query текущий запрос, который был передан в метод для поиска в словаре
     * @param list список с уже найденными совпадениями, наиболее подходящими
     * @return скомпанованную для вывода в CSV строку
     * @throws IOException
     */
    private String dialog (String query, ArrayList<String> list) throws IOException
    {
        String result = "";
        String origName = "";
        String name = "";
        if (list.size() > 0)
        {
            System.out.println("Исходный запрос " + query);
            for ( int i = 0; i < list.size(); i++)
            {
                System.out.println((i + 1) + " >" + "[" + list.get(i) + "]");
            }

            System.out.println("выбери правильный вариант. Напиши его номер и нажми энтер. Если все не то - no");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String buff = reader.readLine();
            if (!"no".equals(buff.toLowerCase())) {
                Integer number = Integer.parseInt(buff); //добавить проверку на число
                System.out.println("Будет добавлен вариант >" + list.get(number - 1));
                result += list.get(number - 1) + ";";
                origName = list.get(number - 1);
                String[] toDict = {origName, query};
                toFill(toDict);
            }
            else
            {
                System.out.println("Ничего не  добавлено");
            }
        }
        else if (list.size() != 0)
        {
            if(!list.get(0).isEmpty())
                result += list.get(0) + ";";
        }

        /*
        добавление в словарь появившегося синонима
         */

        return result;
    }
}
