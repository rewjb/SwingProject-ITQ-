package body;

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

public class B_SalesC_SalesDataChart extends JPanel {

	private DefaultCategoryDataset g_DataSet = new DefaultCategoryDataset();

	private CategoryItemRenderer g_Renderer = new BarRenderer();	
	// 카테고리 아이템 런더를 만들어서
	
	private CategoryPlot g_Plot = new CategoryPlot();
	// plot은 부지,땅이라는 뜻..
	// 카테고리를 넣을 부지를 뜻한다 ?
	
	private JFreeChart g_Chart = new JFreeChart(g_Plot);
	// JFreeChart! 자바 free 차트 객체를 만든다
	// 지금까지 데이터 및 render 그리고 x축 y축에 대한 객체를 넣은 plot을 Jfreechart에 넣어준다.

	private ChartPanel g_ChartPanel = new ChartPanel(g_Chart);
	// 데이터세트,renderer,카테고리 Axis, 번호 Axis => plot => JFreeChart

	private CategoryAxis g_CategoryAxis = new CategoryAxis("");
	private NumberAxis g_NumberAxis = new NumberAxis("매출");

	public B_SalesC_SalesDataChart() {//생성자
		
		//plot이라는 땅안에 세팅해준다. jframe과 유사(?)
		g_Plot.setDataset(g_DataSet);
		g_Plot.setRenderer(g_Renderer);
		g_Plot.setDomainAxis(g_CategoryAxis);
		g_Plot.setRangeAxis(g_NumberAxis);
		g_Plot.setOrientation(PlotOrientation.VERTICAL);
		g_Plot.setDomainGridlinesVisible(true);
		g_Plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		g_ChartPanel.setBounds(0, 0, 450, 275);
		add(g_ChartPanel);
		setLayout(null);
		setBounds(0, 0, 450, 275);

	}

	public void monthChart(String year, ArrayList<Integer> intList) {//월별 그래프 메서드

		int count = g_DataSet.getColumnCount();
		g_Chart.setTitle(year + "년 " + "매출 현황");

		for (int i = 0; i < count; i++) {
			g_DataSet.removeColumn(0);
		} // 기존 데이터 날리기

		for (int i = 0; i < 12; i++) {
			g_DataSet.addValue(intList.get(i), B_Frame.st_G_id, String.valueOf(1 + i) + "월");
		} // 12월까지 입력

	}

	public void dayChart(String year, String month, ArrayList<Integer> intList) {//일별 그래프 메서드
		//일별메서드 1일씩 그래프에 표현하기에는 너무 길기 때문에 2~3일씩 합한 매출을 표현합니다.

		int count = g_DataSet.getColumnCount();
		g_Chart.setTitle(year + "년" + month + "월 매출현황");

		for (int i = 0; i < count; i++) {
			g_DataSet.removeColumn(0);
		} // 기존 데이터 날리기

		if (intList.size()==31) {//31일로 끝나는 달에 대한 조건 
			for (int i = 0; i < 32; i+=3) {
				if (i==30) {
					g_DataSet.addValue(intList.get(i), B_Frame.st_G_id, String.valueOf(1 + i) + "일");
					break;
				}
				g_DataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), B_Frame.st_G_id, String.valueOf(1 + i) + "일");
				
			} // 31일까지 입력
		}else if(intList.size()==30){//30일로 끝나느 달에 대한 조건
			for (int i = 0; i < 28; i+=3) {
				g_DataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), B_Frame.st_G_id, String.valueOf(1 + i) + "일");
			}
		}else if(intList.size()==28){//28일로 끝나는 달에 대한 조건 
			for (int i = 0; i < 29; i+=3) {
				if (i==27) {
					g_DataSet.addValue(intList.get(i), B_Frame.st_G_id, String.valueOf(1 + i) + "일");
					break;
				}
				g_DataSet.addValue(intList.get(i)+intList.get(i+1)+intList.get(i+2), B_Frame.st_G_id, String.valueOf(1 + i) + "일");
			}
		}
	}

}//클래스 끝
