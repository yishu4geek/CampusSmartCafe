package edu.scu.oop.proj.view;
import java.awt.BorderLayout;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.UserSession;

import javax.swing.JPanel;


//DietaryPanel displays the Bar Chart of the daily average nutrition consumption 
public class DietaryPanel extends JPanel{
	
	public DietaryPanel() {
		setLayout(new BorderLayout(0, 1)); 	
		CategoryDataset dataset = createDataset();
		 
		final JFreeChart chart = ChartFactory.createBarChart(
		            "Daily Dietary Statistics",         // chart title
		            "Dietary Component",               // domain axis label
		            "Value",                  // range axis label
		            dataset,                  // data
		            PlotOrientation.VERTICAL, // orientation
		            true,                     // include legend
		            true,                     // tooltips?
		            false                     // URLs?
		        );

		        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		        // set the background color for the chart...
		        chart.setBackgroundPaint(Color.white);

		        // get a reference to the plot for further customisation...
		        final CategoryPlot plot = chart.getCategoryPlot();
		        plot.setDomainGridlinePaint(Color.white);
		        plot.setRangeGridlinePaint(Color.white);

		        // set the range axis to display integers only...
		        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		        // disable bar outlines...
		        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		        renderer.setDrawBarOutline(false);
		        
		        // set up gradient paints for series...
		        final GradientPaint gp0 = new GradientPaint(
		            0.0f, 0.0f, new Color(46,204,113),
		            0.0f, 0.0f, new Color(46,204,113)
		        );
		        final GradientPaint gp1 = new GradientPaint(
		            0.0f, 0.0f, new Color(230, 126, 34), 
		            0.0f, 0.0f, new Color(230, 126, 34)
		        );
		        final GradientPaint gp2 = new GradientPaint(
		            0.0f, 0.0f, new Color(155, 89, 182), 
		            0.0f, 0.0f, new Color(155, 89, 182)
		        );
		        renderer.setSeriesPaint(0, gp0);
		        renderer.setSeriesPaint(1, gp1);
		        renderer.setSeriesPaint(2, gp2);

		        final CategoryAxis domainAxis = plot.getDomainAxis();
		        domainAxis.setCategoryLabelPositions(
		            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
		        );
		        
		        ChartPanel chartPanel = new ChartPanel(chart); 
		        add(chartPanel); 
		        // OPTIONAL CUSTOMISATION COMPLETED.
	}
	
	 private CategoryDataset createDataset() {
	        
	        // row keys...
	        final String series1 = "Average";
	        final String series2 = "Average in your gender";
	        final String series3 = "You";

	        // column keys...
	        final String category1 = "Calories";
	        final String category2 = "Fat";
	        final String category3 = "Protein";
	        final String category4 = "Carbs";
	       // final String category5 = "Category 5";

	        // create the dataset...
	        User currUser = UserSession.getInstance().getCurrentUser(); 
	        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	        ArrayList<Integer> grossAvg = DAOFactory.getUserDAO().getGrossAverage(); 
	        ArrayList<Integer> grossAvgInGender = DAOFactory.getUserDAO().getGrossAverageWithGender(currUser.getGender());
	        ArrayList<Integer> selfDietaryProfile = DAOFactory.getUserDAO().getOwnDietaryProfile(currUser);    
	        
	        dataset.addValue(grossAvg.get(0), series1, category1);
	        dataset.addValue(grossAvg.get(1), series1, category2);
	        dataset.addValue(grossAvg.get(2), series1, category3);
	        dataset.addValue(grossAvg.get(3), series1, category4);

	        dataset.addValue(grossAvgInGender.get(0), series2, category1);
	        dataset.addValue(grossAvgInGender.get(1), series2, category2);
	        dataset.addValue(grossAvgInGender.get(2), series2, category3);
	        dataset.addValue(grossAvgInGender.get(3), series2, category4);

	        dataset.addValue(selfDietaryProfile.get(0), series3, category1);
	        dataset.addValue(selfDietaryProfile.get(1), series3, category2);
	        dataset.addValue(selfDietaryProfile.get(2), series3, category3);
	        dataset.addValue(selfDietaryProfile.get(3), series3, category4);
	        
	        return dataset;       
	    }
}
