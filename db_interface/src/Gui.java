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
        CheckboxStatus pe_status = new CheckboxStatus("PE");
        CheckboxStatus pb_status = new CheckboxStatus("PB");
        CheckboxStatus ps_status = new CheckboxStatus("PS");
        CheckboxStatus cr_status = new CheckboxStatus("CR");
        CheckboxStatus roe_status = new CheckboxStatus("ROE");

        // создание слушателей для чекбоксов
        pe_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                pe_status.changeStatus(); // изменения статуса по нажатию на чекбокс
            }
        });
        pb_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                pb_status.changeStatus();
            }
        });
        ps_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ps_status.changeStatus();
            }
        });
        cr_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                cr_status.changeStatus();
            }
        });
        roe_listener.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                roe_status.changeStatus();
            }
        });

        // UNFINISHED
        // формирование и отправка итогового запроса
        request_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinancialsParser financials_parser = new FinancialsParser();
                YieldParser yield_parser = new YieldParser();

                financials_parser.read("AAPL");
                yield_parser.read("AAPL");

                DateMerger merger = new DateMerger(financials_parser.getParsed(), yield_parser.getParsed());
                Map<String, List<String>> data = merger.merge();


                // прописать отправку запроса парсеру на вычисление и формирование таблицы

                if(pe_status.getStatus()){
                    PEColumn pec = new PEColumn(data, "(?<=\\$).[^\"]+");
                    List<String> pe = pec.calculateValue();

                    for(int i = 0; i < data.get("Date").size(); i += 1){
                        System.out.println(
                            data.get("Date").get(i) + "   " +
                            pe.get(i));
                    }
                }


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
