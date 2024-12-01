package Day1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class Trebuchet {
    public static void main(String[] args) throws IOException{


        FileInputStream doc = new FileInputStream(new File("Day1/document.txt"));
        Scanner scanner = new Scanner(doc);
        
        ArrayList<String> documentArray = new ArrayList<>();

        while(scanner.hasNextLine()){
            documentArray.add(scanner.nextLine());
        }

        int result1 = part1(documentArray);
        
        int result2 = part2(documentArray);

        System.out.println("(Part 1) Document calibration sum: " + result1 + "\n" + 
                            "(Part 2) Document calibration sum: " + result2);

        scanner.close();


        // -------Test---------

        System.out.println("\n\n-------------Test---------------");

        FileInputStream test = new FileInputStream(new File("Day1/test.txt"));
        Scanner testScanner = new Scanner(test);

        ArrayList<String> testArray = new ArrayList<>();

        while(testScanner.hasNextLine()){
            testArray.add(testScanner.nextLine());
        }
        
        int testResult = part2(testArray);

        System.out.println("Test result: " + testResult);

        testScanner.close();
        
    }

    private static int part1(ArrayList<String> documentArray){
        int docSum = 0;
        int lineNumber = 0;

        for (String line : documentArray){
            
            String lineValue = "";
            char firstDigit = ' ';
            char lastDigit = ' ';

            lineNumber += 1;

            for (int i = 0; i < line.length(); i ++) {

                if(Character.isDigit(line.charAt(i)) && firstDigit == ' '){
                    firstDigit = line.charAt(i);
                } else if (Character.isDigit(line.charAt(i))) {
                    lastDigit = line.charAt(i);
                }
            }

            //if lastDigit is still empty, assign it the same as first digit
            if (lastDigit == ' '){
                lastDigit = firstDigit;
            }

            assert (firstDigit != ' ' && lastDigit != ' ') : "Line " + lineNumber + " did not have a digit";

            lineValue = new String(new char[] {firstDigit, lastDigit});
            
            docSum += Integer.valueOf(lineValue);

            System.out.println("First digit: " + firstDigit + "\n" + 
            "Last digit: " + lastDigit + "\n" +
            "Line " + lineNumber + " value: " + lineValue);

            
        }

        return docSum;
    }

    private static int part2(ArrayList<String> documentArray){
        int docSum = 0;
        int lineNumber = 0;

        String[] digitWords = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        HashMap<String, Character> digitMap = new HashMap<>();

        //build digitMap
        for (int i = 0, digit = 1; i < digitWords.length ; i ++, digit ++){

            digitMap.put(digitWords[i], Character.forDigit(digit, 10));
        }

        for (String line : documentArray){

            String lineValue = "";
            char firstDigit = ' ';
            char lastDigit = ' ';

            lineNumber += 1;

            for (int i = 0; i < line.length(); i ++) {

                if(Character.isDigit(line.charAt(i))){
                    if (firstDigit == ' ') {
                        firstDigit = line.charAt(i); 
                    } else {
                        lastDigit = line.charAt(i);
                    }
                    
                } else {
                    String digitProspect = "";
                    String first = "";
                    String last = "";

                    while(!Character.isDigit(line.charAt(i))){
                        // System.out.println("Loop 3");
                        digitProspect += line.charAt(i);
                        i++;

                        if (i == line.length()){
                            break;
                        }
                    }
                    i -= 1;

                    for (String digit : digitWords){

                        if (digitProspect.contains(digit)){
                            if(first == "" || digitProspect.indexOf(first) > digitProspect.indexOf(digit)){
                                first = digit;
                            }
                            if (last == "" || digitProspect.lastIndexOf(last) < digitProspect.lastIndexOf(digit)) {
                                last = digit;
                            }
                        }
                    }
                    if (firstDigit == ' ' && first != ""){
                        firstDigit = digitMap.get(first);
                    } 
                    if (last != "") {
                        lastDigit = digitMap.get(last);
                    }    
                     
                }
            }

            //if lastDigit is still empty, assign it the same as first digit
            if (lastDigit == ' '){
                lastDigit = firstDigit;
            }

            
            lineValue = new String(new char[] {firstDigit, lastDigit});
            docSum += Integer.valueOf(lineValue);

            // System.out.println("First digit: " + firstDigit + "\n" + 
            // "Last digit: " + lastDigit + "\n" +
            // "Line " + lineNumber + " value: " + lineValue);

            System.out.println("Line " + lineNumber + " value: " + lineValue);

        }

        return docSum;
    }

}
