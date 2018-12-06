package org.localftp.intern.natives;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author theboshy
 */
public class CommandManager {

    private static final String UNIX_WIN_ESCAPE = "cmd /c";

    private static String lineResults;

    public static String execCommand(final String lineCommands) {
        lineResults = "";
        /* final Process p = Runtime.getRuntime()
        .exec(Unix.isWindows() ? UNIX_WIN_ESCAPE : "" + lineCommands);*/
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec(lineCommands);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        //end
        return output.toString();
    }

}
