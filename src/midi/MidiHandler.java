package midi;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import main.MainWindow;

/**
 *
 * @author Groowy
 */
    
public class MidiHandler {

    public static ArrayList<MidiDevice> getHardwareDevices() throws MidiUnavailableException {
        ArrayList<MidiDevice> devices = new ArrayList();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            //is this software device? if so, skip this iteration
            if (device instanceof Sequencer || device instanceof Synthesizer) {
                continue;
            }

            devices.add(device);
        }
        return devices;
    }
    
    public static ArrayList<MidiDevice> getInputDevices() throws MidiUnavailableException {
        ArrayList<MidiDevice> devices = new ArrayList();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            if (device.getMaxReceivers() != 0) {
                continue;
            }

            devices.add(device);
        }
        return devices;
    }
    
    public static ArrayList<MidiDevice> getInputHardwareDevices() throws MidiUnavailableException {
        ArrayList<MidiDevice> devices = new ArrayList();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            //is this software device? if so, skip this iteration
            if (device instanceof Sequencer || device instanceof Synthesizer || device.getMaxReceivers() != 0) {
                continue;
            }

            devices.add(device);
        }
        return devices;
    }
    
    public static ArrayList<MidiDevice> getOutputDevices() throws MidiUnavailableException {
        ArrayList<MidiDevice> devices = new ArrayList();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            //is this software device? if so, skip this iteration
            if (device.getMaxTransmitters() != 0) {
                continue;
            }

            devices.add(device);
        }
        return devices;
    }
    
    public static ArrayList<MidiDevice> getOutputHardwareDevices() throws MidiUnavailableException {
        ArrayList<MidiDevice> devices = new ArrayList();
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info info : infos) {
            MidiDevice device = MidiSystem.getMidiDevice(info);
            //is this software device? if so, skip this iteration
            if (device instanceof Sequencer || device instanceof Synthesizer || device.getMaxTransmitters() != 0) {
                continue;
            }

            devices.add(device);
        }
        return devices;
    }

    public static MidiDevice getDefaultDevice() throws MidiUnavailableException{
        return getHardwareDevices().get(0);
    }

    public static void saveRecordingAsMidiFile(String path) throws InvalidMidiDataException, IOException {
        Sequence outSeq = new Sequence(Sequence.SMPTE_25, 50);
        Track midiTrack = outSeq.createTrack();
        long tick = 0;
        
        for(String message : MainWindow.midiRecording) {            
            String[] msgData = message.split(",");
            byte[] data = new byte[msgData.length - 1];
            for (int i = 0; i < msgData.length - 1 /*last value is tick delta*/; i++) {
                data[i] = Byte.valueOf(msgData[i]);
            }
            tick += Long.valueOf(msgData[msgData.length - 1]);
            MidiEvent midiEvt = new MidiEvent(new ShortMessage(data[0], data[1], data[2]),
                    tick);
            midiTrack.add(midiEvt);
        }
        
        MidiSystem.write(outSeq, 0, new File(path));
    }
}
