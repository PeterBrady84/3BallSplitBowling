package gui;

import db.MainProgramOperations;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Peter on 06/03/2015.
 */
class HomeTab extends JPanel {

    private final MainProgramOperations progOps;
    private final Date dateSelected;
    private int yr, mon, day;

    public HomeTab(Date date,
                   MainProgramOperations po) {
        System.out.println("Inside : HomeTabGUI");
        this.dateSelected = date;
        this.progOps = po;


        ImageIcon bg = new ImageIcon("src/lib/files/laneBG.png");
        Image laneBg = bg.getImage();

        this.setPreferredSize(new Dimension(780, 300));
        setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        TaskSeriesCollection dataset = designChart();

        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd-MMMM-yy");
        String d = format.format(dateSelected);

        // title, domain axis, range axis, dataset, legend, tooltip, urls
        JFreeChart chart = ChartFactory.createGanttChart("Available Times : " + d, "Lane", "", dataset, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);

        CategoryPlot plot = chart.getCategoryPlot();

        plot.setBackgroundPaint(new Color(255, 255, 255, 0));

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        // The last int controls the transparency of the bars. 0 = invisible, 255 = solid
        Color color = new Color(83, 23, 171,190);
        renderer.setSeriesPaint(0, color);
        plot.setBackgroundImageAlpha(1.0f);
        plot.setBackgroundImage(laneBg);
        this.add(chartPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private TaskSeriesCollection designChart() {
        System.out.println("Inside : designChart() in HomeTabGUI");
        try {
            String date = new java.text.SimpleDateFormat("dd-MM-yyyy").format(dateSelected);
            yr = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(dateSelected));
            mon = Integer.parseInt(new java.text.SimpleDateFormat("MM").format(dateSelected)) - 1;
            day = Integer.parseInt(new java.text.SimpleDateFormat("dd").format(dateSelected));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        TaskSeriesCollection dataset = new TaskSeriesCollection();

        TaskSeries availableTimeSlots = new TaskSeries("availableTimeSlots");
        Task lane1 = new Task("1",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane1);
        lane1.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane2 = new Task("2",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane2);
        lane2.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane3 = new Task("3",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane3);
        lane3.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane4 = new Task("4",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane4);
        lane4.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane5 = new Task("5",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane5);
        lane5.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane6 = new Task("6",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane6);
        lane6.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane7 = new Task("7",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane7);
        lane7.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane8 = new Task("8",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane8);
        lane8.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane9 = new Task("9",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane9);
        lane9.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane10 = new Task("10",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane10);
        lane10.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane11 = new Task("11",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane11);
        lane11.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane12 = new Task("12",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane12);
        lane12.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane13 = new Task("13",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane13);
        lane13.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane14 = new Task("14",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane14);
        lane14.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane15 = new Task("15",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane15);
        lane15.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));

        Task lane16 = new Task("16",
                new GregorianCalendar(yr, mon, day, 10, 30).getTime(),
                new GregorianCalendar(yr, mon, day, 23, 30).getTime());
        availableTimeSlots.add(lane16);
        lane16.addSubtask(new Task("Clear",
                new GregorianCalendar(yr, mon, day, 10, 0).getTime(),
                new GregorianCalendar(yr, mon, day, 10, 0).getTime()));


        ResultSet rSet = progOps.getBookingDataForHomeTab(dateSelected);
        try {
            while (rSet.next()) {
                java.util.Date sDate = rSet.getDate(3);
                String sTime = rSet.getString(4);
                String eTime = rSet.getString(5);
                yr = Integer.parseInt(new java.text.SimpleDateFormat("yyyy").format(sDate));
                mon = Integer.parseInt(new java.text.SimpleDateFormat("MM").format(sDate)) - 1;
                day = Integer.parseInt(new java.text.SimpleDateFormat("dd").format(sDate));
                int startH = Integer.parseInt(sTime.substring(0, 2));
                int startM = Integer.parseInt(sTime.substring(3, 5));
                int endH = Integer.valueOf(eTime.substring(0, 2));
                int endM = Integer.valueOf(sTime.substring(3, 5));

                int i = 0;

                if (rSet.getInt(1) == 1) {
                    lane1.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                    ;
                }
                else if (rSet.getInt(1) == 2) {
                    lane2.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                    ;
                }
                else if (rSet.getInt(1) == 3) {
                    lane3.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                    ;
                }
                else if (rSet.getInt(1) == 4) {
                    lane4.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                    ;
                }
                else if (rSet.getInt(1) == 5) {
                    lane5.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                    ;
                }
                else if (rSet.getInt(1) == 6) {
                    lane6.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 7) {
                    lane7.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 8) {
                    lane8.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 9) {
                    lane9.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 10) {
                    lane10.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 11) {
                    lane11.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 12) {
                    lane12.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 13) {
                    lane13.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 14) {
                    lane14.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 15) {
                    lane15.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
                else if (rSet.getInt(1) == 16) {
                    lane16.addSubtask(new Task("Booking" + (i + 1),
                            new GregorianCalendar(yr, mon, day, startH, startM).getTime(),
                            new GregorianCalendar(yr, mon, day, endH, endM).getTime()));
                }
            }
        }
        catch (Exception e) {
                System.out.println(e);
            }
        dataset.add(availableTimeSlots);
        return dataset;
    }
}