import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Поехали!
        int userInput = 0;
        int year = 2021;

        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport(year);
        YearlyReport yearlyReport = new YearlyReport(year);

        while (userInput != 123) {
            printMenu();
            userInput = Integer.parseInt(scanner.nextLine());
            switch (userInput) {
                case 1:
                    monthlyReport.read();
                    break;
                case 2:
                    yearlyReport.read();
                    break;
                case 3:
                    checkReports(monthlyReport.isMonthlyReadCompleted, yearlyReport.isYearlyReadCompleted);
                    break;
                case 4:
                    monthlyReport.printMonthlyReports();
                    break;
                case 5:
                    yearlyReport.printYearlyReport();
                    break;
                case 123:
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Некорректный ввод.");
            }
        }
    }

    public static void checkReports(boolean isMonthlyReadCompleted, boolean isYearlyReadCompleted) {
        boolean correctIncome = false;
        boolean correctExpense = false;
        if (isMonthlyReadCompleted && isYearlyReadCompleted) {
            for (int month : MonthlyReport.monthlyReports.keySet()) {
                correctIncome = YearlyReport.yearlyReportIncome.get(month) == MonthlyReport.getMonthlyIncome(month);
                correctExpense = YearlyReport.yearlyReportExpense.get(month) == MonthlyReport.getMonthlyExpense(month);
                if (!correctIncome || !correctExpense) {
                    System.out.println("За " + MonthlyReport.months[month - 1] + " обнаружено несоответствие.");
                }
            }
            if (correctIncome && correctExpense) {
                System.out.println("Операция успешно завершена!");
            }
        } else if (!isMonthlyReadCompleted) {
            System.out.println("Месячные отчёты еще не считаны");
        } else {
            System.out.println("Годовой отчёт еще не считан");
        }
    }

    public static void printMenu() {
        System.out.println("1. Считать все месячные отчёты.");
        System.out.println("2. Считать годовой отчёт.");
        System.out.println("3. Сверить отчёты.");
        System.out.println("4. Вывести информацию о всех месячных отчётах.");
        System.out.println("5. Вывести информацию о годовом отчёте.");
    }
}

