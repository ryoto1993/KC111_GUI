import sun.plugin.net.protocol.jar.CachedJarURLConnection;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RyotoTomioka on 2016/06/01.
 */
public class ControlPanel extends JPanel implements ActionListener, ChangeListener{

    public ControlPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel checkBoxTable = new JPanel(new GridLayout(0,3));
        JPanel roomConfigTable = new JPanel();
        JPanel lightConfigTable = new JPanel();
        JPanel sensorConfigTable = new JPanel();
        roomConfigTable.setLayout(new BoxLayout(roomConfigTable, BoxLayout.PAGE_AXIS));
        roomConfigTable.setBackground(new Color(165,214,167));
        lightConfigTable.setLayout(new BoxLayout(lightConfigTable, BoxLayout.PAGE_AXIS));
        lightConfigTable.setBackground(new Color(144,202,249));
        sensorConfigTable.setLayout(new BoxLayout(sensorConfigTable, BoxLayout.PAGE_AXIS));
        sensorConfigTable.setBackground(new Color(255,204,128));
        checkBoxTable.add(roomConfigTable);
        checkBoxTable.add(lightConfigTable);
        checkBoxTable.add(sensorConfigTable);

        this.add(checkBoxTable);

        // 部屋の枠表示切替
        JCheckBox ck_showRoomLayout = new JCheckBox("部屋の枠を表示");
        ck_showRoomLayout.setActionCommand("showRoom");
        ck_showRoomLayout.addActionListener(this);
        ck_showRoomLayout.setSelected(true);
        roomConfigTable.add(ck_showRoomLayout);

        // グリッド表示切り替え
        JCheckBox ck_showRoomGrid = new JCheckBox("部屋のグリッドを表示");
        ck_showRoomGrid.setActionCommand("showGrid");
        ck_showRoomGrid.addActionListener(this);
        ck_showRoomGrid.setSelected(true);
        roomConfigTable.add(ck_showRoomGrid);

        // 照明表示/非表示切り替え
        JCheckBox ck_showLight = new JCheckBox("照明位置");
        ck_showLight.setActionCommand("showLight");
        ck_showLight.addActionListener(this);
        ck_showLight.setSelected(true);
        lightConfigTable.add(ck_showLight);

        // 点灯パターン表示/非表示切り替え
        JCheckBox ck_showPattern = new JCheckBox("照明点灯パターン");
        ck_showPattern.setActionCommand("showPattern");
        ck_showPattern.addActionListener(this);
        ck_showPattern.setSelected(true);
        lightConfigTable.add(ck_showPattern);

        // 照明点灯光度による色の切り替え
        JCheckBox ck_changeColor = new JCheckBox("照明点灯光度による色の切り替え");
        ck_changeColor.setActionCommand("changeColor");
        ck_changeColor.addActionListener(this);
        ck_changeColor.setSelected(false);
        lightConfigTable.add(ck_changeColor);

        // 照明情報表示切替
        ButtonGroup group_lightInfo = new ButtonGroup();
        JRadioButton radio_light_ill = new JRadioButton("現在光度", true);
        JRadioButton radio_light_pct = new JRadioButton("点灯光度率", false);
        JRadioButton radio_light_nul = new JRadioButton("非表示", false);
        radio_light_ill.addChangeListener(e -> {
            JRadioButton b = (JRadioButton)e.getSource();
            SimulationController.setLuminosityLabelVisible(b.isSelected());
        });
        radio_light_pct.addChangeListener(e -> {
            JRadioButton b = (JRadioButton)e.getSource();
            SimulationController.setLuminosityPctLabelVisible(b.isSelected());
        });
        group_lightInfo.add(radio_light_ill);
        group_lightInfo.add(radio_light_pct);
        group_lightInfo.add(radio_light_nul);
        JPanel panel_lightInfo = new JPanel();
        panel_lightInfo.setLayout(new FlowLayout());
        panel_lightInfo.add(radio_light_ill);
        panel_lightInfo.add(radio_light_pct);
        panel_lightInfo.add(radio_light_nul);
        lightConfigTable.add(panel_lightInfo);

