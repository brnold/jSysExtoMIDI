/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;
import java.util.LinkedList;
import javax.swing.*;

/**
 *
 * @author Benjamin
 */
public class myGUI extends JFrame{
    
    public myGUI(LinkedList midiDevices){
        setTitle("SysEx Midi Helper");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buildPanel(midiDevices);
        
        setVisible(true);
        
    }
    
    private void buildPanel(LinkedList midiDevices){
        JComboBox deviceList = new JComboBox(midiDevices);
    }
    
}
