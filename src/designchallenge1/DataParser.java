/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ianona
 */
abstract public class DataParser
{
	protected List<List<String>> data;
	static int i = 0;
	protected Scanner scanner;
        
	abstract void readData(ArrayList<Event> events, String filename);
        
	public void writeData(ArrayList<Event> events, String filenamePSV, String filenameCSV) {
            FileWriter PSVfw;
            FileWriter CSVfw;
            StringBuilder sb;
		
            // READ TO SEPARATE FILE
            try {
		PSVfw = new FileWriter(new File(filenamePSV));
                CSVfw = new FileWriter(new File(filenameCSV));
                BufferedWriter PSVbw = new BufferedWriter(PSVfw);
                PrintWriter PSVpw = new PrintWriter(PSVbw);  
                
		for(Event event: events) {
                    if (!event.isYearly()) {
                        PSVpw.print(event.getName() + " | " + event.getMonth() + "/" + event.getDay() + "/" + event.getYear() + " | " + event.getColor());
                        PSVpw.println();
                        PSVpw.flush();
                    }
                    else {
                        String line;
                        sb = new StringBuilder();
                        sb.append(event.toReport());
                        
                        line = sb.toString();
                        line = line.replace('[', ' ');
                        line = line.replace(']', ' ');
                        line += "\n";
                    	
                        CSVfw.write(line);
                    }
                    
                }
		PSVfw.close();
                CSVfw.close();
                PSVbw.close();
                PSVpw.close();
            } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
	}
        
        public boolean eventExists(Event e, ArrayList<Event> events) {
            for (int i = 0;i<events.size();i++) {
                if (events.get(i).getName().replaceAll(" ", "").equalsIgnoreCase(e.getName().replaceAll(" ", "")))
                    return true;
            }
            return false;
        }
}