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

public class BodySalesDataChart extends JPanel {

	private DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

	private CategoryItemRenderer renderer = new BarRenderer();	
	// 카테고리 아이템 런더를 만들어서
	
	private CategoryPlot plot = new CategoryPlot();
	// plot은 부지,땅이라는 뜻..
	// 카테고리를 넣을 부지를 뜻한다 ?
	
	private JFreeChart chart = new JFreeChart(plot);
	// JFreeChart! 자바 free 차트 객체를 만든다
	// 지금까지 데이터 및 render 그리고 x축 y축에 대한 객체를 넣은 plot을 Jfreechart에 넣어준다.

	private ChartPanel chartPanel = new ChartPanel(chart);
	// 데이터세트,renderer,카테고리 Axis, 번호 Axis => plot => JFreeChart

	private CategoryAxis categoryAxis = new CategoryAxis("");
	private NumberAxis NumberAxis = new NumberAxis("매출");

	public BodySalesDataChart() {//생성자
		
		//plot이라는 땅안에 세팅해준다. jframe과 유사(?)
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

	public void monthChart(String year, ArrayList<Integer> intList) {//월별 그래프 메서드

		int count = dataSet.getColumnCount();
		chart.setTitle(year + "년 " + "매출 현황");

		for (int i = 0; i < count; i++) {
			dataSet.removeColumn(0);
		} // 기존 데이터 날리기

		for (int i = 0; i < 12; i++) {
			dataSet.addValue(intList.get(i), BodyFrame.id, String.valueOf(1 + i) + "월");
		} // 12월까지 입력

	}

	public void dayChart(String year, String month, ArrayList<Integer> intList) {//일별 그래프 메서드

		int count = dataSet.getColumnCount();
		chart.setTitle(year + "년" + month + "월 매출현황");

		for (int i = 0; i < count; i++) {
			dataSet.removeColumn(0);
		} // 기존 데이터 날리기

		if (intList.size()==31) {
			for (int i = 0; i < 32; i+=3) {
				if (i==30) {
					dataSet.addValue(intList.get(i), BodyFrame.id, String.valueOf(1 + i) + "일");
					break;
				}
				dataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), BodyFrame.id, String.valueOf(1 + i) + "일");
				
			} // 31일까지 입력
			
		}else if(intList.size()==30){
			for (int i = 0; i < 28; i+=3) {
				dataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), BodyFrame.id, String.valueOf(1 + i) + "일");
			}
		}else if(intList.size()==28){
			for (int i = 0; i < 29; i+=3) {
				if (i==27) {
					dataSet.addValue(intList.get(i), BodyFrame.id, String.valueOf(1 + i) + "일");
					break;
				}
				dataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), BodyFrame.id, String.valueOf(1 + i) + "일");
			}
		}

	}

}//클래스 끝
