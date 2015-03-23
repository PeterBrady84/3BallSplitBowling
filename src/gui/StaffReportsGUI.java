package gui;

import db.MainProgramOperations;
import model.Stock;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Peter on 23/03/2015.
 */
public class StaffReportsGUI extends JPanel {

    private JPanel p1, p2, p1a;
    private JButton staffBookings, staffHours, staffSomething;
    private ArrayList<Stock> stockList = new ArrayList<Stock>();
    private String header[] = new String[]{"Stock Id", "Shoe Size", "Description", "Quantity"};
    private JTextField stockId, stockName;

    public StaffReportsGUI() {
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
        staffBookings = new JButton("Staff Bookings Taken");
        //staffBookings.addActionListener(this);
        staffHours = new JButton("Staff Hours Worked");
        //staffHours.addActionListener(this);
        staffSomething = new JButton("Staff Something Else");
        //staffSomething.addActionListener(this);

        p1a.add(staffBookings);
        p1a.add(add(Box.createVerticalStrut(20)));
        p1a.add(staffHours);
        p1.add(p1a, BorderLayout.SOUTH);
        add(p1, BorderLayout.WEST);


        p2 = new JPanel();
        p2 = createDemoPanel();
        p2.setPreferredSize(new Dimension(520, 295));
        //p2.setBackground(Color.BLUE);

        add(p2, BorderLayout.EAST);
        this.setVisible(true);

    }


    private static PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Staff One", new Double(43.2));
        dataset.setValue("Staff Two", new Double(10.0));
        dataset.setValue("Staff Three", new Double(27.5));
        dataset.setValue("Staff Four", new Double(17.5));
        dataset.setValue("Staff Five", new Double(11.0));
        dataset.setValue("Staff Six", new Double(19.4));
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     * @return A chart.
     */
    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart Demo 1",  // chart title
                dataset,             // data
                true,               // include legend
                true,
                false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
}