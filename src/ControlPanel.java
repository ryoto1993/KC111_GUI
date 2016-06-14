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
        this.setLayout(new GridLayout(5, 1));
        this.setPreferredSize(new Dimension(getWidth(), 150));

        // 照明表示/非表示切り替え
        JCheckBox ck_showLight = new JCheckBox("照明");
        ck_showLight.setActionCommand("showLight");
        ck_showLight.addActionListener(this);
        ck_showLight.setSelected(true);
        this.add(ck_showLight);

        // 点灯パターン表示/非表示切り替え
        JCheckBox ck_showPattern = new JCheckBox("照明点灯パターン");
        ck_showPattern.setActionCommand("showPattern");
        ck_showPattern.addActionListener(this);
        ck_showPattern.setSelected(true);
        this.add(ck_showPattern);

        // 照明点灯光度による色の切り替え
        JCheckBox ck_changeColor = new JCheckBox("照明点灯光度による色の切り替え");
        ck_changeColor.setActionCommand("changeColor");
        ck_changeColor.addActionListener(this);
        ck_changeColor.setSelected(false);
        this.add(ck_changeColor);

        // アニメーションパネル
        JPanel animationPanel = new JPanel();
        animationPanel.setPreferredSize(new Dimension(getWidth(), 20));
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
        stepPanel.setPreferredSize(new Dimension(getWidth(), 30));
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
        } else if(e.getActionCommand().equals("btn_play")) {
            SimulationController.startAnimation();
        } else if(e.getActionCommand().equals("btn_stop")) {
            SimulationController.stopAnimation();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider sl = (JSlider)e.getSource();
        SimulationController.setStep(sl.getValue());
        SimulationController.updateCanvas();
    }
}