        // センサ位置表示切り替え
        JCheckBox ck_showSensor = new JCheckBox("センサ位置");
        ck_showSensor.setActionCommand("showSensor");
        ck_showSensor.addActionListener(this);
        ck_showSensor.setSelected(true);
        sensorConfigTable.add(ck_showSensor);

        JCheckBox ck_showIlluminance = new JCheckBox("現在照度値");
        SimulationController.checkBox_showIlluminance = ck_showIlluminance;
        ck_showIlluminance.setSelected(false);
        ck_showIlluminance.setEnabled(false);
        ck_showIlluminance.setActionCommand("showIlluminance");
        ck_showIlluminance.addActionListener(this);
        sensorConfigTable.add(ck_showIlluminance);

        // アニメーションパネル
        JPanel animationPanel = new JPanel();
        animationPanel.setLayout(new FlowLayout());

        JButton btn_play = new JButton("Play");
        btn_play.setActionCommand("btn_play");
        btn_play.addActionListener(this);
        animationPanel.add(btn_play);

        JButton btn_stop = new JButton("Stop");
        btn_stop.setActionCommand("btn_stop");
        btn_stop.addActionListener(this);
        animationPanel.add(btn_stop);

        JSlider sl_anim = SimulationController.animationSpeed;
        animationPanel.add(sl_anim);

        this.add(animationPanel);


        // ステップの切り替え
        JPanel stepPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        stepPanel.setLayout(gbl);

        JSlider sl_step = new JSlider(0, SimulationController.stepMax, 0);
        SimulationController.sl_step = sl_step;
        sl_step.addChangeListener(this);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbl.setConstraints(sl_step, gbc);
        stepPanel.add(sl_step);

        JLabel stepLabel = SimulationController.stepLabel;
        stepLabel.setFont(new Font("" ,Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.weightx = 0.1;
        stepLabel.setText("Step 0");
        gbl.setConstraints(stepLabel, gbc);
        stepPanel.add(stepLabel);

        this.add(stepPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("showLight")) {
            JCheckBox tmp = (JCheckBox) e.getSource();
            if(tmp.isSelected()) {
                SimulationController.setLightLayoutVisible(true);
            } else {
                SimulationController.setLightLayoutVisible(false);
            }
        } else if(e.getActionCommand().equals("showPattern")) {
            JCheckBox tmp = (JCheckBox) e.getSource();
            if(tmp.isSelected()) {
                SimulationController.setLightPatternVisible(true);
            } else {
                SimulationController.setLightPatternVisible(false);
            }
        } else if(e.getActionCommand().equals("changeColor")) {
            JCheckBox tmp = (JCheckBox) e.getSource();
            SimulationController.lightColorChangeMode = tmp.isSelected();
        } else if(e.getActionCommand().equals("showSensor")) {
            JCheckBox tmp = (JCheckBox) e.getSource();
            if(tmp.isSelected()) {
                SimulationController.setSensorLayoutVisible(true);
            } else {
                SimulationController.setSensorLayoutVisible(false);
            }
        } else if(e.getActionCommand().equals("btn_play")) {
            SimulationController.startAnimation();
        } else if(e.getActionCommand().equals("btn_stop")) {
            SimulationController.stopAnimation();
        } else if(e.getActionCommand().equals("showIlluminance")) {
            JCheckBox tmp = (JCheckBox)e.getSource();
            SimulationController.setIlluminanceLabelVisible(tmp.isSelected());
        } else if(e.getActionCommand().equals("showRoom")) {
            JCheckBox tmp = (JCheckBox)e.getSource();
            SimulationController.setRoomLayoutVisible(tmp.isSelected());
            SimulationController.updateCanvas();
        } else if(e.getActionCommand().equals("showGrid")) {
            JCheckBox tmp = (JCheckBox)e.getSource();
            SimulationController.setRoomGridVisible(tmp.isSelected());
            SimulationController.updateCanvas();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider sl = (JSlider)e.getSource();
        SimulationController.setStep(sl.getValue());
        SimulationController.updateCanvas();
    }
}
