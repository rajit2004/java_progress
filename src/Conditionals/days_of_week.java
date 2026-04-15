package conditionals;

import java.util.Scanner;
public class days_of_week {
    public static void main(String[] args) {
        System.out.print("Enter a number : ");
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        switch (num) {
//            case 1 :
//                System.out.println("Sunday");
//                break;
//            case 2 :
//                System.out.println("Monday");
//                break;
//            case 3 :
//                System.out.println("Tuesday");
//                break;
//            case 4 :
//                System.out.println("Wednesday");
//                break;
//            case 5 :
//                System.out.println("Thursday");
//                break;
//            case 6 :
//                System.out.println("Friday");
//                break;
//            case 7 :
//                System.out.println("Saturday");
//                break;
//            default:
//                System.out.println("Enter a valid num ");

//            only works in intellij ide
//            case 1 -> System.out.println("monday");
//            case 2 -> System.out.println("tuesday");
//            case 3 -> System.out.println("wednesday");
//            case 4 -> System.out.println("thursday");
//            case 5 -> System.out.println("friday");
//            case 6 -> System.out.println("saturday");
//            case 7 -> System.out.println("sunday");
//            default -> System.out.println("not a valid number");
        }
//        if (num == 1) {
//            System.out.println("Sunday");
//        } else if (num == 2) {
//            System.out.println("Monday");
//        } else if (num == 3) {
//            System.out.println("Tuesday");
//        } else if (num == 4) {
//            System.out.println("Wednesday");
//        } else if (num == 5) {
//            System.out.println("Thursday");
//        } else if (num == 6) {
//            System.out.println("Friday");
//        } else if (num == 7) {
//            System.out.println("Saturday");
//        } else {
//            System.out.println("Enter a valid num ");
//        }

//        switch (num) {
//            case 1, 2, 3, 4, 5 -> System.out.println("Weekdays");
//            case 6, 7 -> System.out.println("Weekends");
//        }
        switch (num){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5 :
                System.out.println("Weekdays");
                break;
            case 6:
            case 7 :
                System.out.println("Weekends");
        }
    }
}