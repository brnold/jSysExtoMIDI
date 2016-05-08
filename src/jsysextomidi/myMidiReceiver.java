/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import	javax.sound.midi.MidiSystem;
import	javax.sound.midi.InvalidMidiDataException;
import	javax.sound.midi.Sequence;
import	javax.sound.midi.Track;
import	javax.sound.midi.MidiEvent;
import	javax.sound.midi.MidiMessage;
import	javax.sound.midi.ShortMessage;
import	javax.sound.midi.MetaMessage;
import	javax.sound.midi.SysexMessage;
import	javax.sound.midi.Receiver;

/**
 *
 * @author Benjamin
 */
public class myMidiReceiver 
implements	Receiver
{
    private PrintStream		m_printStream;
    String sysExMessageStr;
    LinkedList<Byte> sysExBytes = null, oldMessageBytes= null; 

    
    public myMidiReceiver(PrintStream printStream)
    {
        m_printStream = printStream;
    }
    
    
    @Override
    public void send(MidiMessage message, long timeStamp) {
       String strMessage = null;
       
       if(message instanceof ShortMessage){
           //this.send(decodeMessage((ShortMessage) message));
       }
       
       if (message instanceof SysexMessage){
          strMessage = decodeMessage((SysexMessage) message);
       }
       
       if(strMessage == "NotYet"){
       }else{
       //m_printStream.println(strMessage);
       }
    }
    
    public void decodeMessage(ShortMessage message)
    {
        
    }
    
    public String decodeMessage(SysexMessage message)
    {
        byte[]	abData = message.getData();
        String messageHex = getHexString(abData);
        int lenghtMessage = messageHex.length();
        String	strMessage = null;
        //System.out.println("sysex status: " + message.getStatus());

        if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE)
        {
            strMessage = "Sysex message: F0" + getHexString(abData);
            sysExMessageStr = getHexString(abData);
            sysExBytes = new LinkedList<Byte>();
            for(int i = 0; i < abData.length; i++){
                sysExBytes.addLast(abData[i]);
            }
        }
        else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE)
        {
            //strMessage = "Continued Sysex message F7" + getHexString(abData);
            //strMessage = getHexString(abData);
            sysExMessageStr = sysExMessageStr + getHexString(abData);
            for(int i = 0; i < abData.length; i++){
                sysExBytes.addLast(abData[i]);
            }
        }


        if((messageHex.charAt(lenghtMessage-2)=='F') &&(messageHex.charAt(lenghtMessage-1)=='7')){
            sysexToStop(sysExBytes);
            return sysExMessageStr;
        }
        else return "NotYet";
    }

    
    private void sysexToStop(LinkedList<Byte> message){
        
        //System.out.println(message.size());
        System.out.println("------------------Start of new Data set----------");
        if((message.get(14) & 0xF0) == 0x10){
            System.out.println("Bourdon 8");
        }else {
            System.out.println("Bourdon 8 off");
        }
        
        if((message.get(14) & 0x0F) == 1){
            System.out.println("Viola 8");
        }else{
            System.out.println("Viola 8 off");
        }
        
        
    }
    
    //decrept, ha I got to say that finally
    private static void sysexToStop(String message){
        
        //should check and see if the message lengths are the correct size
        
        if(message.charAt(0) == ' '){
            System.out.println("Principal 16");
        }
        
        
    }
    
    
   
    
    @Override
    public void close() {
    }
    
    private static char hexDigits[] = 
        {'0', '1', '2', '3', 
         '4', '5', '6', '7', 
         '8', '9', 'A', 'B', 
         'C', 'D', 'E', 'F'};
    
    public static String getHexString(byte[] aByte)
    {
        StringBuffer	sbuf = new StringBuffer(aByte.length * 3 + 2);
        for (int i = 0; i < aByte.length; i++)
        {
                sbuf.append(' ');
                sbuf.append(hexDigits[(aByte[i] & 0xF0) >> 4]);
                sbuf.append(hexDigits[aByte[i] & 0x0F]);
                /*byte	bhigh = (byte) ((aByte[i] &  0xf0) >> 4);
                sbuf.append((char) (bhigh > 9 ? bhigh + 'A' - 10: bhigh + '0'));
                byte	blow = (byte) (aByte[i] & 0x0f);
                sbuf.append((char) (blow > 9 ? blow + 'A' - 10: blow + '0'));*/
        }
        return new String(sbuf);
    }
    
}
