import javax.swing.*;
import java.awt.*;

/**
 * Created by RyotoTomioka on 2016/05/26.
 */
public class MainFrame extends JFrame {
    LayoutPane layoutPane;
    MyMenuBar menubar;

    MainFrame() {
        super();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        this.setTitle("KC111シミュレータ");
        this.setSize(new Dimension(800, 800));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        layoutPane = new LayoutPane(new Dimension(800, 600));
        this.getContentPane().add(layoutPane, BorderLayout.CENTER);
        this.getContentPane().add(new ControlPanel(), BorderLayout.SOUTH);

        menubar = new MyMenuBar();
        this.setJMenuBar(menubar);
        MyMenuBar.mainFrame = this;

        this.setVisible(true);
    }

    public void changeTitle(String file_name) {
        this.setTitle("KC111シミュレータ (" + file_name + ")");
    }
}
