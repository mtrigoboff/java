package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
import java.awt.*;

public abstract class GaugeDisplay
        extends Panel {

    public abstract void setGauge(int gaugeIndex, float value);

    public abstract void zeroValues();
}
