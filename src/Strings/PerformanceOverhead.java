package Strings;

public class PerformanceOverhead {
    public static void main(String[] args) {
        String series = "";
        for (int i = 0; i < 26; i++) {
            char ch = (char)('a'+i);
            series = series + " " + ch;
            System.out.println(series);
        }
//        only the last iteration is referenced to the series object rest all are dereferenced memory wastage
//        System.out.println(series);
    }

// after i = 26 instead of having alphabets we start getting symbols

/*
so what is the problem here ??

let's dry run this :
    iteration 1 : "" + (a+0) = "" + a = "a"
    iteration 2 : "a" + (a+1) = "a" + b = "ab"
    iteration 3 : "ab" + (a+2) = "ab" + c = "abc"
    and so on.

so after every iteration we have a new object is being created everytime
since strings are immutable so we can't change the value of it . it is not changing the original object
so it's actually creating a new string object and coping the old one and then appending the new changes

every object created with each iteration are memory wastage since it is getting dereferenced everytime
also the time complexity would be n^2
*/

//    solution ??? a data type which allows to modify the string itself rather than creating new objects everytime?
//    here comes stringbuiler

}

