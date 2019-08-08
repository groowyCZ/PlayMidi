package midi;


import java.util.ArrayList;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

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

}
