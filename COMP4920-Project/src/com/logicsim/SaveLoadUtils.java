package com.logicsim;

import org.json.*;

import java.io.*;
import java.util.*;

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

        int id = 0;

        for (Component c : comps) {
            JSONObject comp = new JSONObject();
            if(!c.getClass().getSimpleName().equals("Connector")) {
                comp.put("type", c.getClass().getSimpleName());
                c.saveId = id;
                comp.put("id", id++);
                comp.put("x", c.getX());
                comp.put("y", c.getY());
                if (c instanceof Source) {
                    comp.put("sourceOn", ((Source)c).getState());
                }
                compsArr.put(comp);
            }
        }

        for (Component c : comps) {
            JSONObject conn = new JSONObject();
            if(!c.getClass().getSimpleName().equals("Connector")) {

                // Sources don't have outpoints; don't have to worry about them for connectors
                if (c instanceof Output)
                    continue;

                if (c.outPoint.getCon() != null) {
                    conn.put("outID", c.outPoint.getComp().saveId);
                    conn.put("inID", c.outPoint.getCon().getOutput().saveId);

                    if (c.outPoint.getCon().getOutput() instanceof Output)
                        conn.put("inPos", false);
                    else
                        conn.put("inPos", c.outPoint.getCon() == c.outPoint.getCon().getOutput().inPoints.get(1).getCon());


                    connArr.put(conn);
                }
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

        Map map = new HashMap<>();

        JSONArray comps = obj.getJSONArray("comps");

        for(Object o : comps) {
            JSONObject c = (JSONObject) o;
            String type = c.getString("type");
            int id = c.getInt("id");
            int x = c.getInt("x");
            int y = c.getInt("y");

            Component comp = null;

            switch(type) {
                case "Source":
                    comp = new Source(x, y, se);
                    ((Source)comp).setState(c.getBoolean("sourceOn"));
                    break;
                case "And":
                    comp = new And(x, y, se);
                    break;
                case "Or":
                    comp = new Or(x, y, se);
                    break;
                case "Output":
                    comp = new Output(x, y, se);
                    break;
                default:
                    break;
            }
            if (comp != null) se.getComponents().add(comp);
            map.put(id, comp);
        }

        JSONArray connectors = obj.getJSONArray("connectors");
        for(Object o : connectors) {
            JSONObject c = (JSONObject) o;
            int inPos = c.getBoolean("inPos") ? 1 : 0;
            int inID = c.getInt("inID");
            int outID = c.getInt("outID");

            Connector connector = se.buildConnector(((Component)map.get(outID)).outPoint, null);
            ((Component)map.get(outID)).outPoint.setCon(connector);
            connector = se.buildConnector(((Component)map.get(inID)).inPoints.get(inPos), connector);
            ((Component)map.get(inID)).inPoints.get(inPos).setCon(connector);

            se.getComponents().add(connector);
        }
    }
}
