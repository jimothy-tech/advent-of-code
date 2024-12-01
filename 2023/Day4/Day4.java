package Day4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.*;

public class Day4 {
    
    public static void main(String[] args) throws FileNotFoundException{
        FileInputStream cardStream = new FileInputStream(new File("./Day4/cards.txt"));

        Scanner cardsScan = new Scanner(cardStream);

        ArrayList<String> cardsArray = new ArrayList<>();

        while(cardsScan.hasNextLine()){
            cardsArray.add(cardsScan.nextLine());
        }

        int result1 = part1(cardsArray);
        int result2 = part2(cardsArray);

        System.out.println("Scratch-off sum: " + result1 + "\n" + 
        "Card Instances: " + result2);

        cardsScan.close();

    }


    private static int part1(ArrayList<String> inputArray){
        int sum = 0;

        for(String line : inputArray){
            //lop off the card name
            String cardNumbers = line.split(":")[1];
            String[] splitNumbers = cardNumbers.split("[|]");
            //regex pattern matches one or more digits
            Pattern singleWhiteSpace = Pattern.compile("\\d+");
            Matcher matcher1 = singleWhiteSpace.matcher(splitNumbers[0]);
            Matcher matcher2 = singleWhiteSpace.matcher(splitNumbers[1]);

            int points = 0;
            int timesWon = 0;

            while(matcher1.find()){
                while(matcher2.find()){
                    // System.out.println(matcher1.group() + " compared to " + matcher2.group()); 
                    if (matcher1.group().equals(matcher2.group())){
                        timesWon += 1;
                    }
                }
                matcher2.reset();
            }

            if(timesWon == 1){
                points = 1;
            }else if(timesWon > 1){
                points = (int)Math.pow(2, timesWon - 1);
            }

            sum += points;

        }
        return sum;
    }


    public static int part2(ArrayList<String> inputArray){
        ArrayList<Integer> cardInstances = new ArrayList<>();
        //initialize with 1's
        for(String card : inputArray) {
            cardInstances.add(1);
        }

        int cards = 0;

        for(int i = 0; i < inputArray.size(); i++){
            int currentInstances = cardInstances.get(i);
            //lop off the card name
            String cardNumbers = inputArray.get(i).split(":")[1];
            String[] splitNumbers = cardNumbers.split("[|]");
            //regex pattern matches one or more digits
            Pattern singleWhiteSpace = Pattern.compile("\\d+");
            Matcher matcher1 = singleWhiteSpace.matcher(splitNumbers[0]);
            Matcher matcher2 = singleWhiteSpace.matcher(splitNumbers[1]);

            int timesWon = 0;

            while(matcher1.find()){
                while(matcher2.find()){
                    // System.out.println(matcher1.group() + " compared to " + matcher2.group()); 
                    if (matcher1.group().equals(matcher2.group())){
                        timesWon += 1;
                    }
                }
                matcher2.reset();
            }

            for(int ii = i + 1; ii < i + timesWon + 1; ii++){

                if (ii == inputArray.size() - 1){
                    cardInstances.set(ii, cardInstances.get(ii) + currentInstances);
                    break;
                } else {
                    cardInstances.set(ii, cardInstances.get(ii) + currentInstances);
                }                    
            }
        }

        for(int instances : cardInstances){
            cards += instances;
        }

        return cards;
    }    
}
