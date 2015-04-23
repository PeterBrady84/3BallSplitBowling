package gui;

import db.MainProgramOperations;
import controller.TableColumnAdjuster;
import model.Stock;
import org.jfree.chart.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;

import java.io.File;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Diarmuid on 24/03/2015.
 */
class MembershipReportGUI extends JPanel implements ActionListener {

    private JPanel p3;
    private final JButton barCharts;
    private final JButton back;
    private final JButton pieCharts;
    private ArrayList<Stock> stockList = new ArrayList<>();
    private final MainProgramOperations progOps;
    private ResultSet rSet;
    private final DefaultTableModel model;
    private final DefaultPieDataset pieDataset = new DefaultPieDataset();
    private final DefaultCategoryDataset barDataSet = new DefaultCategoryDataset();

    public MembershipReportGUI(MainProgramOperations po) {
        System.out.println("Inside : MembershipReportGUI");
        this.progOps = po;
        this.setPreferredSize(new Dimension(780, 300));
        this.setLayout(new FlowLayout());
        ((FlowLayout)this.getLayout()).setVgap(0);
        this.setBackground(Color.WHITE);

        JPanel p1 = new JPanel();
        p1.setPreferredSize(new Dimension(200, 290));
        p1.setLayout(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JPanel p1a = new JPanel();
        p1a.setPreferredSize(new Dimension(180, 200));
        p1a.setLayout(new BoxLayout(p1a, BoxLayout.Y_AXIS));
        p1a.setBackground(Color.WHITE);
        barCharts = new JButton("Bar Charts");
        barCharts.addActionListener(this);
        pieCharts = new JButton("Pie Charts");
        pieCharts.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);



        p1a.add(barCharts);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(pieCharts);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(back);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);

        JPanel p2 = new JPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        p2.setBackground(Color.WHITE);
        String[] header = new String[]{"First Name", "Last Name", "Number of Visits", "Gender"};
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

        fillTableMember();

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



    private void fillTableMember() {
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

    private void fillBarChartMember() {
        System.out.println("Inside : fillBarChartMember() in MembershipReportGUI");

        try {
            rSet = progOps.getMember();
            while (rSet.next()){
                String name = rSet.getString(1);
                int numVisits = rSet.getInt(3);

                barDataSet.setValue(numVisits,"Number of Visits", name);
            }
            JFreeChart chart = ChartFactory.createBarChart("Member Visits","Name","Number of Visits",barDataSet,PlotOrientation.VERTICAL,false,true,false);
//
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.black);
            ChartFrame frame = new ChartFrame("Bar Chart for Members Visits",chart);
            frame.setVisible(true);
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);

                /* Specify dimensions and quality factor for Pie Chart */
            int width=640;
            int height=480;
            float quality=1; /* Quality factor */
                /* Write Bar Chart as a JPEG file */
            File BarChart=new File("SQL2BarChart.png");
            ChartUtilities.saveChartAsJPEG(BarChart, quality, chart,width,height);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void fillPieChartMemberGender() {
        System.out.println("Inside : FillPieChartMemberGender() in MembershipReportGUI");

        try {
            rSet = progOps.getMemberGender();
            PrintWriter pw = new PrintWriter(new FileWriter("genderBalance.txt"));
            while (rSet.next()){
                //////////////for text file//////////////
                String genderType = rSet.getString(1);
                int total = rSet.getInt(2);
                ////For DataSet////////////////////////
                String gender = rSet.getString(1);
                int genderTotal = rSet.getInt(2);
                pw.println("First Name: " + genderType);
                pw.println("Last Name: " + total);
                pieDataset.setValue(gender, genderTotal); //Convert data source from table to Pie Chart Data Source
            }
                /* Create Logical Chart */
        JFreeChart PieChartObject=ChartFactory.createPieChart("Male V's Female Members - Pie Chart", pieDataset, true, true, false);
                /* Close JDBC specific objects */
        rSet.close();

            ////////////////Create a frame to display pie chart///////////
            ChartFrame frame = new ChartFrame("Pie Chart for Gender Balance",PieChartObject);
            frame.setVisible(true);
            frame.setSize(400, 500);

                /* Specify dimensions and quality factor for Pie Chart */
        int width=640;
        int height=480;
        float quality=1; /* Quality factor */
                /* Write Pie Chart as a JPEG file */
        File PieChart=new File("SQL2PieChart.png");
        ChartUtilities.saveChartAsJPEG(PieChart, quality, PieChartObject,width,height);
        } catch (Exception e) {
            System.out.println(e);
        }

    }





    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Inside : ActionPerformed() in MembershipReportsGUI");
        if (e.getSource() == back){
            //this.setVisible(false);
            this.removeAll();
            this.add(new AdminTab(progOps));//Adding to content pane, not to Frame
            this.revalidate();
            this.repaint();
            printAll(getGraphics());//Extort print all content
        }
        else if (e.getSource() == barCharts) {

            fillBarChartMember();

        }
        else if (e.getSource() == pieCharts) {

            fillPieChartMemberGender();

        }
    }





}
