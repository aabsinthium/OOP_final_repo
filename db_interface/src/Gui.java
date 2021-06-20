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
    private JCheckBox pe_listener;
    private JCheckBox pb_listener;
    private JCheckBox ps_listener;
    private JCheckBox cr_listener;
    private JCheckBox roe_listener;
    private JTextField gui_open;
    private JTextField gui_close;

    // добвить кнопку сохранения файла

    public Gui() {
        // создание контейнеров для хранения статуса
        String[] checkboxes = {"PE", "PB", "PS", "CR", "ROE"};
        CheckboxStatus status = new CheckboxStatus(checkboxes);

        // создание слушателей для чекбоксов
        pe_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("PE"); // изменения статуса по нажатию на чекбокс
            }
        });
        pb_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("PB");
            }
        });
        ps_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("PS");
            }
        });
        cr_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("CR");
            }
        });
        roe_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("ROE");
            }
        });

        // UNFINISHED
        // формирование и отправка итогового запроса
        request_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // прописать отправку запроса парсеру на вычисление и формирование таблицы
                Request request = new Request(status.getStatus());
                request.formRequest();
                request.getFile();



                /*
                JOptionPane.showMessageDialog(null, pe_status.sendStatus().get("PE"));
                JOptionPane.showMessageDialog(null, pb_status.sendStatus().get("PB"));
                JOptionPane.showMessageDialog(null, ps_status.sendStatus().get("PS"));
                JOptionPane.showMessageDialog(null, cr_status.sendStatus().get("CR"));
                JOptionPane.showMessageDialog(null, roe_status.sendStatus().get("ROE"));
                 */
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Gui");
        frame.setVisible(true);
        frame.setContentPane(new Gui().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
}
