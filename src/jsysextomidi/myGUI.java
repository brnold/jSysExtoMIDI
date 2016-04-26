/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
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
        
        JPanel thePanel = buildPanel(midiDevices);
        
        setContentPane(thePanel);
        setSize(250, 150);
        setLocation(100, 100);
        
        setVisible(true);
        
    }
    
    private JPanel buildPanel(LinkedList midiDevices) {
        JComboBox deviceList;
        deviceList = new JComboBox(midiDevices.toArray());
        
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(deviceList, BorderLayout.CENTER);
        
        //add an okay button
        JButton okButton = new JButton("OK");
     //   okButton.addActionListener(this);
        
        return content;
    }
    
}
