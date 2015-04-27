package gui;

import controller.TableColumnAdjuster;
import db.MainProgramOperations;
import model.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Diarmuid on 24/03/2015.
 */
class FinancialReportsGUI extends JPanel implements ActionListener{

    private final JButton back,piechart;
    private final MainProgramOperations progOps;
    private final DefaultTableModel model;
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();

    public FinancialReportsGUI(MainProgramOperations po) {
        System.out.println("Inside : FinancialReportsGUI");
        this.progOps = po;
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JPanel p1a = new JPanel();
        p1a.setPreferredSize(new Dimension(180, 200));
        p1a.setLayout(new BoxLayout(p1a, BoxLayout.Y_AXIS));
        p1a.setBackground(Color.WHITE);

        piechart = new JButton("Pie Chart");
        piechart.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);


        p1a.add(piechart);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(back);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);
        //p1a.add(add(Box.createVerticalStrut(20)));


        JPanel p2 = new JPanel();

        String[] header = new String[]{"Payment ID", "Booking ID", "Deposit", "Total Price", "Fully Paid", "Payment Method"};
        model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return comp;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);

        fillTableFinancialReport();

        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnAdjuster tca = new TableColumnAdjuster(table);
        tca.adjustColumns();

        table.setColumnSelectionAllowed(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane sp = new JScrollPane();
        sp.setViewportView(table);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(500, 290));
        sp.setBackground(Color.WHITE);
        p2.add(sp);
        add(p2, BorderLayout.EAST);
    }

    public void fillTableFinancialReport() {
        System.out.println("Inside : fillTableFinancialRport() in FinancialReportGUI");


        try {
            ResultSet rSet = progOps.getPaymentDetails();
            PrintWriter pw = new PrintWriter(new FileWriter("src/reports/financialReport.txt"));
            while (rSet.next()){
                int paymentId = rSet.getInt(1);
                int bookingId = rSet.getInt(2);
                double deposit = rSet.getDouble(3);
                double totalPrice = rSet.getDouble(4);
                String fullyPaid = rSet.getString(5);
                String paymentMethod = rSet.getString(6);

                model.addRow(new Object[]{rSet.getInt(1), rSet.getInt(2), rSet.getDouble(3), rSet.getDouble(4), rSet.getString(5), rSet.getString(6)});
                pw.println("Payment ID: " + paymentId);
                pw.println("Booking ID: " + bookingId);
                pw.println("Deposit: " + deposit );
                pw.println("Total Price: " + totalPrice);
                pw.println("Fully Paid: " + fullyPaid );
                pw.println("Payment Method: " + paymentMethod +"\n");

            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void fillPieChartPaymentType() {
        System.out.println("Inside : FillPieChartPaymentType() in MembershipReportGUI");

        try {
            ResultSet rSet = progOps.getPaymentType();
            PrintWriter pw = new PrintWriter(new FileWriter("src/reports/paymentType.txt"));
            while (rSet.next()){

                String paymentType = rSet.getString(1);
                int total = rSet.getInt(2);

                pw.println("Payment Method: " + paymentType);
                pw.println("Total: " + total);
                pieDataset.setValue(paymentType, total); //Convert data source from table to Pie Chart Data Source
            }
                /* Create Logical Chart */
            JFreeChart PieChartObject= ChartFactory.createPieChart("Methods of Payment - Pie Chart", pieDataset, true, true, false);
                /* Close JDBC specific objects */
            rSet.close();

            ////////////////Create a frame to display pie chart///////////
            ChartFrame frame = new ChartFrame("Pie Chart for Payment Method",PieChartObject);
            frame.setVisible(true);
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);

                /* Specify dimensions and quality factor for Pie Chart */
            int width=640;
            int height=480;
            float quality=1; /* Quality factor */
                /* Write Pie Chart as a JPEG file */
            File PieChart=new File("src/reports/SQL2PieChartPaymentMethod.png");
            ChartUtilities.saveChartAsJPEG(PieChart, quality, PieChartObject, width, height);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : actionPerformed() in FinancialReportsGUI");
        if (e.getSource() == back){
            //this.setVisible(false);
            this.removeAll();
            this.add(new AdminTab(progOps));//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics());//Extort print all content
        }
        else if(e.getSource() == piechart){
            fillPieChartPaymentType();
        }

    }
}
