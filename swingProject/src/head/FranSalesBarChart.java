
package head;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.editor.DefaultLogAxisEditor;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class FranSalesBarChart extends JPanel {

	DefaultCategoryDataset dataSet;

	CategoryItemRenderer renderer = new BarRenderer();
	// 카테고리 아이템 런더를 만들어서

	CategoryPlot plot = new CategoryPlot();
	// plot은 부지,땅이라는 뜻..
	// 카테고리를 넣을 부지를 뜻한다 ?

	JFreeChart chart = new JFreeChart(plot);
	// JFreeChart! 자바 free 차트 객체를 만든다
	// 지금까지 데이터 및 render 그리고 x축 y축에 대한 객체를 넣은 plot을 Jfreechart에 넣어준다.

	ChartPanel chartPanel = new ChartPanel(chart);
	// 데이터세트,renderer,카테고리 Axis, 번호 Axis => plot => JFreeChart

	CategoryAxis categoryAxis = new CategoryAxis("");
	NumberAxis NumberAxis = new NumberAxis("매출");

	public FranSalesBarChart() {
		// create the first dataset...
		// franName 은 가맹점의 이름 , sort에서 0은 월별 , 1은 일별

		// 데이터 세트를 선언!
		// 데이터 세트 안에
//      dataset1.addValue(값, "차트 분류", "x 구분자");

//		plot.setDataset(dataSet);
//		// 땅에다가 데이터 세트를 넣는다?

		plot.setRenderer(renderer);
		// 차트 내부 내용만 사라진다.
		// 차트에 renderer를 넣어준다.

		// 현재까지 plot(부지)에 데이터와 renderer를 넣어주었다.

		plot.setDomainAxis(categoryAxis);
		// 기능은 자세히는 모르나 .. 없으면 에러가 발생
		// x축 기준 객체인듯하다 .. Domain:범위

		plot.setRangeAxis(NumberAxis);
		// y축 기준 객체 넣기

		plot.setOrientation(PlotOrientation.VERTICAL);
		// 차트를 수직으로 표현하는 메서드
//      plot.setRangeGridlinesVisible(true);
		// 이건 무슨 기능인지 모르겠다..
		plot.setDomainGridlinesVisible(true);
		// y축 점선 표시

//        plot.setDatasetRenderingOrder(null);
		// 이거 기능이 뭔지 모르겠다..

		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		// x축 카테고리를 얼마나 기울일지 정하는 메서드

		// chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setBounds(0, 0, 450, 275);
		add(chartPanel);

		setLayout(null);
		setBounds(0, 0, 450, 275);
	}// 생성자 종료

	public void monthChartShow(String franName, String year, ArrayList<Integer> intList) {

		dataSet = new DefaultCategoryDataset();

		chart.setTitle(franName + "점 " + year + "년 " + "매출 현황");

		if (franName == null) {
			franName = "미지정";
		}

		for (int i = 0; i < 12; i++) {
			dataSet.addValue(intList.get(i), franName, String.valueOf(1 + i) + "월");
		} // 12월까지 입력

		plot.setDataset(dataSet);
	}// 가맹점 월별 데이터 보여주기 메서드

	public void dayChartShow(String franName, String year, String month, ArrayList<Integer> intList) {

		int count = intList.size();
		int tem = 0;
		int sum = 0;
		ArrayList<Integer> tempList = new ArrayList<>();
		dataSet = new DefaultCategoryDataset();

		for (int i = 0; i < count; i++) {
			sum += intList.get(i);
			System.out.println((i + 1) + "일 매출 :" + intList.get(i));
			++tem;
			if (tem == 4) {
				tempList.add(sum);
				tem = 0;
				sum = 0;
			}
		}

		if (count > 28) {
			tempList.add(sum);
		}

		tem = tempList.size();

		chart.setTitle(franName + "점 " + year + "년 " + month + "월 " + "매출 현황");
		for (int i = 0; i < tem; i++) {
			dataSet.addValue(tempList.get(i), franName, String.valueOf(1 + 4 * i + "일"));
		}
		plot.setDataset(dataSet);
	}// 가맹점 월별 데이터 보여주기 메서드

}// 클래스 종료