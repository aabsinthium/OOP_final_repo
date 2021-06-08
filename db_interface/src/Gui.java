import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Map;

public class Gui {
    private JPanel panel1;
    private JButton request_button;
    private JCheckBox gui_pe;
    private JCheckBox gui_pb;
    private JCheckBox gui_ps;
    private JCheckBox gui_cr;
    private JCheckBox gui_roe;
    private JTextField gui_open;
    private JTextField gui_close;

    public Gui() {
        // создание
        CBStatus cbstatus_pe = new CBStatus("PE");
        CBStatus cbstatus_pb = new CBStatus("PB");
        CBStatus cbstatus_ps = new CBStatus("PS");
        CBStatus cbstatus_cr = new CBStatus("CR");
        CBStatus cbstatus_roe = new CBStatus("ROE");


        gui_pe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_pe.changeStatus();
            }
        });

        gui_pb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_pb.changeStatus();
            }
        });

        gui_ps.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_ps.changeStatus();
            }
        });

        gui_cr.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_cr.changeStatus();
            }
        });

        gui_roe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_roe.changeStatus();
            }
        });


        request_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, cbstatus_pe.sendStatus().get("PE"));
                JOptionPane.showMessageDialog(null, cbstatus_pb.sendStatus().get("PB"));
                JOptionPane.showMessageDialog(null, cbstatus_ps.sendStatus().get("PS"));
                JOptionPane.showMessageDialog(null, cbstatus_cr.sendStatus().get("CR"));
                JOptionPane.showMessageDialog(null, cbstatus_roe.sendStatus().get("ROE"));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gui");
        frame.setVisible(true);
        frame.setContentPane(new Gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();


        Parser parser = new Parser();
        parser.read("A.csv");

        Map<String, List<String>> data = parser.getParsed();

        for (String key : data.keySet()){
            System.out.println(key);
        }

        for (String element : data.get("Date")){
            System.out.println(element);
        }
    }
}
