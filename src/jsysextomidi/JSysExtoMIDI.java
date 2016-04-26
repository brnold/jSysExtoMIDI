/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.io.IOException;
import java.util.LinkedList;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 *
 * @author Benjamin
 */
public class JSysExtoMIDI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MidiDevice device = null;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        
        //myGUI theGUI = new myGUI(midiDevicetoString(infos));
        
        for(int i = 0; i < infos.length; i++){
            System.out.println(infos[i]);
        }
        
        System.out.println(infos[1].getDescription());
        System.out.println(infos[2].getDescription());
        
        try{
            device = MidiSystem.getMidiDevice(infos[1]);
            device.open();
        } catch (MidiUnavailableException e){
            System.out.println("Hey, the device is unavailble!");
        }
        
        Receiver	r = new DumpReceiver(System.out);
        try
                {
                        Transmitter	t = device.getTransmitter();
                        t.setReceiver(r);
                }
        catch (MidiUnavailableException e)
                {
                        System.out.println("wasn't able to connect the device's Transmitter to the Receiver:");
                        System.out.println(e); 
                        device.close();
                        System.exit(1);
                }
        
        try
			{
				System.in.read();
			}
		catch (IOException ioe)
			{
			}
		device.close();
		System.out.println("Received "+((DumpReceiver) r).seCount+" sysex messages with a total of "+((DumpReceiver) r).seByteCount+" bytes");
		System.out.println("Received "+((DumpReceiver) r).smCount+" short messages with a total of "+((DumpReceiver) r).smByteCount+" bytes");
		System.out.println("Received a total of "+(((DumpReceiver) r).smByteCount + ((DumpReceiver) r).seByteCount)+" bytes");
        
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
