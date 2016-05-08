/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.util.LinkedList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import static javax.sound.midi.ShortMessage.*;


/**
 *
 * @author Benjamin
 */
public class StopObject {
    private boolean enabled, statusChanged;
    private String stopName;
    private byte sysExValue, channelNumber, noteValue;
    private byte messagePosition;
    
   
    public StopObject(String name, byte sysExValue, byte messagePosition, 
                                            byte channelNumber, byte noteName){
        this.enabled = false;
        statusChanged = true;
        this.stopName = name;
        this.sysExValue = sysExValue;
        this.messagePosition = messagePosition;
        this.channelNumber = channelNumber;
        this.noteValue = noteName;
        
    }
    /**
     * 
     * @param message, linkedList of the sysexmessage
     * @return midiShort message of the stop value
     */
    public ShortMessage getMidiStatus(LinkedList<Byte> message){
        setEnabledStatus(message);
        if(statusChanged = true){
            statusChanged = false; //clear the flag
            
            try{
                if(enabled == true){
                    return new ShortMessage(NOTE_ON, channelNumber, noteValue, 127);
                }else if(enabled == false){
                    return new ShortMessage(NOTE_OFF, channelNumber, noteValue, 127);
                }
            }catch (InvalidMidiDataException ex){
                System.out.println("Invaild Midi Data Exception");
                System.out.println("Values are: ChannelNumber = " + channelNumber + 
                        ", noteValue = " + noteValue);
                return null;
            }
        }
        return null;
    }
    
    private void setEnabledStatus(LinkedList<Byte> message){
        boolean tempEnabled;
        if((message.get(messagePosition) & sysExValue) == sysExValue){
            tempEnabled = true;
        }else{
            tempEnabled = false;
        }
        
        if(tempEnabled != enabled){
            statusChanged = true;
            enabled = tempEnabled;
        }
    }
    
    public String getName(){
        return stopName;
    }
}
