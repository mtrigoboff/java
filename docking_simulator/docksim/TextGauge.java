package docksim;

// (c) 2000 MLT Software, Inc.  All Rights Reserved.
class TextGauge
        extends java.awt.TextField
        implements Gauge {

    String label;

    TextGauge(String label) {
        this.label = label;
    }

    public void setValue(float value) {
        setText(label + ": " + Float.toString(value));
    }
}
