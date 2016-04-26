/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsysextomidi;

import java.io.PrintStream;
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
    String sysExMessage;
    
    public myMidiReceiver(PrintStream printStream)
    {
        m_printStream = printStream;
    }
    
    @Override
    public void send(MidiMessage message, long timeStamp) {
       String strMessage = null;
       
       if (message instanceof SysexMessage){
          strMessage = decodeMessage((SysexMessage) message);
       }
       
       if(strMessage == "NotYet"){
           return;
       }else{
       m_printStream.println(strMessage);
       }
    }
    
    public String decodeMessage(SysexMessage message)
    {
        byte[]	abData = message.getData();
        int lenghtMessage = abData.length;
        String	strMessage = null;
        // System.out.println("sysex status: " + message.getStatus());
        if (message.getStatus() == SysexMessage.SYSTEM_EXCLUSIVE)
        {
            sysExMessage = getHexString(abData);
            strMessage = "Sysex message: F0" + getHexString(abData);
        }
        else if (message.getStatus() == SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE)
        {
                //strMessage = "Continued Sysex message F7" + getHexString(abData);
                strMessage = getHexString(abData);
                sysExMessage += getHexString(abData);
        }


        if((abData[lenghtMessage-1]==15) &&(abData[lenghtMessage-2]==7)){
            return sysExMessage;
        }
        else return "NotYet";
    }

    
    private static String sysexToStop(String message){
        
        
        
        return "Stop Showing Red!"
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
