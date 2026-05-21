package Strings;

import java.util.Arrays;
import java.util.Locale;

public class InBuilt_Methods {
    public static void main(String[] args) {
        String name = "Ranesh Rajit";

        System.out.println(name.toLowerCase());
        System.out.println(name.toUpperCase());
        System.out.println(name.indexOf('j'));
        System.out.println(name.charAt(5));

        String newName = "          apple is red";
        System.out.println(newName.strip());
        System.out.println(newName);

        String place = "Jaipur Agra Delhi Gurgaon Kota Jaisalmer";
        System.out.println(Arrays.toString(place.split(" ")));          // returns the array of elements from the string separated by regex here " "


        System.out.println(place.contains("Agra"));
        System.out.println(place.contains("Mumbai"));
    }

}
