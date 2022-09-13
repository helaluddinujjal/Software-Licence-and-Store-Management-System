/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author helal
 */
public class test {

    public static void main(String[] args) {
        try {
            File file = new File("src/data/data.dat");
            if (!file.exists()) {
                file.createNewFile();
                try (FileWriter myWriter = new FileWriter("src/data/data.dat")) {
                    myWriter.write("Files in Java might be tricky, but it is fun enough!\n");
                    myWriter.write("Files in Java might be tricky, but it is fun enough!\n");
                    myWriter.write("Files in Java might be tricky, but it is fun enough!\n");
                    myWriter.close();
                }
            }

            File myObj = new File("src/data/data.dat");
            try (Scanner myReader = new Scanner(myObj)) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);
                }
               myReader.close();
            }

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
