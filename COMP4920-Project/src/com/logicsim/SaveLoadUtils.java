package com.logicsim;

import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Class to save and load json files
 * @author Jayden, Andre, Mitchell, Anthony
 */

public class SaveLoadUtils {

    /**
     * Saves files to "save_"+[0,n] name format in the saves folder
     * @param se == The simulator engine
     */
    public static void save(SimulatorEngine se, File file) {
        ArrayList<Component> comps = se.getComponents();
        JSONObject root = new JSONObject();
        JSONArray compsArr = new JSONArray();
        JSONArray connArr = new JSONArray();

        for (Component c : comps) {
            JSONObject comp = new JSONObject();
            if(!c.getClass().getSimpleName().equals("Connector")) {
                comp.put("type", c.getClass().getSimpleName());
                comp.put("x", c.getX());
                comp.put("y", c.getY());
                compsArr.put(comp);
            } else {
                c = (Connector) c;
                comp.put("x1", ((Connector) c).getStartPoint()[0]);
                comp.put("y1", ((Connector) c).getStartPoint()[1]);
                comp.put("x2", ((Connector) c).getStartPoint()[0]);
                comp.put("y2", ((Connector) c).getStartPoint()[1]);
                connArr.put(comp);
            }
        }

        root.put("comps", compsArr);
        root.put("connectors", connArr);

        writeToSave(file, root.toString(4));
    }

    /**
     * Method to write to a JSON file to be used as a save.
     * @param path == Path to the save folder
     * @param fileName == Name of the save file
     * @param data == Data to be written
     */
    public static void writeToSave(File file, String data) {

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.print(data);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Load file from a specified file name
     * @param se == The simulator engine
     * @param fileName == Name of the save file to be loaded
     */
    public static void load(SimulatorEngine se, File file) {
        // clear workspace before loading
        se.getComponents().clear();

        Reader in = null;
        try {
            in = new FileReader(file);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        JSONTokener jtk = new JSONTokener(in);
        JSONObject obj = new JSONObject(jtk);

        JSONArray comps = obj.getJSONArray("comps");
        for(Object o : comps) {
            JSONObject c = (JSONObject) o;
            String type = c.getString("type");
            int x = c.getInt("x");
            int y = c.getInt("y");
            switch(type) {
                case "Source":
                    se.getComponents().add(new Source(x, y, se));
                    break;
                case "And":
                    se.getComponents().add(new And(x, y, se));
                    break;
                case "Or":
                    se.getComponents().add(new Or(x, y, se));
                    break;
                case "Output":
                    se.getComponents().add(new Output(x, y, se));
                    break;
                default:
                    break;
            }
        }

        JSONArray connectors = obj.getJSONArray("connectors");
        for(Object o : connectors) {
            JSONObject c = (JSONObject) o;
            Connector connector = new Connector();
            int x1 = c.getInt("x1");
            int y1 = c.getInt("y1");
            int x2 = c.getInt("x2");
            int y2 = c.getInt("y2");
            connector.setPoints(x1, y1, x2, y2);
        }
    }
}
