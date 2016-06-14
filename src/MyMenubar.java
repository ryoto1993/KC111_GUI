import javax.swing.*;

/**
 * Created by Ryoto on 2016/06/14.
 */
public class MyMenuBar extends JMenuBar {
    JMenu menu_file = new JMenu("File");
    JMenu menu_help = new JMenu("Help");

    public MyMenuBar() {
        super();

        this.add(menu_file);
        this.add(menu_help);

        // ファイル
        JMenuItem item_exit = new JMenuItem("Exit");
        item_exit.addActionListener(e -> {System.exit(2);});
        menu_file.add(item_exit);

        // ヘルプ
    }
}
