package Methods;

import java.util.Scanner;

public class grade {
    static void main() {
        System.out.print("Enter your marks : ");
        Scanner input = new Scanner(System.in);
        int y  = input.nextInt();
        System.out.println(marks(y));
    }
    static String marks(int x){
        if(x>91)
            return "Grade : AA";
        else if (x<90 && x>81)
            return "Grade : AB";
        else if (x<80 && x>71)
            return "Grade : BB";
        else if (x<70 && x>61)
            return "Grade : BC";
        else if (x<60 && x>51)
            return "Grade : CD";
        else if (x<50 && x>41)
            return "Grade : DD";
        else if (x>0 && x<=40)
            return "Fail";
        else
            return "Enter a valid marks.";
    }
}
