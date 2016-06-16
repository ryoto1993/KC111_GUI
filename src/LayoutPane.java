import javax.swing.*;

import org.apache.batik.swing.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by RyotoTomioka on 2016/05/26.
 */
public class LayoutPane extends JLayeredPane{
    public JPanel light_layout, illuminance_layout, luminosity_layout, luminosityPct_layout, sensor_layout;
    public KC111Canvas light_pattern;
    public JSVGCanvas room_layout, room_grid_layout;

    private static int ROOM_WIDTH = 604, ROOM_HEIGHT = 562;

    LayoutPane(Dimension parent_dimension) {
        SimulationController.setLayoutPane(this);

        this.setPreferredSize(parent_dimension);
        this.setSize(parent_dimension);

        JPanel background = new JPanel();
        background.setBackground(Color.white);
        background.setOpaque(true);
        background.setBounds(0, 0, getWidth(), getHeight());
        this.add(background, JLayeredPane.DEFAULT_LAYER);
        this.setLayer(background, -1000);

        SimulationController.setLight();
        SimulationController.setLightHistory("data/log_a.csv");
        SimulationController.setSensor();

        setLight_layout();
        setRoom_layout();
        setRoomGrid_layout();
        setSensor_layout();
        setLight_Pattern();
        setLuminosity_layout();
        setIlluminanse_layout();

        SimulationController.setLightPatternVisible(true);
        SimulationController.setSensorLayoutVisible(true);
        SimulationController.setLightLayoutVisible(true);
        SimulationController.setLuminosityLabelVisible(true);
        SimulationController.setRoomLayoutVisible(true);
        SimulationController.setRoomGridVisible(true);

        SimulationController.setStep(0);
    }

    public void setRoom_layout() {
        room_layout = new JSVGCanvas();
        room_layout.setURI("svg/KC111_layout.svg");
        room_layout.setBounds(getWidth()/2-(ROOM_WIDTH /2), (getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH, ROOM_HEIGHT);
        room_layout.setOpaque(false);
        room_layout.setBackground(new Color(255,255,255,0));
    }

    public void setRoomGrid_layout() {
        room_grid_layout = new JSVGCanvas();
        room_grid_layout.setURI("svg/KC111_grid.svg");
        room_grid_layout.setBounds(getWidth()/2-(ROOM_WIDTH /2), (getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH, ROOM_HEIGHT);
        room_grid_layout.setOpaque(false);
        room_grid_layout.setBackground(new Color(255,255,255,0));

    }

    public void setLight_layout() {
        light_layout = new JPanel();
        light_layout.setOpaque(false);
        light_layout.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        light_layout.setLayout(null);

        for (JSVGCanvas canvas : SimulationController.light_canvas) {
            light_layout.add(canvas);
        }

    }

    public void setSensor_layout() {
        sensor_layout = new JPanel();
        sensor_layout.setOpaque(false);
        sensor_layout.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        sensor_layout.setLayout(null);

        for (JSVGCanvas canvas : SimulationController.sensor_canvas) {
            sensor_layout.add(canvas);
        }
    }

    public void setIlluminanse_layout() {
        illuminance_layout = new JPanel();
        illuminance_layout.setOpaque(false);
        illuminance_layout.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        illuminance_layout.setLayout(null);

        ArrayList<Sensor> sensors = SimulationController.sensors;
        ArrayList<JLabel> labels = SimulationController.illuminanceLabels;
        for(int i=0; i<sensors.size(); i++) {
            labels.get(i).setFont(new Font("", Font.PLAIN, 18));
            labels.get(i).setOpaque(true);
            labels.get(i).setBackground(new Color(255, 255, 255, 200));
            labels.get(i).setHorizontalAlignment(JLabel.CENTER);
            labels.get(i).setBounds((int)((sensors.get(i).getX())*50-35), (int)((sensors.get(i).getY())*50-12), 70, 25);
            illuminance_layout.add(labels.get(i));
        }
    }

    public void setLuminosity_layout() {
        luminosity_layout = new JPanel();
        luminosity_layout.setOpaque(false);
        luminosity_layout.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        luminosity_layout.setLayout(null);

        luminosityPct_layout = new JPanel();
        luminosityPct_layout.setOpaque(false);
        luminosityPct_layout.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        luminosityPct_layout.setLayout(null);

        ArrayList<Light> lights = SimulationController.lights;
        ArrayList<JLabel> labels = SimulationController.luminosityLabels;
        for(int i=0; i<lights.size(); i++) {
            labels.get(i).setHorizontalAlignment(JLabel.CENTER);
            labels.get(i).setBounds((lights.get(i).getX()-1)*50, (lights.get(i).getY()-1)*50, 50, 50);
            luminosity_layout.add(labels.get(i));
        }

        ArrayList<JLabel> pctLabels = SimulationController.luminosityPctLabels;
        for(int i=0; i<lights.size(); i++) {
            pctLabels.get(i).setHorizontalAlignment(JLabel.CENTER);
            pctLabels.get(i).setBounds((lights.get(i).getX()-1)*50, (lights.get(i).getY()-1)*50, 50, 50);
            luminosityPct_layout.add(pctLabels.get(i));
        }
    }

    public void setLight_Pattern() {
        light_pattern = new KC111Canvas();
        light_pattern.setBounds(2+getWidth()/2-(ROOM_WIDTH /2), 6+(getHeight()-30)/2-(ROOM_HEIGHT /2), ROOM_WIDTH -4, ROOM_HEIGHT -12);
        light_pattern.setOpaque(false);
    }
}
