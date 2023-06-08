package ca.weblite.codename1.components.charts;


/**
 * 
 *  @author shannah
 */
public class ChartBuilder {

	public ChartBuilder() {
	}

	public Chart newPieChart(double[] values, String[] labels) {
	}

	/**
	 *  Creates a new series for use in a bar chart with the specified values.
	 *  The bars will have the magnitudes specified at the xcoordinates of
	 *  the value index+offset.
	 *  @param values
	 *  @param offset
	 *  @return A series.
	 */
	public Series newBarChartSeries(double[] values, double offset) {
	}

	/**
	 *  Creates a new bar chart that shows multiple series' values.
	 *  @param values 2-dimensional array of values.  One array per series.
	 *  @param seriesLabels The labels for the series.  This should be the same length
	 *  as the "values" array.
	 *  @param labels The labels for the ticks on the x-axis.  This should be the same
	 *  length as each value array in the values structure.
	 *  @return The Chart model.
	 */
	public Chart newBarChart(double[][] values, String[] seriesLabels, String[] labels) {
	}

	/**
	 *  Creates a new bar chart model to represent the given values and labels.
	 *  @param values The values of the bars.  The cardinality of this array should
	 *  match the cardinality of the labels array.
	 *  @param labels The labels for the chart.
	 *  @return A basic chart model for this bar chart.
	 */
	public Chart newBarChart(double[] values, String[] labels) {
	}
}
