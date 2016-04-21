/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.util.LinkedList;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

/**
 *
 * @author Benjamin
 */
public class JSysExtoMIDI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        
        myGUI theGUI = new myGUI();
        
        for(int i = 0; i < infos.length; i++){
            System.out.println(infos[i]);
        }
    }
    
    private LinkedList<String> midiDevicetoString(MidiDevice.Info[] list){
        LinkedList<String> Llist = new LinkedList();
        for(int i = 0; i < list.length; i++){
            Llist.add(list.toString());
        }
        return Llist;
        
    }
}
