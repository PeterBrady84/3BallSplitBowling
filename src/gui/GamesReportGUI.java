package gui;

import controller.TableColumnAdjuster;
import db.MainProgramOperations;
import model.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Diarmuid on 24/03/2015.
 */
public class GamesReportGUI extends JPanel implements ActionListener{

    private JPanel p1, p2, p1a;
    private JButton charts, gameStats, back;
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private String header[] = new String[]{"Stock Id", "Shoe Size", "Description", "Quantity"};
    private MainProgramOperations progOps;
    private ResultSet rSet;
    private JTable table;
    private DefaultTableModel model;
    private DefaultPieDataset pieDataset = new DefaultPieDataset();
    private DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();

    public GamesReportGUI(MainProgramOperations po) {
        this.progOps = po;
        System.out.println("Inside : GamesReportGUI");
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        this.setBackground(Color.WHITE);

        p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        p1a = new JPanel();
        p1a.setPreferredSize(new Dimension(180, 200));
        p1a.setLayout(new BoxLayout(p1a, BoxLayout.Y_AXIS));
        p1a.setBackground(Color.WHITE);


        gameStats = new JButton("Games Data");
        gameStats.addActionListener(this);
        charts = new JButton("Charts");
        charts.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);

        p1a.add(gameStats);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(charts);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(back);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);


        p2 = new JPanel();

        model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                comp.setBackground(row % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                return comp;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);

        //fillTable();

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

    public void fillTableMember() {
        System.out.println("Inside : fillTable() in MembershipReportGUI");


        try {
            rSet = progOps.getMember();
            PrintWriter pw = new PrintWriter(new FileWriter("members.txt"));
            while (rSet.next()){
                String fname = rSet.getString(1);
                String lname = rSet.getString(2);
                int visit = rSet.getInt(3);
                String gender = rSet.getString(4);
                model.addRow(new Object[]{rSet.getString(1), rSet.getString(2), rSet.getInt(3), rSet.getString(4)});
                pw.println("First Name: " + fname);
                pw.println("Last Name: " + lname);
                pw.println("Gender: " + gender );
                pw.println("Number of Visits: " + visit +"\n");

            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back){
            //this.setVisible(false);
            AdminTab at = new AdminTab(progOps);
            JPanel admin = at;
            this.removeAll();
            this.add(admin);//Adding to content pane, not to Frame
            repaint();
            printAll(getGraphics());//Extort print all content
        }
    }


}
