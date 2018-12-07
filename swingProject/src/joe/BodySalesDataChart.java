package joe;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import DTO_DAO.B_SalesDTO;

public class BodySalesDataChart extends JPanel{

	DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

	CategoryItemRenderer renderer = new BarRenderer();

	CategoryPlot plot = new CategoryPlot();

	JFreeChart chart = new JFreeChart(plot);

	ChartPanel chartPanel = new ChartPanel(chart);

	
	CategoryAxis categoryAxis = new CategoryAxis("");
	NumberAxis NumberAxis = new NumberAxis("매출");
	
	public BodySalesDataChart() {
		
		plot.setDataset(dataSet);
		
		plot.setRenderer(renderer);
		
		plot.setDomainAxis(categoryAxis);
		
		plot.setRangeAxis(NumberAxis);
		
		plot.setOrientation(PlotOrientation.VERTICAL);
		
		plot.setDomainGridlinesVisible(true);
		
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		
		chartPanel.setBounds(0, 0, 450, 275);
		add(chartPanel);
		
		setLayout(null);
		setBounds(0, 0, 450, 275);
		
	}

	public void monthChart(String year, ArrayList<Integer> intList) {
		
		int count = dataSet.getColumnCount();
		chart.setTitle(year + "년 " + "매출 현황");
		
		for (int i = 0; i < count; i++) {
			dataSet.removeColumn(0);
		} // 기존 데이터 날리기
		
		for (int i = 0; i < 12; i++) {
			dataSet.addValue(intList.get(i),BodyFrame.id, String.valueOf(1 + i) + "월");
		} // 12월까지 입력
		
		
		
		
	}
	
	
	
}
