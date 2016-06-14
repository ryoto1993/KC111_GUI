import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by RyotoTomioka on 2016/06/01.
 */
public class KC111Canvas extends JPanel {
    public static int OVAL_SIZE = 150;

    public KC111Canvas() {
        SimulationController.setCanvas(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<Light> lights = SimulationController.lights;
        int max = lights.get(0).MAXLUM;
        int step = SimulationController.getStep();


        for(int i = 0; i< lights.size(); i++) {
            int size = OVAL_SIZE * lights.get(i).getLum(step)/max;

            g2.setColor(new Color(255, 141, 0, 193));
            g2.fillOval((lights.get(i).getX()-1)*50 + 25 - size/2,
                    (lights.get(i).getY()-1)*50 + 25 - size/2,
                    size,
                    size);
            g2.setColor(new Color(255, 14, 0, 206));
            g2.drawOval((lights.get(i).getX()-1)*50 + 25 - size/2,
                    (lights.get(i).getY()-1)*50 + 25 - size/2,
                    size,
                    size);
        }
    }
}
