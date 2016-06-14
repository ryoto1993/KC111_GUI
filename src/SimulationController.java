import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by RyotoTomioka on 2016/06/01.
 */
public class SimulationController {
    private static LayoutPane pane;
    private static KC111Canvas canvas;
    public static ArrayList<Light> lights = new ArrayList<>();
    public static ArrayList<Sensor> sensors = new ArrayList<>();
    public static ArrayList<JLabel> luminosityLabels = new ArrayList<>();
    public static ArrayList<JLabel> luminosityPctLabels = new ArrayList<>();
    public static ArrayList<JLabel> luminanceLabels = new ArrayList<>();
    public static ArrayList<JLabel> targetLuminanceLabels = new ArrayList<>();
    public static ArrayList<JSVGCanvas> light_canvas = new ArrayList<>();
    public static ArrayList<JSVGCanvas> sensor_canvas = new ArrayList<>();
    private static int step = 0;
    public static int stepMax;
    public static boolean lightColorChangeMode = false;
    public static JLabel stepLabel = new JLabel();
    public static JSlider animationSpeed = new JSlider(1, 20);
    public static JSlider sl_step = new JSlider();
    private static AnimationThread thread;


    public static void setLight() {
        try {
            File csv = new File("data/light.csv"); // CSVデータファイル
            BufferedReader br = new BufferedReader(new FileReader(csv));

            // 初期化
            lights.clear();
            luminanceLabels.clear();
            luminosityPctLabels.clear();
            light_canvas.clear();

            // 最終行まで読み込む
            String line = "";
            while ((line = br.readLine()) != null) {

                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",");

                while (st.hasMoreTokens()) {
                    // 1行の各要素をタブ区切りで表示
                    lights.add(new Light(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
                    luminosityLabels.add(new JLabel());
                    luminosityPctLabels.add(new JLabel());

                    JSVGCanvas canvas = new JSVGCanvas();
                    canvas.setURI("svg/light_op.svg");
                    canvas.setBounds((lights.get(lights.size()-1).getX()-1)*50, (lights.get(lights.size()-1).getY()-1)*50, 50, 50);
                    light_canvas.add(canvas);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSensor() {
        try {
            File csv = new File("data/sensor.csv"); // CSVデータファイル
            BufferedReader br = new BufferedReader(new FileReader(csv));

            // 最終行まで読み込む
            String line = "";
            while ((line = br.readLine()) != null) {

                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",");

                while (st.hasMoreTokens()) {
                    // 1行の各要素をタブ区切りで表示
                    sensors.add(new Sensor(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken())));
                    luminanceLabels.add(new JLabel());
                    targetLuminanceLabels.add(new JLabel());

                    JSVGCanvas canvas = new JSVGCanvas();
                    canvas.setURI("svg/sensor.svg");
                    canvas.setBounds((int)((sensors.get(sensors.size()-1).getX())*50-15), (int)((sensors.get(sensors.size()-1).getY())*50-15), 30, 30);
                    sensor_canvas.add(canvas);
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLightHistory(String path) {

        for(int i=0; i<lights.size(); i++) {
            lights.get(i).clearHistory();
        }

        try {
            File csv = new File(path); // CSVデータファイル
            BufferedReader br = new BufferedReader(new FileReader(csv));

            // 最終行まで読み込む
            String line = "";
            while ((line = br.readLine()) != null) {

                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",");

                while (st.hasMoreTokens()) {
                    // 1行の各要素をタブ区切りで表示
                    for(int i=0; i<lights.size(); i++) {
                        lights.get(i).appendHistory(Integer.parseInt(st.nextToken()));
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimulationController.stepMax = lights.get(0).getLum_history().size() - 1;
        sl_step.setMaximum(stepMax);

    }

    public static void setLightHistory(File csv) {
        for(int i=0; i<lights.size(); i++) {
            lights.get(i).clearHistory();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(csv));

            // 最終行まで読み込む
            String line = "";
            while ((line = br.readLine()) != null) {

                // 1行をデータの要素に分割
                StringTokenizer st = new StringTokenizer(line, ",");

                while (st.hasMoreTokens()) {
                    // 1行の各要素をタブ区切りで表示
                    for(int i=0; i<lights.size(); i++) {
                        lights.get(i).appendHistory(Integer.parseInt(st.nextToken()));
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SimulationController.stepMax = lights.get(0).getLum_history().size() - 1;
        sl_step.setMaximum(stepMax);

    }

    public static void setLayoutPane(LayoutPane p) {
        pane = p;
    }

    public static void setCanvas(KC111Canvas c) {
        canvas = c;
    }

    public static void setLightLayoutVisible(boolean visible) {
        if(visible) {
            pane.add(pane.light_layout, JLayeredPane.DEFAULT_LAYER);
            pane.setLayer(pane.light_layout, 10);
        } else {
            pane.remove(pane.light_layout);
        }
        pane.repaint();
    }

    public static void setLightPatternVisible(boolean visible) {
        if(visible) {
            pane.add(pane.light_pattern, JLayeredPane.DEFAULT_LAYER);
            pane.setLayer(pane.light_pattern, 100);
        } else {
            pane.remove(pane.light_pattern);
        }
        pane.repaint();
    }

    public static void setLuminosityLabelVisible(boolean visible) {
        if(visible) {
            pane.add(pane.luminosity_layout, JLayeredPane.DEFAULT_LAYER);
            pane.setLayer(pane.luminosity_layout, 110);
        } else {
            pane.remove(pane.luminosity_layout);
        }
        pane.repaint();
    }

    public static void setLuminosityPctLabelVisible(boolean visible) {
        if(visible) {
            pane.add(pane.luminosityPct_layout, JLayeredPane.DEFAULT_LAYER);
            pane.setLayer(pane.luminosityPct_layout, 120);
        } else {
            pane.remove(pane.luminosityPct_layout);
        }
        pane.repaint();
    }

    public static void setSensorLayoutVisible(boolean visible) {
        if(visible) {
            pane.add(pane.sensor_layout, JLayeredPane.DEFAULT_LAYER);
            pane.setLayer(pane.sensor_layout, 120);
        } else {
            pane.remove(pane.sensor_layout);
        }
        pane.repaint();
    }

    public static int getStep() {
        return step;
    }

    public static void setStep(int s) {
        step = s;

        stepLabel.setText("Step " + step);
        sl_step.setValue(step);

        if(lightColorChangeMode) {
            for (int i = 0; i < lights.size(); i++) {
                double lum = (double) lights.get(i).getLum(step);
                double max = Light.MAXLUM;
                luminosityLabels.get(i).setText(Integer.toString(lights.get(i).getLum(step)) + " cd");
                luminosityPctLabels.get(i).setText(Integer.toString((int)(100*(double)lights.get(i).getLum(step) / (double)Light.MAXLUM)) + " %");
                int r = (int) (204 + 38 * (lum / max));
                int g = (int) (204 - 54 * (lum / max));
                int b = (int) (204 - 204 * (lum / max));
                light_canvas.get(i).setBackground(new Color(r, g, b));
            }
        } else {
            for (int i=0; i<lights.size(); i++) {
                luminosityLabels.get(i).setText(Integer.toString(lights.get(i).getLum(step)) + " cd");
                luminosityPctLabels.get(i).setText(Integer.toString((int)(100*(double)lights.get(i).getLum(step) / (double)Light.MAXLUM)) + " %");
                light_canvas.get(i).setBackground(new Color(206, 198, 206));
            }
        }
    }

    public static void updateCanvas() {
        canvas.repaint();
    }

    public static void startAnimation() {
        thread = new AnimationThread();
        thread.start();
    }

    public static void stopAnimation() {
        thread.stopThread();
    }
}

class AnimationThread extends Thread {
    private boolean isActive = true;

    public void run() {
        while(isActive) {
            try {
                sleep(20 * (21-SimulationController.animationSpeed.getValue()));
            } catch (InterruptedException e) {}
            if(SimulationController.getStep()!=SimulationController.stepMax) {
                SimulationController.setStep(SimulationController.getStep() + 1);
                SimulationController.updateCanvas();
            } else {
                isActive = false;
            }
        }
    }

    public void stopThread() {
        isActive = false;
    }
}
