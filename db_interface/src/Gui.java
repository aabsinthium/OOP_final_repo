import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Gui {
    private JPanel panel1;
    private JCheckBox pe_listener;
    private JCheckBox pb_listener;
    private JCheckBox ps_listener;
    private JCheckBox cr_listener;
    private JCheckBox roe_listener;
    private JButton form_button;
    private JButton send_button;
    private JComboBox open_year;
    private JComboBox close_year;
    private JComboBox comboBox1;
    private JLabel ticker_label;
    private JLabel period_label;
    private JToggleButton multiple_toggle;
    private JToggleButton single_toggle;

    // добвить кнопку сохранения файла

    public Gui() {
        // создание контейнеров для хранения статуса
        String[] checkboxes = {"PE", "PB", "PS", "CR", "ROE", "Multiple", "Single"};
        CheckboxStatus status = new CheckboxStatus(checkboxes);
        Request request = new Request();

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

        // селектор формы (файл с одной компанией / файл со всеми)
        multiple_toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.changeStatus("Multiple");
                single_toggle.setSelected(!single_toggle.isSelected());
            }
        });
        single_toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.changeStatus("Single");
                multiple_toggle.setSelected(!multiple_toggle.isSelected());
            }
        });

        // установка селектора в положение Multiple по-умолчанию
        multiple_toggle.setSelected(true);

        // формирование и отправка итогового запроса
        form_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request.formRequest(status.getStatus());

            }
        });
        send_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request.getFile();
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
