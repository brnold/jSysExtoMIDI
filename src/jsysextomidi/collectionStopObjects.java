/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Benjamin
 */
public class collectionStopObjects {
    LinkedList<StopObject> listStopObjects;
    LinkedList<ShortMessage> qMidiMessages;
    
    
    public collectionStopObjects(){
        listStopObjects = new LinkedList<StopObject>();
        qMidiMessages = new LinkedList<ShortMessage>();
        csvToStopList();
        
    }
    
    private void csvToStopList(){

        String name, sysExValue, messagePosition, channelNumber, noteValue;
        byte sB, mB, cB, nB;
        Scanner fileReader;
        try {
            fileReader = new Scanner (new File("stopList.csv"));
            fileReader.useDelimiter(",");
        
            while (fileReader.hasNext()){
            name = fileReader.next();
            sysExValue = fileReader.next();
            messagePosition = fileReader.next();
            channelNumber = fileReader.next();
            noteValue = fileReader.next();
            
            sB = (byte) (1 << (Byte.valueOf(sysExValue)));
            
            mB = (byte) (Byte.valueOf(messagePosition) + 7);
            cB = Byte.valueOf(channelNumber);
            nB = Byte.valueOf(noteValue);
            listStopObjects.addLast(new StopObject(name, sB, mB, cB, nB));
            }
        
        
        
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(collectionStopObjects.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("List of Stops not found");
        }
        
        
    }
    
    public void sysExToMidiConverstion(LinkedList message){
        ShortMessage m = null;
        for (StopObject stop : listStopObjects) {
            m = stop.getMidiStatus(message);
            if(m != null)
                qMidiMessages.add(m);
        }
    }
    
    public boolean isEmpty(){
        return qMidiMessages.isEmpty();
    }
    
    public ShortMessage getNextMessage(){  
        return qMidiMessages.poll();
    }
    
    @Override
    public String toString(){
        String s;
        
        return s
    }
}
