import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.*;

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
    private JComboBox ticker_box;
    private JLabel ticker_label;
    private JLabel open_label;
    private JToggleButton multiple_toggle;
    private JToggleButton single_toggle;
    private JLabel close_label;

    public Gui() {
        // создание контейнеров для хранения статуса
        String[] features = {"PE", "PB", "PS", "CR", "ROE"};
        String[] parameters = {"Multiple/Single", "Open year", "Close year", "Ticker"};
        List<String> tickers = new ArrayList<>();
        Parameters status = new Parameters(features, parameters);
        Request request = new Request();

        List<File> files = Arrays.asList(new File("././data/balance-sheet").listFiles());
        Collections.sort(files);

        for(File file : files) {
            ticker_box.addItem(file.getName().substring(0, file.getName().length() - 4));
            tickers.add(file.getName());
        }

        for(int i = 2005; i < 2021; i++){
            open_year.addItem(i);
            close_year.addItem(i);
        }

        // создание слушателей для чекбоксов
        pe_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                status.changeStatus("PE");
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
                status.setParam("Multiple/Single", "Multiple");
                single_toggle.setSelected(!multiple_toggle.isSelected());
            }
        });
        single_toggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                status.setParam("Multiple/Single", "Single");
                multiple_toggle.setSelected(!single_toggle.isSelected());
            }
        });

        // слушатели списков
        ticker_box.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String pv = (String)cb.getSelectedItem();
                status.setParam("Ticker", pv);
            }
        });
        open_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                status.setParam("Open year", (String)cb.getSelectedItem());
            }
        });
        close_year.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                status.setParam("Close year", (String)cb.getSelectedItem());
            }
        });

        // установка селектора в положение Multiple по-умолчанию
        multiple_toggle.setSelected(true);

        // формирование и отправка итогового запроса
        form_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                request.formRequest(status.getStatus(), status.getParams());

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
