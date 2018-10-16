package com.logicsim;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import static java.lang.Math.toIntExact;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Class to create in initialize a window for the simulator to run in
 * @author Jayden, Andre, Mitchell, Anthony
 */

public class Toolbar extends JToolBar implements ActionListener {

    SimulatorCanvas s;

    /**
     * Initializes toolbar.
     * @param sim == The simulator canvas it works on.
     */
    public Toolbar(SimulatorCanvas sim) {
        s = sim;

        setRollover(true);

        JButton saveButton = new JButton("Save");
        saveButton.setActionCommand("save");
        saveButton.setToolTipText("save the current workspace");
        saveButton.addActionListener(this);

        JButton loadButton = new JButton("Load");
        loadButton.setActionCommand("load");
        loadButton.setToolTipText("load into current workspace");
        loadButton.addActionListener(this);

        JButton clearButton = new JButton("Clear");
        clearButton.setActionCommand("clear");
        clearButton.setToolTipText("clear the current workspace");
        clearButton.addActionListener(this);

        add(saveButton);
        add(loadButton);
        add(clearButton);
    }

    /**
     * Used to decide what happens when toolbar button is clicked.
     * @param e == An event representing some type of action
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // TODO: Move this stuff to another class
        if (command.equals("save")) {
            System.out.println("Saving workspace.");
            ArrayList<Component> comps = s.getSimEngine().getComponents();
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            for (Component c : comps) {
                JSONObject comp = new JSONObject();
                comp.put("type", c.getClass().getSimpleName());
                comp.put("x", c.getX());
                comp.put("y", c.getY());
                arr.add(comp);
            }
            root.put("comps", arr);

            File file = new File(System.getProperty("user.dir") + "/COMP4920-Project/src/com/logicsim/saves/test_save");

            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(root.toJSONString());

            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            }

        } else if (command.equals("load")) {
            s.getSimEngine().getComponents().clear();
            JSONParser parser = new JSONParser();

            try {
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/COMP4920-Project/src/com/logicsim/saves/test_save"));

                JSONObject jsonObj = (JSONObject) obj;
                JSONArray comps = (JSONArray) jsonObj.get("comps");
                for(Object o: comps) {
                    JSONObject comp = (JSONObject) o;
                    String type = (String) comp.get("type");
                    int x = Math.toIntExact((Long)comp.get("x"));
                    int y = Math.toIntExact((Long)comp.get("y"));
                    SimulatorEngine se = s.getSimEngine();
                    Component newComp = null;
                    switch(type) {
                        case "Source":
                            newComp = new Source(x, y, se);
                            se.getComponents().add(newComp);
                            break;
                        case "And":
                            newComp = new And(x, y, se);
                            se.getComponents().add(newComp);
                            break;
                        case "Or":
                            newComp = new Or(x, y, se);
                            se.getComponents().add(newComp);
                            break;
                        case "Output":
                            newComp = new Output(x, y, se);
                            se.getComponents().add(newComp);
                            break;
                        default:
                            break;
                    }

                }

            } catch (FileNotFoundException ex) {
                System.out.println(e.toString());
            } catch (ParseException ex) {
                System.out.println(ex.toString());
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
        } else if (command.equals("clear")) {
            System.out.println("Clearing workspace.");
            s.getSimEngine().getComponents().clear();
        }
    }

}
