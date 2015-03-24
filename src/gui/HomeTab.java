package gui;

import db.MainProgramOperations;
import model.Booking;
import lib.ModifyDateAndTime;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.Month;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Peter on 06/03/2015.
 */
public class HomeTab extends JPanel {

    private ArrayList<Booking> bookingList = new ArrayList<Booking>();
    static String months[] = {null , "JANUARY" , "FEBRUARY" , "MARCH" , "APRIL", "MAY", "JUNE", "JULY",
            "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    private ResultSet rSet;
    private MainProgramOperations progOps;

    public HomeTab(ArrayList<Booking> b, MainProgramOperations po) {
        System.out.println("Inside : HomeTabGUI");
        this.progOps = po;
        this.bookingList = b;

        this.setPreferredSize(new Dimension(780, 300));
        setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        TaskSeriesCollection dataset = designChart();

        // title, domain axis, range axis, dataset, legend, tooltip, urls
        JFreeChart chart = ChartFactory.createGanttChart("Available Times", "Lane", "", dataset, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // set the color (r,g,b)
        Color color = new Color(2, 171, 33);
        renderer.setSeriesPaint(0, color);

        this.add(chartPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    public TaskSeriesCollection designChart() {

        TaskSeriesCollection dataset = new TaskSeriesCollection();

        TaskSeries availableTimeSlots = new TaskSeries("availableTimeSlots");
        Task lane1 = new Task("1",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane1);

        Task lane2 = new Task("2",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane2);

        Task lane3 = new Task("3",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane3);

        Task lane4 = new Task("4",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane4);

        Task lane5 = new Task("5",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane5);

        Task lane6 = new Task("6",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane6);

        Task lane7 = new Task("7",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane7);

        Task lane8 = new Task("8",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane8);

        Task lane9 = new Task("9",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane9);

        Task lane10 = new Task("10",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane10);

        Task lane11 = new Task("11",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane11);

        Task lane12 = new Task("12",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane12);

        Task lane13 = new Task("13",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane13);

        Task lane14 = new Task("14",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane14);

        Task lane15 = new Task("15",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane15);

        Task lane16 = new Task("16",
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 10, 30).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 20, 23, 30).getTime());
        availableTimeSlots.add(lane16);


        ModifyDateAndTime mdt = new ModifyDateAndTime();
        for (int i = 0; i < bookingList.size(); i++) {
            try {
                String fromDate = bookingList.get(i).getFromDateTime();
                String toDate = bookingList.get(i).getToDateTime();
                int year = Integer.parseInt(mdt.getYear(fromDate));
                int month = Integer.parseInt(mdt.getMonth(fromDate)) - 1;
                int date = Integer.parseInt(mdt.getDate(fromDate));
                int startH = Integer.parseInt(mdt.getHour(fromDate));
                int startM = Integer.parseInt(mdt.getMinute(fromDate));
                int endH = Integer.parseInt(mdt.getHour(toDate));
                int endM = Integer.parseInt(mdt.getMinute(toDate));

                if (bookingList.get(i).getLaneId() == 1) {
                    lane1.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, startM).getTime(),
                            new GregorianCalendar(year, month, date, endH, endM).getTime()));
                }
                else if (bookingList.get(i).getLaneId() == 2) {
                    lane2.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, 30).getTime(),
                            new GregorianCalendar(year, month, date, endH, 30).getTime()));
                }
                else if (bookingList.get(i).getLaneId() == 3) {
                    lane3.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, 30).getTime(),
                            new GregorianCalendar(year, month, date, endH, 30).getTime()));
                }
                else if (bookingList.get(i).getLaneId() == 4) {
                    lane4.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, 30).getTime(),
                            new GregorianCalendar(year, month, date, endH, 30).getTime()));
                }
                else if (bookingList.get(i).getLaneId() == 5) {
                    lane5.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, 30).getTime(),
                            new GregorianCalendar(year, month, date, endH, 30).getTime()));
                }
                else if (bookingList.get(i).getLaneId() == 6) {
                    lane6.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(year, month, date, startH, 30).getTime(),
                            new GregorianCalendar(year, month, date, endH, 30).getTime()));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        dataset.add(availableTimeSlots);
        return dataset;
    }
}


        /**lane1.addSubtask(new Task("Booking 1",
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 12, 00).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 16, 00).getTime()));

        lane2.addSubtask(new Task("Booking 2",
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 10, 00).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 11, 00).getTime()));

        lane2.addSubtask(new Task("Booking 3",
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 14, 00).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 15, 00).getTime()));

        lane2.addSubtask(new Task("Booking 4",
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 16, 00).getTime(),
                new GregorianCalendar(2015, Month.FEBRUARY, 1, 18, 00).getTime()));

        dataset.add(availableTimeSlots);
        return dataset;**/




    /**public HomeTab(MainProgramOperations po) {
        System.out.println("Inside : HomeTabGUI");
        this.progOps = po;

        this.setPreferredSize(new Dimension(780, 300));
        setLayout(new FlowLayout());

        String[] cols = {"12pm", "1pm", "2pm", "3pm", "4pm", "5pm",
                "6pm", "7pm", "8pm", "9pm", "10pm", "11pm", "12am"};
        int numRows = 16;
        model = new DefaultTableModel(numRows, cols.length);
        model.setColumnIdentifiers(cols);
        table = new JTable(model);
        list = new DefaultListModel();
        for (int i = 0; i < numRows; i++) {
            list.addElement("Lane " + (i + 1));
        }
        table.setRowHeight(16);
        table.setFillsViewportHeight(true);
        table.setAutoCreateColumnsFromModel(false);
        table.setRowSelectionAllowed(false);
        rowHeader = new JList(list);
        LookAndFeel.installColorsAndFont
                (rowHeader, "TableHeader.background", "TableHeader.foreground",
                        "TableHeader.font");
        rowHeader.setFixedCellHeight(table.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer());
        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(table);
        sp.setRowHeaderView(rowHeader);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        sp.setPreferredSize(new Dimension(780, 280));
        p1 = new JPanel();
        p1.add(sp);

        this.add(p1, BorderLayout.EAST);
    }**/