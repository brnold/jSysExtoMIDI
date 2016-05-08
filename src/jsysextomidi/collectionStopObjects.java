/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.util.LinkedList;
import javax.sound.midi.ShortMessage;

/**
 *
 * @author Benjamin
 */
public class collectionStopObjects {
    LinkedList<StopObject> listStopObjects;
    LinkedList<ShortMessage> qMidiMessages;
    
    
    public collectionStopObjects(){
        csvToStopList();
        
    }
    
    private void csvToStopList(){
        
    }
    
    private void sysExToMidiConverstion(LinkedList message){
        ShortMessage m = null;
        for (StopObject stop : listStopObjects) {
            m = stop.getMidiStatus(message);
            if(m != null)
                qMidiMessages.add(m);
        }
    }
    
    public ShortMessage getNextMessage(){  
        return qMidiMessages.poll();
    }
}
