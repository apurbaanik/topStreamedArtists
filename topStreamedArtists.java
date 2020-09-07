/**
 * @author Anik Barua
 * @since 09-06-2020
 * @version 1.0
 *
 * Description: Lab #2 - This java program prints out how many times each
 * artists appear from the Spotify's Top 200 Artists CSV dataset, and a 
 * formatted chart of the artist's list.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.LinkedHashMap;

public class topStreamedArtists { // class started

    public static void main(String[] args) throws Exception { //main method

        PrintStream report1 = new PrintStream("artist&number.txt");
        // First output file will contain artist's appearence frequency
        PrintStream report2 = new PrintStream("Artists-WeekOf08302020.txt");
        // Second output file will contain the formal chart with artist's details

        int row = 200;
        int column = 5;

        String[][] array = new String[row][column];
        //Multi-Dimensinal Array that will contain readings(Artist's data) from the csv file.

        //Read in the csv file part 
        try {
            Scanner sc = new Scanner(new File("spotify_music.csv"));
            for (int i = 0; i < row; i++) {
                String[] line = sc.nextLine().split(",");
                // Splits words by "," from each line
                for (int j = 0; j < column; j++) {
                    array[i][j] = line[j];
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } // End of try and catch block

        LinkedHashMap<String, Integer> map = frequency(array);
        // this map will contain the artist name and the total number of apperarence on the list

        report1.println("Report - Artist and their number of appearance");
        report1.println();

        // Prints out part one of the report (Artist name and total number of apperarence)
        for (Map.Entry<String, Integer> artist : map.entrySet()) {
            report1.println(artist.getKey() + " " + "appears" + ": " + artist.getValue() + " times");
        }

        report1.close();

        TreeMap<String, String[]> map2 = removeDuplicates(array);
        // this map will contain the original chart but no duplicates from my nested array

        // Prints out part two of the report original chart containing zero duplicates
        int count = 1;
        for (Map.Entry<String, String[]> x : map2.entrySet()) {
            report2.println(count + " Track Name: " + x.getValue()[1] + ", Artist Name: "
                    + (x.getValue()[2]).toUpperCase() + ", Streams: " + x.getValue()[3]
                    + ", URL: " + x.getValue()[4]);
                    // The name is of the artist is uppercased during the print
            count++;
            report2.println();
        }

        report2.close();
    } //end of main

    /*
    This frequency method counts the total number of appearance of each artists. It takes a multidimensional array and returns a LinkedHashMap. 
     */
    public static LinkedHashMap frequency(String[][] array) {
        String[] names = new String[200]; // will contain the artist names

        for (int i = 0; i < 200; i++) {
            names[i] = array[i][2];
        }

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        //Using LinkedHashMap map because it won't have duplicates and I don't want it to be sorted.

        for (String artist : names) {  //gets artists name
            Integer count = map.get(artist);  // gets the occurance value
            if (count == null) {
                map.put(artist, 1); // if emppty puts one
            } else {
                map.put(artist, count + 1); //if comes up again, it adds 1 to it
            }
        }
        return map;// return map to main method
    } // end of frequency method

    /*
    This removeDuplicates method removes duplicates from the array and sorts it. It takes a multidimensional array and returns a TreeMap. 
     */
    public static TreeMap removeDuplicates(String[][] array) {
        String[] names = new String[200]; // will contain the artist names

        for (int i = 0; i < 200; i++) {
            names[i] = array[i][2]; // fills it out from multi-dimensional array
        }

        TreeMap<String, String[]> map = new TreeMap<>();
        // Using TreeMap because I want it sorted and won't have duplicates

        for (int i = 0; i < names.length; i++) {
            if (!map.containsKey(names[i])) {
                //if my map doesn't have the name put it in the map
                map.put(names[i], array[i]);
            }
        }
        return map; // returns map
    } // end of removeDuplicates method

} // end of class topStreamedArtists