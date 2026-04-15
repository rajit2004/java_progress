package conditionals;

import java.util.Scanner;

public class temperature {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the conditionals.temperature in Celsius:");
        float tempC = input.nextFloat();
        float tempF = (float)(tempC * 1.8) + 32;
        if (tempF >= 102){
            System.out.println("HIGH FEVER" + " Temp in Fahrenheit: " + tempF);
        }
        else if (tempF >= 100){
            System.out.println("FEVER" + " Temp in Fahrenheit: " + tempF);
        }
        else {
            System.out.println("NORMAL" + "Temp in Fahrenheit: " + tempF);
        }
    }
}
