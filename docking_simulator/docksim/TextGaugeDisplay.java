package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public class TextGaugeDisplay
		extends GaugeDisplay {

	private TextGauge[] gauges;

	public TextGaugeDisplay(int nCols, String[] labels) {
		int nGauges = labels.length;

		gauges = new TextGauge[nGauges];
		setLayout(new GridLayout(0, nCols, 8, 8));
		setForeground(Color.black);

		for (int i = 0; i < nGauges; i++) {
			gauges[i] = new TextGauge(labels[i]);
			add(gauges[i]);
		}
	}

	public void setGauge(int gaugeIndex, float value) {
		gauges[gaugeIndex].setValue(value);
	}

	public void zeroValues() {
		for (int i = 0; i < gauges.length; i++) {
			gauges[i].setValue(0);
		}
	}
}
