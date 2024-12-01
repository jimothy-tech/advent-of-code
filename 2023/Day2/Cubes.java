package Day2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Cubes {

    private static int power = 1;
    public static void main(String[] args) throws FileNotFoundException{

        FileInputStream gamesStream = new FileInputStream(new File("Day2/games.txt"));
        Scanner scan = new Scanner(gamesStream);

        ArrayList<String> gamesArray = new ArrayList<>();

        while(scan.hasNextLine()){
            gamesArray.add(scan.nextLine());
        }

        int result1 = part1(gamesArray);
        int result2 = part2(gamesArray);

        System.out.println("Gamesum: " + result1 + "\n" +
        "Power Sum: " + result2);

        scan.close();

    }

    private static int part1(ArrayList<String> games) {
        int gameSum = 0;

        //Limits
        HashMap<String, Integer> limits = new HashMap<>();
        limits.put("red", 12);
        limits.put("green", 13);
        limits.put("blue", 14);

        for(String game : games){

            int gameId = Integer.valueOf(game.split(" ")[1].replaceAll(":", ""));
            
            if(isGameValid(game, limits)){
                gameSum += gameId;
            } 
        }

        return gameSum;
    }
    
    private static int part2(ArrayList<String> games){
        int powerSum = 0;
        
        for (String game : games){
            Cubes.power = 1;
            getLeastSet(game).forEach((key, value) -> {
                Cubes.power = power * value;
            });

            powerSum += Cubes.power;
        }

        return powerSum;
    }

    private static HashMap<String, Integer> getLeastSet(String game){
        HashMap<String, Integer> leastSet = new HashMap<>();
        String[] reveals = game.split(": ")[1].split("; ");
        for (String reveal : reveals){
            String[] colors = reveal.split(", ");
            for(String color : colors){
                String[] pair = color.split(" ");
                if (leastSet.get(pair[1]) == null || leastSet.get(pair[1]) < Integer.valueOf(pair[0])){
                    leastSet.put(pair[1], Integer.valueOf(pair[0]));
                }
                
            }
        }

        return leastSet;
    }

    private static boolean isGameValid(String game, HashMap<String, Integer> limits){
        String[] reveals = game.split(": ")[1].split("; ");
        for (String reveal : reveals){
            String[] colors = reveal.split(", ");
            for(String color : colors){
                String[] pair = color.split(" ");
                if(Integer.valueOf(pair[0]) > limits.get(pair[1])){
                    // System.out.println(pair[0] + pair[1]);
                    return false;
                }
            }
        }
        return true;
    }


}
