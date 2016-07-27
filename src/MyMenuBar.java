import javax.swing.*;
import java.io.File;

/**
 * Created by Ryoto on 2016/06/14.
 */
public class MyMenuBar extends JMenuBar {
    JMenu menu_file = new JMenu("File");
    JMenu menu_help = new JMenu("Help");
    static MainFrame mainFrame;

    public MyMenuBar() {
        super();

        this.add(menu_file);
        this.add(menu_help);

        // ファイル
        JMenuItem item_open_ill = new JMenuItem("Load Illuminance history");
        item_open_ill.addActionListener(e2 -> {
            File csv = new File("data/light.csv"); // CSVデータファイル
            JFileChooser fileChooser = new JFileChooser(csv);
            int selected;
            selected = fileChooser.showOpenDialog(this);
            if(selected == JFileChooser.APPROVE_OPTION) {
                SimulationController.setSensorHistory(fileChooser.getSelectedFile());
                mainFrame.changeTitle(fileChooser.getSelectedFile().getName());
                SimulationController.isIlluminanceLoaded = true;
                SimulationController.setIlluminanceLabelVisible(true);
                SimulationController.checkBox_showIlluminance.setEnabled(true);
                SimulationController.checkBox_showIlluminance.setSelected(true);
            }
        });
        menu_file.add(item_open_ill);

        JMenuItem item_open_lum = new JMenuItem("Load Luminosity history");
        item_open_lum.addActionListener(e1 -> {
            File csv = new File("data/light.csv"); // CSVデータファイル
            JFileChooser fileChooser = new JFileChooser(csv);
            int selected;
            selected = fileChooser.showOpenDialog(this);
            if(selected == JFileChooser.APPROVE_OPTION) {
                SimulationController.setLightHistory(fileChooser.getSelectedFile());
                mainFrame.changeTitle(fileChooser.getSelectedFile().getName());
            }
        });
        menu_file.add(item_open_lum);

        JMenuItem item_exit = new JMenuItem("Exit");
        item_exit.addActionListener(e -> {System.exit(2);});
        menu_file.add(item_exit);

        // ヘルプ
    }
}
