package MadTeam;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author FrancoMain
 */
public class Braille {

    private static String total = "";
    private static String totalbits = "";
    final protected static char[] HEXARRAY = "0123456789ABCDEF".toCharArray();

    public static void getBrailleSymbol(int val) {
        char c = 0;
        if (0 <= val && val < 64) {
            String inhex = Integer.toHexString(val);
            if (inhex.length() == 2) {
                total += StringEscapeUtils.unescapeJava("\\u28" + inhex);
            } else {
                total += StringEscapeUtils.unescapeJava("\\u280" + inhex);
            }
            String binary = new StringBuilder(Integer.toBinaryString(val)).reverse().toString();
            int lim = 6 - binary.length();
            for (int i = 0; i < lim; i++) {
                binary += "0";
            }
            totalbits += binary;
        }
    }

    public static String getBinSymbol(String data) {
        try {
            int val = Integer.parseInt(data, 16);
            String inhex = Integer.toHexString(val);
            String binary = "";
            binary = new StringBuilder(Integer.toBinaryString(val)).reverse().toString();
            int lim = 6 - binary.length();
            for (int i = 0; i < lim; i++) {
                binary += "0";
            }
            return binary;
        } catch (Exception e) {
            return "";
        }
    }

    public static String getBinaryString(String text, String line_separator, int max_lines) {
        System.out.println("GETBIN DATA IN:\n" + text);
        String bin = "";
        for (String line : text.split("\n")) {
            for (String numb : StringEscapeUtils.escapeJava(line).split("u28")) {
                bin += getBinSymbol(numb.replace("\\", ""));
                System.out.print(getBinSymbol(numb.replace("\\", "")));
            }
            bin += line_separator;
            System.out.println();
        }
        return bin;
    }

    public static String translate(MainWindow main, String text) {
        String answer = "";
        String s = null;
        String[] lines = text.split("\n");
        try {
            for (String newline : lines) {
                System.out.println("Traduciendo");
                if (newline.equals("")) {
                    answer += "\n";
                    continue;
                }
                Process p=null;
                if(System.getProperty("os.name").equals("Linux")){
                    p = Runtime.getRuntime().exec("lou_translate " +"unicode.dis," + main.DIC);
                }else{
                    String dir = System.getProperty("user.dir") + File.separator + "liblouis" + File.separator + "liblouis-mingw32";
                    String bin = dir + File.separator + "bin" + File.separator + "lou_translate.exe";
                    String dicDir = dir + File.separator + "share" + File.separator + "liblouis" + File.separator + "tables";
                    System.out.println(bin + " " + dicDir + File.separator + "unicode.dis," + dicDir + File.separator + main.DIC);
                    p = Runtime.getRuntime().exec(bin + " " + dicDir + File.separator + "unicode.dis," + dicDir + File.separator + main.DIC);
                }
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
                BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                OutputStream stdin = p.getOutputStream();
                byte[] b = newline.getBytes(Charset.forName("UTF-8"));
                stdin.write(b);
                stdin.flush();
                // read the output from the command
                int linescount = 0;
                stdin.close();
                while ((s = stdInput.readLine()) != null) {
                    byte[] bits = s.getBytes();
                    s = new String(bits, "UTF-8");
                    while (s.length() > main.MAX_CHARS_PER_LINE) {
                        //iteration to check if the line exceeds the maximun chars allowed
                        answer += (s.substring(0, main.MAX_CHARS_PER_LINE));
                        answer += ("\n");
                        s = s.substring(main.MAX_CHARS_PER_LINE);
                        linescount++;
                    }
                    if (s.length() <= main.MAX_CHARS_PER_LINE) {
                        System.out.println(s.length() + "<=" + main.MAX_CHARS_PER_LINE);
                        answer += s;
                        answer += "\n";
                        linescount++;
                    }
                    System.out.println("***");
                    System.out.println(s);
                    System.out.println(StringEscapeUtils.escapeJava(s));
                    System.out.println("***");
                }
                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }
                stdError.close();
                stdInput.close();
            }
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
        return answer;
    }

    private static String saveCommandsFromImg(char separator, String input) {
        for (int index = 9; index >= 2; index--) {
            input = input.replace(new String(new char[index]).replace("\0", separator + ""), separator + "*" + index);
        }
        return input;
    }

    public static String getMadTextFromImage(String imgText, MainWindow main) {
        String out = "";
        String[] lines = imgText.replace(" ", main.BETWEEN_BRAILLE_DOTS).replace("*", "1").split("\n");
        int renglones = 0;
        for (String line : lines) {
            if (!line.contains("1")) {
                out += main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
            } else {
                out += line + main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
            }

            renglones++;
            if (renglones * 3 == main.MAX_LINES_PER_SHEET) {
                out += main.BETWEEN_BRAILLE_SHEETS + "\n";
                renglones = 0;
            }
        }
        return saveCommandsFromImg(main.BETWEEN_BRAILLE_DOTS.charAt(0), out);
    }

    public static String getMadText(String braille, MainWindow main) {
        String symbol = "";
        String[] lines = braille.replace(" ", "\u2800").split("\n");
        String[] binlines = new String[3];
        String out = "";
        int renglones = 0;
        for (String line : lines) {
            System.out.println(line);
            binlines[0] = "";
            binlines[1] = "";
            binlines[2] = "";
            if (line.equals("")) {
                out += main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
                out += main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
                out += main.BETWEEN_BRAILLE_LINES + "\n";
                continue;
            }
            for (String numb : StringEscapeUtils.escapeJava(line).substring(1).split("u28")) {
                symbol = getBinSymbol(numb.replace("\\", ""));
                if (symbol.length() == 6) {
                    binlines[0] += symbol.charAt(0) + main.BETWEEN_BRAILLE_DOTS + symbol.charAt(3) + main.BETWEEN_BRAILLE_CHARS;
                    binlines[1] += symbol.charAt(1) + main.BETWEEN_BRAILLE_DOTS + symbol.charAt(4) + main.BETWEEN_BRAILLE_CHARS;
                    binlines[2] += symbol.charAt(2) + main.BETWEEN_BRAILLE_DOTS + symbol.charAt(5) + main.BETWEEN_BRAILLE_CHARS;
                }
            }
            out += binlines[0] + main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
            out += binlines[1] + main.BETWEEN_BRAILLE_LINES_DOTS + "\n";
            out += binlines[2].substring(0, binlines[2].length() - 1) + main.BETWEEN_BRAILLE_LINES + "\n";
            renglones++;
            if (renglones == main.MAX_LINES_PER_SHEET) {
                out += main.BETWEEN_BRAILLE_SHEETS + "\n";
                renglones = 0;
            }
        }
        out = out.replace("0", "");
        out = out.replace(main.BETWEEN_BRAILLE_CHARS + main.BETWEEN_BRAILLE_LINES_DOTS, main.BETWEEN_BRAILLE_LINES_DOTS);
        out = out.replace(main.BETWEEN_BRAILLE_DOTS + main.BETWEEN_BRAILLE_CHARS, main.BETWEEN_BRAILLE_DOTS_ZERO);
        out = out.replace(main.BETWEEN_BRAILLE_CHARS + main.BETWEEN_BRAILLE_DOTS, main.BETWEEN_BRAILLE_DOTS_ZERO);
        return out;
    }
}
