package gui;

import controller.TableColumnAdjuster;
import db.MainProgramOperations;
import model.Stock;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

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
 * Created by Peter on 23/03/2015.
 */
public class StaffReportsGUI extends JPanel implements ActionListener {

    private JPanel p1, p2, p1a;
    private JButton barCharts, staffHours, staffDetails,back;
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private AdminTab aTab;
    private MainProgramOperations progOps;
    private String header[] = new String[]{"Staff ID", "First Name", "Bookings"};
    private ResultSet rSet;
    private DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();
    private JTable table;
    private DefaultTableModel model;
    private DefaultPieDataset pieDataset = new DefaultPieDataset();

    public StaffReportsGUI(MainProgramOperations po) {
        this.progOps = po;
        System.out.println("Inside : StaffReportsGUI");
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
        staffDetails = new JButton("Staff Details");
        staffDetails.addActionListener(this);
        barCharts = new JButton("Bar Charts");
        barCharts.addActionListener(this);
        staffHours = new JButton("Staff Hours Worked");
        //staffHours.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);

        p1a.add(staffDetails);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(barCharts);
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

    public void fillTableStaff() {
        System.out.println("Inside : fillTableStaff() in StaffReportGUI");


        try {
            rSet = progOps.getStaffBookings();
            PrintWriter pw = new PrintWriter(new FileWriter("staff.txt"));
            while (rSet.next()){
                int staffId = rSet.getInt(1);
                String fname = rSet.getString(2);
                int numBookings = rSet.getInt(3);

                model.addRow(new Object[]{rSet.getInt(1), rSet.getString(2), rSet.getInt(3)});
                pw.println("----------- " + fname +"--------------------------");
                pw.println("ID Number: " + staffId);
                pw.println("Number of Bookings: " + numBookings +"\n");

            }
            pw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public JPanel fillBarChartStaffBookings() {
        System.out.println("Inside : fillBarChartStaffBookings() in StaffReportsGUI");

        try {
            rSet = progOps.getStaffMembers();
            while (rSet.next()){
                int staffId = rSet.getInt(1);
                String name = rSet.getString(2);
                int numBookings = rSet.getInt(3);

                barDataSet.setValue(numBookings,"Number of Bookings Taken",name);
            }
            JFreeChart chart = ChartFactory.createBarChart("Bookings","Name","Number of Bookings Taken",barDataSet, PlotOrientation.VERTICAL,false,true,false);
//
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.black);
            ChartFrame frame = new ChartFrame("Bar Chart for Number of Bookings Taken by each Staff Member",chart);
            frame.setVisible(true);
            frame.setSize(400,500);

                /* Specify dimensions and quality factor for Pie Chart */
            int width=640;
            int height=480;
            float quality=1; /* Quality factor */
                /* Write Bar Chart as a JPEG file */
            File BarChartStaffBookings=new File("BarChartStaffBookings.png");
            ChartUtilities.saveChartAsJPEG(BarChartStaffBookings, quality, chart, width, height);

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
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
        else if (e.getSource() == staffDetails){

            fillTableStaff();

        }
        else if (e.getSource() == barCharts){

            fillBarChartStaffBookings();

        }
    }
}