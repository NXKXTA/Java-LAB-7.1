public class Main {
    public static void main(String[] args) {
        SchoolJournal journal = new SchoolJournal("C:\\Фигня всякая\\Java Lab 7\\src\\data_school.txt");

        journal.printPupils();         // (0)
        journal.printGrade((byte) 4); // (1)
        journal.printPerformance();   // (2)
        journal.printSubject("математика"); // (3)
        journal.printFile((byte) 5);  // (4)
        journal.findPupil("Иванов", "Иван"); // (5)
        journal.findBestSubject();    // (6)
    }
}