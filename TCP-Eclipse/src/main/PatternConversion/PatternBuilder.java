package main.PatternConversion;

import main.Constants.Constants;
import org.jfugue.pattern.Pattern;

public class PatternBuilder {

    private int instrumentNumber;
    private int octave;
    private int volume;
    private StringBuilder stringPattern;

    public PatternBuilder() {
        this.instrumentNumber = Constants.DEFAULT_INSTRUMENT;
        this.octave = Constants.DEFAULT_OCTAVE;
        this.volume = Constants.DEFAULT_VOLUME;
        this.stringPattern = new StringBuilder(":CON(7, " + volume + ") ");
    }

    public Pattern getOutput() {
        return new Pattern(stringPattern.toString());
    }

    public boolean addNote(Character note){
        if(note < 'A' || note > 'G'){
            return false;
        }
        stringPattern.append(note).append(octave).append(' ');
        return true;
    }

    public void doubleVolume(){
        volume *= 2;
        if(volume > 128){
            volume = Constants.DEFAULT_VOLUME;
        }
        stringPattern.append(":CON(7, ").append(volume).append(") ");
    }

    public void changeInstrument(int instrument) {
        stringPattern.append('I').append(instrument).append(' ');
        instrumentNumber = instrument;
    }

    public void addValToInstrumentNumber(char numberChar){
        instrumentNumber += Character.getNumericValue(numberChar);
        stringPattern.append("I").append(instrumentNumber).append(' ');
    }

    public void nextOctave(){
        if(octave < 10){
            octave++;
        }else {
            octave = Constants.DEFAULT_OCTAVE;
        }
    }

    public void addPause(){
        stringPattern.append('R').append(' ');
    }
}
