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
    public static void save(SimulatorEngine se) {

        ArrayList<Component> comps = se.getComponents();
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();

        for (Component c : comps) {
            JSONObject comp = new JSONObject();
            comp.put("type", c.getClass().getSimpleName());
            comp.put("x", c.getX());
            comp.put("y", c.getY());
            arr.put(comp);
        }

        root.put("comps", arr);

        String saveDir = System.getProperty("user.dir") + "/COMP4920-Project/src/com/logicsim/saves/";

        // Get last number used; new save uses last number + 1
        // Files saved in ascending order
        if(new File(saveDir).list().length == 0) {
            writeToSave(saveDir, "save_0.json", root.toString(4));
        } else {
            File lastFile = getLastFile(saveDir);
            int lastNum = getNum(lastFile.getName());
            int newNum = lastNum + 1;
            writeToSave(saveDir, "save_" + newNum + ".json", root.toString(4));
        }

    }

    /**
     * Method to write to a JSON file to be used as a save.
     * @param path == Path to the save folder
     * @param fileName == Name of the save file
     * @param data == Data to be written
     */
    public static void writeToSave(String path, String fileName, String data) {

        File file = new File(path + fileName);

        try (PrintWriter writer = new PrintWriter(path + fileName)) {
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
    public static void load(SimulatorEngine se, String fileName) {

        // clear workspace before loading
        se.getComponents().clear();

        String saveDir = System.getProperty("user.dir") + "/COMP4920-Project/src/com/logicsim/saves/";
        File file = new File(saveDir + fileName);
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

    }

    /**
     * Get the last file saved
     * @param path == Path to the save directory
     * @return The last file numerically
     */
    public static File getLastFile (String path) {

        File savesDir = new File(path);
        File[] saves = savesDir.listFiles();

        Arrays.sort(saves, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                int n1 = getNum(f1.getName());
                int n2 = getNum(f2.getName());
                return n1 - n2;
            }
        });

        return saves[saves.length - 1];
    }

    /**
     * Extract the file number from a file name
     * @param name == The file name of a save
     * @return An integer containing the last number used
     */
    public static int getNum(String name) {

        int i = 0;
        try {
            int start = name.indexOf('_') + 1;
            int end = name.indexOf('.');
            String num = name.substring(start, end);
            i = Integer.parseInt(num);
        } catch (Exception e) {
            i = 0;
        }
        return i;
    }

}
