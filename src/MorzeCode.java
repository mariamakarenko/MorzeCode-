import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Scanner;

public class MorzeCode {

    public static HashMap<String, String> letterToCode = new HashMap<>();

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        mapLettersToCode(letterToCode);
        String morseCode = printCode();

        codeToSound(morseCode);
    }

    public static String printCode() {

        System.out.println("Введите, пожалуйста, текст: ");
        String message = scanner.nextLine();

        String morseCode = "";
        for (int i = 0; i < message.length(); i++) {
            for (String letter : letterToCode.keySet()) {
                if (letter.equals(message.substring(i, i + 1).toLowerCase())) {
                    morseCode += letterToCode.get(letter) + "   ";
                }
            }
        }
        System.out.println(morseCode);
        return morseCode;
    }

    public static void codeToSound(String morseCode) throws InterruptedException, MidiUnavailableException, InvalidMidiDataException {
        Synthesizer synth = MidiSystem.getSynthesizer();

        for (int i = 0; i < morseCode.length(); i++) {
            if (morseCode.charAt(i) == '.') {
                synth.open();
                Receiver synthRcvr = synth.getReceiver();
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 4, 60, 93);
                synthRcvr.send(msg, 25);
                synthRcvr.close();
                Thread.sleep(250);
            }
            else if(morseCode.charAt(i) == '-'){
                synth.open();
                Receiver synthRcvr = synth.getReceiver();
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 4, 75, 93);
                synthRcvr.send(msg, 60);
                synthRcvr.close();
                Thread.sleep(250);
            }
            else{
                Thread.sleep(50);
            }
        }
    }

    public static void mapLettersToCode(HashMap<String, String> letterToCode) {
        letterToCode.put("а", ".-");
        letterToCode.put("б", "-...");
        letterToCode.put("ц", "-.-.");
        letterToCode.put("д", "-..");
        letterToCode.put("е", ".");
        letterToCode.put("ф", "..-.");
        letterToCode.put("г", "--.");
        letterToCode.put("х", "....");
        letterToCode.put("и", "..");
        letterToCode.put("й", ".---");
        letterToCode.put("к", "-.-");
        letterToCode.put("л", ".-..");
        letterToCode.put("м", "--");
        letterToCode.put("н", "-.");
        letterToCode.put("о", "---");
        letterToCode.put("п", ".--.");
        letterToCode.put("щ", "--.-");
        letterToCode.put("р", ".-.");
        letterToCode.put("с", "...");
        letterToCode.put("т", "-");
        letterToCode.put("у", "..-");
        letterToCode.put("ж", "...-");
        letterToCode.put("в", ".--");
        letterToCode.put("ь", "-..-");
        letterToCode.put("ы", "-.--");
        letterToCode.put("з", "--..");
        letterToCode.put("ч", "---.");
        letterToCode.put("ш", "----");
        letterToCode.put("ю", "..--");
        letterToCode.put("я", ".-.-");
        letterToCode.put("1", ".---");
        letterToCode.put("2", "..--");
        letterToCode.put("3", "...--");
        letterToCode.put("4", "....--");
        letterToCode.put("5", ".....");
        letterToCode.put("6", "-....");
        letterToCode.put("7", "--...");
        letterToCode.put("8", "---..");
        letterToCode.put("9", "----.");
        letterToCode.put("0", "-----");
        letterToCode.put(".", "......");
        letterToCode.put(",", ".-.-.-");
        letterToCode.put(";", "-.-.-.");
        letterToCode.put(":", "---...");
        letterToCode.put("?", "..--..");
        letterToCode.put("!", "--..--");
    }

}