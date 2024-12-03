import java.util.*;
import java.io.*;

// Класс для работы с журналом школьников
public class SchoolJournal {
    private TreeMap<Byte, ArrayList<SchoolBoy>> journal;

    // Конструктор, который инициализирует журнал из файла
    public SchoolJournal(String fileName) {
        journal = new TreeMap<>();
        SchoolBoy.readFile(fileName, journal);
    }

    // Метод для вывода всех учеников
    public void printPupils() {
        for (Map.Entry<Byte, ArrayList<SchoolBoy>> entry : journal.entrySet()) {
            byte numClass = entry.getKey();
            ArrayList<SchoolBoy> pupils = entry.getValue();
            System.out.println("\n" + numClass + " класс:");
            for (SchoolBoy pupil : pupils)
                pupil.print();
            SchoolBoy.writeFile(pupils, numClass);
        }
    }

    // Метод для вывода учеников с указанной оценкой
    public void printGrade(byte grade) {
        for (Map.Entry<Byte, ArrayList<SchoolBoy>> entry : journal.entrySet()) {
            ArrayList<SchoolBoy> pupils = entry.getValue();
            System.out.println("\n" + entry.getKey() + " класс:");
            boolean found = false;
            for (SchoolBoy pupil : pupils) {
                if (pupil.equal(grade)) {
                    pupil.print();
                    found = true;
                }
            }
            if (!found) System.out.println("Нет учеников с такой оценкой.");
        }
    }

    // Метод для сортировки классов по средней успеваемости
    public void printPerformance() {
        ArrayList<Map.Entry<Byte, Double>> performance = new ArrayList<>();
        for (Map.Entry<Byte, ArrayList<SchoolBoy>> entry : journal.entrySet()) {
            ArrayList<SchoolBoy> pupils = entry.getValue();
            double avg = pupils.stream().mapToInt(SchoolBoy::getGrade).average().orElse(0.0);
            performance.add(new AbstractMap.SimpleEntry<>(entry.getKey(), avg));
        }
        performance.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        performance.forEach(e -> System.out.printf("%d класс - %.2f\n", e.getKey(), e.getValue()));
    }

    // Метод для вывода учеников, изучающих указанный предмет
    public void printSubject(String subject) {
        journal.forEach((numClass, pupils) -> {
            System.out.println("\n" + numClass + " класс:");
            boolean found = false;
            for (SchoolBoy pupil : pupils) {
                if (pupil.equal(subject)) {
                    pupil.print();
                    found = true;
                }
            }
            if (!found) System.out.println("Нет учеников с этим предметом.");
        });
    }

    // Метод для сохранения данных класса в файл
    public void printFile(byte numClass) {
        ArrayList<SchoolBoy> pupils = journal.get(numClass);
        if (pupils == null) {
            System.out.println("Такого класса нет в журнале.");
            return;
        }
        TreeSet<String> subjects = new TreeSet<>();
        for (SchoolBoy pupil : pupils) subjects.add(pupil.getSubject());
        SchoolBoy.writeFile2(subjects, pupils, numClass);
    }

    // Метод для поиска ученика по имени и фамилии
    public void findPupil(String surname, String name) {
        boolean found = false;
        for (Map.Entry<Byte, ArrayList<SchoolBoy>> entry : journal.entrySet()) {
            for (SchoolBoy pupil : entry.getValue()) {
                if (pupil.equal(surname, name)) {
                    System.out.printf("%s %s учится в %d классе\n", surname, name, entry.getKey());
                    found = true;
                }
            }
        }
        if (!found) System.out.println("Такого ученика нет.");
    }

    // Метод для нахождения предмета с лучшей средней успеваемостью
    public void findBestSubject() {
        HashMap<String, Integer> sum = new HashMap<>();
        HashMap<String, Integer> count = new HashMap<>();
        for (ArrayList<SchoolBoy> pupils : journal.values()) {
            for (SchoolBoy pupil : pupils) {
                sum.merge(pupil.getSubject(), pupil.getGrade(), Integer::sum);
                count.merge(pupil.getSubject(), 1, Integer::sum);
            }
        }
        String bestSubject = null;
        double highestAvg = 0.0;
        for (String subject : sum.keySet()) {
            double avg = (double) sum.get(subject) / count.get(subject);
            if (avg > highestAvg) {
                highestAvg = avg;
                bestSubject = subject;
            }
        }
        System.out.printf("Лучший предмет: %s (%.2f)\n", bestSubject, highestAvg);
    }
}