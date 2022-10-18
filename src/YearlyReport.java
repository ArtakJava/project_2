import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class YearlyReport {
    int year;
    YearlyReport (int year) {
        this.year = year;
    }
    static HashMap<Integer, Integer> yearlyReportIncome = new HashMap<>();
    static HashMap<Integer, Integer> yearlyReportExpense = new HashMap<>();
    boolean isYearlyReadCompleted = false;

    public void read() {
        String path = "C:/Users/user/dev/java-sprint2-hw/resources/y." + year + ".csv";
        String fileContents = YearlyReport.readFileContentsOrNull(path);
        String[] lines = fileContents.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] elements = lines[i].split(",");
            if (!yearlyReportIncome.containsKey(Integer.parseInt(elements[0]))) {
                yearlyReportIncome.put(Integer.parseInt(elements[0]), 0);
                yearlyReportExpense.put(Integer.parseInt(elements[0]), 0);
            }
            if (elements[2].equals("false")) {
                yearlyReportIncome.put(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
            } else if(elements[2].equals("true")) {
                yearlyReportExpense.put(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]));
            }
        }
        System.out.print("Отчет за " + year + " год ");
        if (fileContents != null) {
            System.out.println(" считан.");
            isYearlyReadCompleted = true;
        } else {
            System.out.println(" не считан.");
            isYearlyReadCompleted = false;
        }
    }

    private static String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void printYearlyReport() {
        if (isYearlyReadCompleted) {
            System.out.println(year + " год:");
            System.out.println("Прибыль по каждому месяцу:");
            for (int month: yearlyReportIncome.keySet()) {
                System.out.println(" - За " + MonthlyReport.months[month-1] + " прибыль: " + getProfitByMonths(month));
            }
            System.out.println("Средний расход за все месяцы в году: " + getAverage(YearlyReport.yearlyReportExpense));
            System.out.println("Средний доход за все месяцы в году: " + getAverage(YearlyReport.yearlyReportIncome));
        } else {
            System.out.println("Годовой отчёт еще не считан");
        }
    }

    public int getAverage(HashMap yearlyReport) {
        int sum = 0;
        HashMap <Integer, Integer> getSum = yearlyReport;
        for (Integer yearlyReportLine: getSum.values()) {
            sum += yearlyReportLine;
        }
        return sum/yearlyReport.size();
    }

    public int getProfitByMonths(int month) {
        return yearlyReportIncome.get(month) - yearlyReportExpense.get(month);
    }
}
