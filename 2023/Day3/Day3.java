import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day3{

    private static int gearSum = 0;
    public static void main(String[] args) throws IOException{
        FileInputStream schematicStream = new FileInputStream(new File("./schematic.txt"));
        Scanner schematicScan = new Scanner(schematicStream);

        //initialize the 2d matrix 
        String[][] matrix = new String[140][140];
        for(int lineIndex = 0; schematicScan.hasNextLine(); lineIndex++){
            String currentLine = schematicScan.nextLine();

            for (int characterIndex = 0; characterIndex < currentLine.length(); characterIndex++){
                matrix[lineIndex][characterIndex] = String.valueOf(currentLine.charAt(characterIndex));
            }
        }

        // for(String[] line : matrix){
        //     for(String character : line){
        //         System.out.print(character);
        //     }
        //     System.out.println();
        // }
        int result1 = part1(matrix);
        int result2 = part2(matrix);

        System.out.println("(Part 1) Schematic sum: " + result1 + "\n" +
        "(Part 2) Gear sum: " + result2);

        schematicScan.close();

    }

    public static int part1(String[][] matrix){
        int sum = 0;
        for (int i = 0; i < matrix.length; i++){
            for (int ii = 0; ii < matrix[i].length; ii++){
                String prospectNum = "";
                boolean symbolFlag = false;
                // System.out.print(matrix[i][ii]);
                while(matrix[i][ii].matches("\\d")){
                    if(checkForSymbol(matrix, i, ii)){
                        symbolFlag = true;
                    }
                    prospectNum += matrix[i][ii];
                    if(ii < matrix[i].length - 1){
                        ii += 1;
                    } else {
                        break;
                    }
                }

                if (symbolFlag){
                    sum += Integer.valueOf(prospectNum);
                    // System.out.println(prospectNum);
                }
            }
        }
        return sum;
    }
    public static int part2(String[][] matrix){
        HashMap<String, ArrayList<Integer>> gearMap = new HashMap<>();
        for (int i = 0; i < matrix.length; i++){
            for (int ii = 0; ii < matrix[i].length; ii++){
                String prospectNum = "";
                String gearCoordinate = "";
                boolean gearFlag = false;
                // System.out.print(matrix[i][ii]);
                while(matrix[i][ii].matches("\\d")){
                    if(!checkForGear(matrix, i, ii).equals("false")){
                        gearFlag = true;
                        gearCoordinate = checkForGear(matrix, i, ii);
                    }
                    prospectNum += matrix[i][ii];
                    if(ii < matrix[i].length - 1){
                        ii += 1;
                    } else {
                        break;
                    }
                }

                if (gearFlag){
                    if(gearMap.get(gearCoordinate) == null){
                        gearMap.put(gearCoordinate, new ArrayList<>());
                        gearMap.get(gearCoordinate).add(Integer.valueOf(prospectNum));
                    } else {
                        gearMap.get(gearCoordinate).add(Integer.valueOf(prospectNum));
                    }
                }
            }
        }

        gearMap.forEach((gearCoordinate, assocNums) -> {
            if (assocNums.size() == 2){
                Day3.gearSum += assocNums.get(0) * assocNums.get(1);
            }
        });
        return Day3.gearSum;
    }

    public static boolean checkForSymbol(String[][] matrix, int y, int x){
        if((y > 0 && y < matrix.length - 1) && (x > 0 && x < matrix[0].length - 1)){ // if coordinate is in middle
            //check above 3
            for(int i = x - 1; i < x + 2; i++){
                if (!matrix[y - 1][i].matches("[0-9]|[.]")){
                    return true;
                }
            }
            //check beside
            if(!matrix[y][x - 1].matches("[0-9]|[.]") || !matrix[y][x + 1].matches("[0-9]|[.]")) {
                return true;
            }
            //check below 3
            for(int i = x - 1; i < x + 2; i++){
                if (!matrix[y + 1][i].matches("[0-9]|[.]")){
                    return true;
                }
            }
        }else if(y == 0){ // if coordinate is at the top line of the matrix
            if (x > 0 && x < matrix[0].length - 1){// if  coordinate is not at any horizontal end
                //check beside
                if(!matrix[y][x - 1].matches("[0-9]|[.]") || !matrix[y][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
                //check below 3
                for(int i = x - 1; i < x + 2; i++){
                    if (!matrix[y + 1][i].matches("[0-9]|[.]")){
                        return true;
                    }
                }
            }else if (x == 0){ // if ccoordinate is at top left corner
                //check beside
                if(!matrix[y][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y + 1][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y + 1][x].matches("[0-9]|[.]")) {
                    return true;
                }

            }else if (x == matrix[0].length - 1) { // if coordinate is at top right corner
                //check beside
                if(!matrix[y][x - 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y + 1][x - 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y + 1][x].matches("[0-9]|[.]")) {
                    return true;
                }
            }

        } else if(y == matrix.length - 1){ // if coordinate is at bottom line of matrix
            if (x > 0 && x < matrix[0].length - 1){// if  coordinate is not at any horizontal end
                //check above 3
                for(int i = x - 1; i < x + 2; i++){
                    if (!matrix[y - 1][i].matches("[0-9]|[.]")){
                        return true;
                    }
                }
                //check beside
                if(!matrix[y][x - 1].matches("[0-9]|[.]") || !matrix[y][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
            }else if (x == 0){ // if ccoordinate is at bottom left corner
                //check beside
                if(!matrix[y][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y - 1][x + 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y - 1][x].matches("[0-9]|[.]")) {
                    return true;
                }
            }else if (x == matrix[0].length - 1) { // if coordinate is at bottom right corner
                //check beside
                if(!matrix[y][x - 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y - 1][x - 1].matches("[0-9]|[.]")) {
                    return true;
                }
                if(!matrix[y - 1][x].matches("[0-9]|[.]")) {
                    return true;
                }
            }           
        } else if(x == 0){ // if coordinate is at horizontal left edge(not top or bottom)
            //check beside
            if(!matrix[y][x + 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y - 1][x + 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y - 1][x].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y + 1][x + 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y + 1][x].matches("[0-9]|[.]")) {
                return true;
            }

        } else if(x == matrix[0].length - 1){  // if coordinate is at horizontal right edge(not top or bottom)
            //check beside
            if(!matrix[y][x - 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y - 1][x - 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y - 1][x].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y + 1][x - 1].matches("[0-9]|[.]")) {
                return true;
            }
            if(!matrix[y + 1][x].matches("[0-9]|[.]")) {
                return true;
            }
        }
        return false;
    }

    // public static boolean checkForSymbol(String[][] matrix, int y, int x) {
    //     // Check if the element at the given coordinates is a symbol
    //     String element = matrix[y][x];
    //     if (!element.matches("[0-9.]")) {
    //         return true;
    //     }
    
    //     // Check vertically adjacent elements
    //     if (y > 0 && matrix[y-1][x].matches("[^0-9.]")) {
    //         return true;
    //     }
    //     if (y < matrix.length - 1 && matrix[y+1][x].matches("[^0-9.]")) {
    //         return true;
    //     }
    
    //     // Check horizontally adjacent elements
    //     if (x > 0 && matrix[y][x-1].matches("[^0-9.]")) {
    //         return true;
    //     }
    //     if (x < matrix[0].length - 1 && matrix[y][x+1].matches("[^0-9.]")) {
    //         return true;
    //     }
    
    //     // Check diagonally adjacent elements
    //     if (y > 0 && x > 0 && matrix[y-1][x-1].matches("[^0-9.]")) {
    //         return true;
    //     }
    //     if (y > 0 && x < matrix[0].length - 1 && matrix[y-1][x+1].matches("[^0-9.]")) {
    //         return true;
    //     }
    //     if (y < matrix.length - 1 && x > 0 && matrix[y+1][x-1].matches("[^0-9.]")) {
    //         return true;
    //     }
    //     if (y < matrix.length - 1 && x < matrix[0].length - 1 && matrix[y+1][x+1].matches("[^0-9.]")) {
    //         return true;
    //     }
    
    //     // If no symbol was found, return false
    //     return false;
    // }


    public static String checkForGear(String[][] matrix, int y, int x){
        if((y > 0 && y < matrix.length - 1) && (x > 0 && x < matrix[0].length - 1)){ // if coordinate is in middle
            //check above 3
            for(int i = x - 1; i < x + 2; i++){
                if (matrix[y - 1][i].matches("[*]")){
                    return String.valueOf(y-1) + "," + String.valueOf(i);
                }
            }
            //check beside
            if(matrix[y][x - 1].matches("[*]")) {
                return String.valueOf(y) + "," + String.valueOf(x-1);
            }
            if(matrix[y][x + 1].matches("[*]")){
                return String.valueOf(y) + "," + String.valueOf(x+1);
            }
            //check below 3
            for(int i = x - 1; i < x + 2; i++){
                if (matrix[y + 1][i].matches("[*]")){
                    return String.valueOf(y+1) + "," + String.valueOf(i);
                }
            }
        }else if(y == 0){ // if coordinate is at the top line of the matrix
            if (x > 0 && x < matrix[0].length - 1){// if  coordinate is not at any horizontal end
                //check beside
                if(matrix[y][x - 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x-1);
                }
                if( matrix[y][x + 1].matches("[*]")){
                    return String.valueOf(y) + "," + String.valueOf(x+1);
                }
                //check below 3
                for(int i = x - 1; i < x + 2; i++){
                    if (matrix[y + 1][i].matches("[*]")){
                        return String.valueOf(y+1) + "," + String.valueOf(i);
                    }
                }
            }else if (x == 0){ // if ccoordinate is at top left corner
                //check beside
                if(matrix[y][x + 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x+1);
                }
                if(matrix[y + 1][x + 1].matches("[*]")) {
                    return String.valueOf(y+1) + "," + String.valueOf(x+1);
                }
                if(matrix[y + 1][x].matches("[*]")) {
                    return String.valueOf(y+1) + "," + String.valueOf(x);
                }

            }else if (x == matrix[0].length - 1) { // if coordinate is at top right corner
                //check beside
                if(matrix[y][x - 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x-1);
                }
                if(matrix[y + 1][x - 1].matches("[*]")) {
                    return String.valueOf(y+1) + "," + String.valueOf(x-1);
                }
                if(matrix[y + 1][x].matches("[*]")) {
                    return String.valueOf(y+1) + "," + String.valueOf(x);
                }
            }

        } else if(y == matrix.length - 1){ // if coordinate is at bottom line of matrix
            if (x > 0 && x < matrix[0].length - 1){// if  coordinate is not at any horizontal end
                //check above 3
                for(int i = x - 1; i < x + 2; i++){
                    if (matrix[y - 1][i].matches("[*]")){
                        return String.valueOf(y-1) + "," + String.valueOf(i);
                    }
                }
                //check beside
                if(matrix[y][x - 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x-1);
                }
                if(matrix[y][x + 1].matches("[*]")){
                    return String.valueOf(y) + "," + String.valueOf(x+1);
                }
            }else if (x == 0){ // if ccoordinate is at bottom left corner
                //check beside
                if(matrix[y][x + 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x+1);
                }
                if(matrix[y - 1][x + 1].matches("[*]")) {
                    return String.valueOf(y-1) + "," + String.valueOf(x+1);
                }
                if(matrix[y - 1][x].matches("[*]")) {
                    return String.valueOf(y-1) + "," + String.valueOf(x);
                }
            }else if (x == matrix[0].length - 1) { // if coordinate is at bottom right corner
                //check beside
                if(matrix[y][x - 1].matches("[*]")) {
                    return String.valueOf(y) + "," + String.valueOf(x-1);
                }
                if(matrix[y - 1][x - 1].matches("[*]")) {
                    return String.valueOf(y-1) + "," + String.valueOf(x-1);
                }
                if(matrix[y - 1][x].matches("[*]")) {
                    return String.valueOf(y-1) + "," + String.valueOf(x);
                }
            }           
        } else if(x == 0){ // if coordinate is at horizontal left edge(not top or bottom)
            //check beside
            if(matrix[y][x + 1].matches("[*]")) {
                return String.valueOf(y) + "," + String.valueOf(x+1);
            }
            if(matrix[y - 1][x + 1].matches("[*]")) {
                return String.valueOf(y-1) + "," + String.valueOf(x+1);
            }
            if(matrix[y - 1][x].matches("[*]")) {
                return String.valueOf(y-1) + "," + String.valueOf(x);
            }
            if(matrix[y + 1][x + 1].matches("[*]")) {
                return String.valueOf(y+1) + "," + String.valueOf(x+1);
            }
            if(matrix[y + 1][x].matches("[*]")) {
                return String.valueOf(y+1) + "," + String.valueOf(x);
            }

        } else if(x == matrix[0].length - 1){  // if coordinate is at horizontal right edge(not top or bottom)
            //check beside
            if(matrix[y][x - 1].matches("[*]")) {
                return String.valueOf(y) + "," + String.valueOf(x-1);
            }
            if(matrix[y - 1][x - 1].matches("[*]")) {
                return String.valueOf(y-1) + "," + String.valueOf(x-1);
            }
            if(matrix[y - 1][x].matches("[*]")) {
                return String.valueOf(y-1) + "," + String.valueOf(x);
            }
            if(matrix[y + 1][x - 1].matches("[*]")) {
                return String.valueOf(y+1) + "," + String.valueOf(x-1);
            }
            if(matrix[y + 1][x].matches("[*]")) {
                return String.valueOf(y+1) + "," + String.valueOf(x);
            }
        }
        return "false";
    }
}