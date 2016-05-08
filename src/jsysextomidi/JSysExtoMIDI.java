/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.*;
import static javax.sound.midi.ShortMessage.NOTE_OFF;
import static javax.sound.midi.ShortMessage.NOTE_ON;

/**
 *
 * @author Benjamin
 */
public class JSysExtoMIDI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MidiDevice device = null, softwarePort = null;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        
        //myGUI theGUI = new myGUI(midiDevicetoString(infos));
        
        for(int i = 0; i < infos.length; i++){
            System.out.println(infos[i]);
        }
        
        System.out.println(infos[1].getDescription());
        System.out.println(infos[2].getDescription());
        
        try{
            //device = MidiSystem.getMidiDevice(infos[1]);
            //device.open();
            
            softwarePort = MidiSystem.getMidiDevice(infos[1  ]);
            softwarePort.open();
        } catch (MidiUnavailableException e){
            System.out.println("Hey, the devices are unavailble!");
        }
        
       // Receiver r = new myMidiReceiver(System.out);
        
        
        try
        {
            //Transmitter tCord = device.getTransmitter(); //Make a Transmitter from my Midi Cord
           // tCord.setReceiver(r); //Set the receiver to my class file
            
            Receiver receiverSoftware = softwarePort.getReceiver();
            
            ShortMessage m;
            for(int i = 0; i < 127; i++){
                
                try {
                    m = new ShortMessage(NOTE_ON, 13, i, 127);
                    receiverSoftware.send(m, -1);
                    Thread.sleep(125);
                    m = new ShortMessage(NOTE_OFF, 13, i, 127);
                } catch (InvalidMidiDataException ex) {
                    System.out.println("Blah Blah data bo0!");
                } catch (InterruptedException ex) {
                    Logger.getLogger(JSysExtoMIDI.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }
        }
        catch (MidiUnavailableException e)
                {
                        System.out.println("wasn't able to connect the device's Transmitter to the Receiver:");
                        System.out.println(e); 
                        //device.close();
                        System.exit(1);
                }
        
        try
            {
                
                System.in.read();
                System.out.print("Test");
            }
    catch (IOException ioe)
            {
            }
		//device.close();
		//System.out.println("Received "+((DumpReceiver) r).seCount+" sysex messages with a total of "+((DumpReceiver) r).seByteCount+" bytes");
		//System.out.println("Received "+((DumpReceiver) r).smCount+" short messages with a total of "+((DumpReceiver) r).smByteCount+" bytes");
		//System.out.println("Received a total of "+(((DumpReceiver) r).smByteCount + ((DumpReceiver) r).seByteCount)+" bytes");
        
            device.close();
        
    }
    
    private static LinkedList<String> midiDevicetoString(MidiDevice.Info[] list){
        LinkedList<String> Llist = new LinkedList();
        for(int i = 0; i < list.length; i++){
            Llist.add(list[i].toString());
        }
        return Llist;
        
    }
}
