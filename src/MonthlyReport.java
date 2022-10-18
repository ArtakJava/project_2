import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    int year;
    MonthlyReport (int year) {
        this.year = year;
    }
    static final String[] months = new String[] {"январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};

    boolean isMonthlyReadCompleted = false;

    static HashMap<Integer, ArrayList<MonthlyReportLine>> monthlyReports = new HashMap<>();

    public void read() {
        for (int i = 1; i < 4; i++) {
            String path = "C:/Users/user/dev/java-sprint2-hw/resources/m." + year + "0" + i + ".csv";
            String fileContents = MonthlyReport.readFileContentsOrNull(path);
            String[] lines = fileContents.split("\n");
            ArrayList<MonthlyReportLine> monthlyReportLines  = new ArrayList<>();
            for (int j = 1; j < lines.length; j++) {
                String[] elements = lines[j].split(",");
                monthlyReportLines.add(new MonthlyReportLine(elements[0], elements[1], Integer.parseInt(elements[2]), Integer.parseInt(elements[3])));
            }
            monthlyReports.put(i, monthlyReportLines);
            System.out.print("Отчет за " + months[i - 1]);
            if (fileContents != null) {
                System.out.println(" считан.");
                isMonthlyReadCompleted = true;
            } else {
                System.out.println(" не считан.");
                isMonthlyReadCompleted = false;
            }
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

    public void printMonthlyReports() {
        if (isMonthlyReadCompleted) {
            for (Integer month : monthlyReports.keySet()) {
                System.out.println(months[month - 1]);
                HashMap<String, Integer> maxProfitInMonth = findMaxInMonth(month, false);
                for (String maxProfitItem: maxProfitInMonth.keySet()) {
                    System.out.println(" - Самый прибыльный товар: <" + maxProfitItem + "> на сумму " + maxProfitInMonth.get(maxProfitItem) + " рублей");
                }
                HashMap<String, Integer> maxExpenseInMonth = findMaxInMonth(month, true);
                for (String maxExpenseItem: maxExpenseInMonth.keySet()) {
                    System.out.println(" - Самая большая трата: <" + maxExpenseItem + "> на сумму " + maxExpenseInMonth.get(maxExpenseItem) + " рублей");
                }
            }
        } else {
            System.out.println("Месячные отчёты еще не считаны");
        }

    }

    public HashMap<String, Integer> findMaxInMonth(Integer month, boolean is_expense) {
        HashMap<String, Integer> maxInMonth = new HashMap<>();
        if (monthlyReports.containsKey(month)) {
            String maxProfitItem = "";
            int sum_of_total = 0;
            for (MonthlyReportLine monthlyReportLine: monthlyReports.get(month)) {
                if (monthlyReportLine.is_expense == is_expense && sum_of_total < monthlyReportLine.quantity * monthlyReportLine.sum_of_one) {
                    sum_of_total = monthlyReportLine.quantity * monthlyReportLine.sum_of_one;
                    maxProfitItem = monthlyReportLine.item_name;
                }
            }
            maxInMonth.put(maxProfitItem, sum_of_total);
        }
        return maxInMonth;
    }

    public static int getMonthlyIncome(int month) {
        int monthlyIncome = 0;
        for (MonthlyReportLine  monthlyReportLine: monthlyReports.get(month)) {
            if (monthlyReportLine.is_expense == false) {
                monthlyIncome += monthlyReportLine.quantity * monthlyReportLine.sum_of_one;
            }
        }
        return monthlyIncome;
    }

    public static int getMonthlyExpense(int month) {
        int monthlyIncome = 0;
        for (MonthlyReportLine  monthlyReportLine: monthlyReports.get(month)) {
            if (monthlyReportLine.is_expense == true) {
                monthlyIncome += monthlyReportLine.quantity * monthlyReportLine.sum_of_one;
            }
        }
        return monthlyIncome;
    }

    class MonthlyReportLine {
        String item_name;
        boolean is_expense;
        int quantity;
        int sum_of_one;

        MonthlyReportLine (String item_name, String is_expense, int quantity, int sum_of_one) {
            this.item_name = item_name;
            if (is_expense.equals("TRUE")) {
                this.is_expense = true;
            }
            this.quantity = quantity;
            this.sum_of_one = sum_of_one;
        }
    }
}
