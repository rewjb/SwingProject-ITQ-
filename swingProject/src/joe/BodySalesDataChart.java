package joe;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class BodySalesDataChart extends JPanel{

	DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

	CategoryItemRenderer renderer = new BarRenderer();

	CategoryPlot plot = new CategoryPlot();

	JFreeChart chart = new JFreeChart(plot);

	ChartPanel chartPanel = new ChartPanel(chart);

	public BodySalesDataChart() {
		
		
		
		
		
		
		
		
		
		
	}

}
