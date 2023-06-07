package ca.weblite.codename1.components.charts;


/**
 * 
 *  @author shannah
 */
public class LegendOptions {

	public LegendOptions() {
	}

	public LegendOptions show(Boolean show) {
	}

	public Boolean show() {
	}

	public LegendOptions labelBoxBorderColor(Color labelBoxBorderColor) {
	}

	public Color labelBoxBorderColor() {
	}

	public LegendOptions noColumns(Integer noColumns) {
	}

	public Integer noColumns() {
	}

	public LegendOptions position(LegendOptions.Position position) {
	}

	public LegendOptions.Position position() {
	}

	public LegendOptions margin(Integer margin) {
	}

	public Integer margin() {
	}

	public LegendOptions backgroundColor(Color backgroundColor) {
	}

	public Color backgroundColor() {
	}

	public LegendOptions backgroundOpacity(Double backgroundOpacity) {
	}

	public Double backgroundOpacity() {
	}

	public LegendOptions sorted(LegendOptions.Sort sorted) {
	}

	public LegendOptions.Sort sorted() {
	}

	public static final class Position {


		public static final LegendOptions.Position NE;

		public static final LegendOptions.Position NW;

		public static final LegendOptions.Position SE;

		public static final LegendOptions.Position SW;

		public static LegendOptions.Position[] values() {
		}

		public static LegendOptions.Position valueOf(String name) {
		}

		@java.lang.Override
		public String toString() {
		}
	}

	public static final class Sort {


		public static final LegendOptions.Sort ASCENDING;

		public static final LegendOptions.Sort DESCENDING;

		public static final LegendOptions.Sort REVERSE;

		public static LegendOptions.Sort[] values() {
		}

		public static LegendOptions.Sort valueOf(String name) {
		}

		@java.lang.Override
		public String toString() {
		}
	}
}
