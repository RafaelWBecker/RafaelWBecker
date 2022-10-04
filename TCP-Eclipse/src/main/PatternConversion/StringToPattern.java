package main.PatternConversion;

import main.Constants.Constants;
import main.Constants.JFugueInstruments;
import org.jfugue.pattern.Pattern;

public final class StringToPattern {

    public static Pattern convert(String input){
        PatternBuilder patternBuilder = new PatternBuilder();
        char lastLetter = ' ';
        for (char letter: input.toCharArray()) {
            switch(letter){
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'G':
                    patternBuilder.addNote(letter);
                    break;
                case ' ':
                    patternBuilder.doubleVolume();
                    break;
                case '!':
                    patternBuilder.changeInstrument(JFugueInstruments.AGOGO);
                    break;
                case 'O':
                case 'o':
                case 'I':
                case 'i':
                case 'U':
                case 'u':
                    patternBuilder.changeInstrument(JFugueInstruments.HARPSICHORD);
                    break;
                case ';':
                    patternBuilder.changeInstrument(JFugueInstruments.PAN_FLUTE);
                    break;
                case ',':
                    patternBuilder.changeInstrument(JFugueInstruments.CHURCH_ORGAN);
                    break;
                case '\n':
                    patternBuilder.changeInstrument(JFugueInstruments.TUBULAR_BELLS);
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    patternBuilder.addValToInstrumentNumber(letter);
                    break;
                case '?':
                    patternBuilder.nextOctave();
                    break;
                default:
                    if(lastLetter >= Constants.FIRST_NOTE && lastLetter <= Constants.LAST_NOTE){
                        patternBuilder.addNote(lastLetter);
                    }else {
                        patternBuilder.addPause();
                    }
            }

            lastLetter = letter;
        }

        return patternBuilder.getOutput();
    }
}
