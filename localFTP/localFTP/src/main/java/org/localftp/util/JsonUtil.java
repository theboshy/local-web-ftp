package org.localftp.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author theboshy
 */
public class JsonUtil {

    public static JSONObject fromJsonFile(File source) {
        Object obj = null;
        try {
            JSONParser parser = new JSONParser();
            try {
                obj = parser.parse(new FileReader(source));
            } catch (IOException | ParseException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return (JSONObject) obj;
    }

}
