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
        // создание контейнеров для хранения статуса
        CheckboxStatus cbstatus_pe = new CheckboxStatus("PE");
        CheckboxStatus cbstatus_pb = new CheckboxStatus("PB");
        CheckboxStatus cbstatus_ps = new CheckboxStatus("PS");
        CheckboxStatus cbstatus_cr = new CheckboxStatus("CR");
        CheckboxStatus cbstatus_roe = new CheckboxStatus("ROE");

        // создание слушателей для чекбоксов
        gui_pe.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cbstatus_pe.changeStatus(); // изменения статуса по нажатию на чекбокс
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

        // UNFINISHED
        // формирование и отправка итогового запроса
        request_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // прописать отправку запроса парсеру на вычисление и формирование таблицы

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

        // UNFINISHED
        // что-то придумать с датой
        // прописать форматирование данных
        // (убрать лишние символы, привести к единому виду, убрать запятые, разделяющие тысячи)
        FinancialsParser financials_parser = new FinancialsParser();
        financials_parser.read("A");

        Map<String, List<String>> data = financials_parser.getParsed();

        for (String key : data.keySet()){
            System.out.println(key);
        }

        for (String element : data.get("Gross Profit")){
            System.out.println(element);
        }
    }
}
