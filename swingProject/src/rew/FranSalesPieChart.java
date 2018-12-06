
package rew;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class FranSalesPieChart extends JPanel {
	
	private DefaultPieDataset dataset = new DefaultPieDataset();
	// 파이 데이터 셋! 메서드로 만들었다!
	
	JFreeChart chart = createChart("dd");
	// JFreeChart! ChartPanel에 들어가기전 최종 차트!
	
	private ChartPanel chartPanel = new ChartPanel(chart);
	//최종 판넬

	public FranSalesPieChart() {
		chartPanel.setBounds(0, 0, 284, 274);
		createDataset();
		add(chartPanel);
		setLayout(null);
		setBounds(0, 0, 284, 274);
	}//생성자 종료

	private void createDataset() {
		// DefaultCategoryDataset bar에서 사용한 데이터 셋!
		// 파이그래프에 전용 데이터 셋이 있는 것 가다..
		dataset.setValue("chi", 60);
		dataset.setValue("B", new Double(27.5));
		dataset.setValue("C", new Double(17.5));
	}//createDataset() : 메서드 종료

	private JFreeChart createChart(String franName) {
		// 다른 예제에 나오는 ChartFactory(차트 공장)을 사용하였다! 여기에는 다양한 차트가 존재
		franName= "aa";
		JFreeChart chart = ChartFactory.createPieChart(franName, // chart title
				dataset, // dataset
				true, // 하위에 항목 사용
				true, // 툴팁 사용 여부
				false // urls를 true ?? 뭐니 ..?
		);
		PiePlot plot = (PiePlot) chart.getPlot();
		//다운 캐스팅 ? chart.getPlot()가 Plot을 반환한다. 즉 .. Plot이 상위 PiePlot은 하위
		plot.setNoDataMessage("입력된 데이터가 없습니다.");
		plot.setNoDataMessageFont(new Font(null, Font.BOLD, 25));
		plot.setExplodePercent("", 1);
		return chart;
	}// createChart() : 메서드 종료

}//클래스 종료
