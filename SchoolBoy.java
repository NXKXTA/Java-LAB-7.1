import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

// Класс, представляющий школьника
public class SchoolBoy
{
    private String surname; // Фамилия школьника
    private String name;    // Имя школьника
    private byte num_class; // Номер класса
    private String subject; // Предмет, который изучает школьник
    private byte grade;     // Оценка по предмету

    // Конструктор без параметров, инициализирующий поля значениями по умолчанию
    public SchoolBoy()
    {
        this.surname = "";
        this.name = "";
        this.num_class = 0;
        this.subject = "";
        this.grade = 0;
    }

    // Конструктор с параметрами, позволяющий создать объект школьника с заданными значениями
    public SchoolBoy(String surname, String name, byte num_class, String subject, byte grade)
    {
        setSurname(surname); // Устанавливаем фамилию
        setName(name);       // Устанавливаем имя
        setNumClass(num_class); // Устанавливаем номер класса
        setSubject(subject);    // Устанавливаем предмет
        setGrade(grade);        // Устанавливаем оценку
    }

    // Метод для проверки, совпадает ли предмет школьника с заданным
    public boolean equal(String subject)
    {
        return getSubject().equals(subject); // Сравниваем текущий предмет с заданным
    }

    // Метод для проверки, совпадает ли оценка школьника с заданной
    public boolean equal(byte grade)
    {
        return getGrade() == grade; // Сравниваем текущую оценку с заданной
    }

    // Метод для проверки, совпадают ли фамилия и имя школьника с заданными
    public boolean equal(String surname, String name)
    {
        return (getSurname().equals(surname) && getName().equals(name)); // Сравниваем фамилию и имя
    }

    // Метод для вывода информации о школьнике в консоль
    public void print()
    {
        System.out.printf("%s %s, ", getSurname(), getName()); // Печатаем фамилию и имя
    }

    // Метод для чтения данных о школьниках из файла
    public static void readFile(String file_name, TreeMap<Byte, ArrayList<SchoolBoy>> journal)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(file_name)))
        { // Открываем файл для чтения
            String str; // Строка для хранения текущей строки файла
            while ((str = reader.readLine()) != null)
            { // Читаем файл построчно
                String[] word = str.split("\\s+"); // Разбиваем строку на части по пробелу

                String surname = word[0];        // Фамилия школьника
                String name = word[1];           // Имя школьника
                byte num_class = Byte.parseByte(word[2]); // Номер класса
                String subject = word[3];        // Предмет
                byte grade = Byte.parseByte(word[4]);     // Оценка

                // Создаем объект школьника с прочитанными данными
                SchoolBoy school_boy = new SchoolBoy(surname, name, num_class, subject, grade);

                // Добавляем школьника в журнал. Если ключа нет, создается новый список
                journal.computeIfAbsent(num_class, key -> new ArrayList<>()).add(school_boy);
            }
        } catch (IOException e)
        { // Обработка ошибок ввода-вывода
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    // Метод для записи списка школьников определенного класса в файл
    public static void writeFile(ArrayList<SchoolBoy> array_school, byte num_class) {
        try (FileWriter writer = new FileWriter("class" + num_class + ".txt")) { // Открываем файл для записи
            for (SchoolBoy school_boy : array_school) {
                // Записываем фамилию и имя школьника в файл
                writer.write(school_boy.getSurname() + " " + school_boy.getName() + "\n");
            }
        } catch (IOException e) { // Обработка ошибок записи в файл
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    // Метод для записи списка школьников и их оценок по предметам в файл
    public static void writeFile2(TreeSet<String> subjects, ArrayList<SchoolBoy> array_school, byte num_class) {
        try (FileWriter writer = new FileWriter("Your class" + num_class + ".txt")) { // Открываем файл для записи
            for (String subject : subjects) { // Проходим по всем предметам
                writer.write(subject + ":\n"); // Пишем название предмета
                for (SchoolBoy school_boy : array_school) { // Проходим по всем школьникам
                    if (school_boy.equal(subject)) { // Проверяем, относится ли школьник к текущему предмету
                        writer.write(school_boy.getSurname() + " " + school_boy.getName() + " " + school_boy.getGrade() + "\n");
                    }
                }
                writer.write("\n"); // Добавляем пустую строку после каждого предмета
            }
        } catch (IOException e) { // Обработка ошибок записи в файл
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    // Сеттеры для установки значений полей
    public void setSurname(String surname) { this.surname = surname; }
    public void setName(String name) { this.name = name; }
    public void setNumClass(byte num_class) { this.num_class = num_class; }
    public void setSubject(String subject) { this.subject = subject; }
    public void setGrade(byte grade) { this.grade = grade; }

    // Геттеры для получения значений полей
    public String getSurname() { return surname; }
    public String getName() { return name; }
    public byte getNumClass() { return num_class; }
    public String getSubject() { return subject; }
    public int getGrade() { return grade; }
}
