/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ianona
 */
public class PSVDataParser extends DataParser {
    @Override
    void readData(ArrayList<Event> events, String filename) {
        try {
            scanner = new Scanner(new File(filename));
            while(scanner.hasNext()){
                String line= scanner.nextLine();
                String[] values = line.split("\\|");
                String[] date = values[1].split("/");
                events.add(new Event(values[0],values[2],Integer.valueOf(date[2].replaceAll(" ", "")), Integer.valueOf(date[0].replaceAll(" ", "")), Integer.valueOf(date[1].replaceAll(" ", "")), false));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("FILE NOT FOUND");
            // e.printStackTrace();
        }
    }
    
    
}
