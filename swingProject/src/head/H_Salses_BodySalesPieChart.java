
package head;

import java.awt.Font;
import java.util.ArrayList;

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

public class H_Salses_BodySalesPieChart extends JPanel {
	
	private DefaultPieDataset dataset = new DefaultPieDataset();
	// 파이 데이터 셋! 메서드로 만들었다!
	
	JFreeChart chart  = ChartFactory.createPieChart("", // chart title
			dataset, // dataset
			true, // 하위에 항목 사용
			true, // 툴팁 사용 여부
			false);// urls를 true ?? 뭐니 ..?;
	// JFreeChart! ChartPanel에 들어가기전 최종 차트!
	
	private ChartPanel chartPanel = new ChartPanel(chart);
	//최종 판넬

	public H_Salses_BodySalesPieChart() {
		
		PiePlot plot = (PiePlot) chart.getPlot();
		
		plot.setNoDataMessage("입력된 데이터가 없습니다.");
		plot.setNoDataMessageFont(new Font(null, Font.BOLD, 25));
		plot.setExplodePercent("", 1);
		
		chartPanel.setBounds(0, 0, 284, 274);
		add(chartPanel);
		setLayout(null);
		setBounds(0, 0, 284, 274);
	}//생성자 종료

	public void pieChartShow(String name , ArrayList<Integer> list) {
		chart.setTitle(name);
		// DefaultCategoryDataset bar에서 사용한 데이터 셋!
		// 파이그래프에 전용 데이터 셋이 있는 것 가다..
		
		
		dataset.setValue("chickenF", list.get(0)); // 후라이드 치킨 
		dataset.setValue("chickenH", list.get(1)); // 양념 치킨
		dataset.setValue("chickenS", list.get(2)); // 간장 치킨
	}//createDataset() : 메서드 종료


}//클래스 종료
