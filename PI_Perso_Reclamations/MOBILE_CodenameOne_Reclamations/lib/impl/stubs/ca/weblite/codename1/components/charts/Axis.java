package ca.weblite.codename1.components.charts;


/**
 * 
 *  @author shannah
 */
public class Axis {

	public Axis() {
	}

	public org.json.me.JSONObject toJS(Object c) {
	}

	public Axis show(Boolean show) {
	}

	public Boolean show() {
	}

	public Axis position(Axis.Position position) {
	}

	public Axis.Position position() {
	}

	public Axis mode(Axis.Mode mode) {
	}

	public Axis.Mode mode() {
	}

	public Axis timeZone(String timeZone) {
	}

	public String timeZone() {
	}

	public Axis color(Color color) {
	}

	public Color color() {
	}

	public Axis tickColor(Color tickColor) {
	}

	public Color tickColor() {
	}

	public Axis font(String font) {
	}

	public String font() {
	}

	public Axis min(Double min) {
	}

	public Double min() {
	}

	public Axis max(Double max) {
	}

	public Double max() {
	}

	public Axis autoscaleMargin(Double autoscaleMargin) {
	}

	public Double autoscaleMargin() {
	}

	public Axis ticks(Integer ticks) {
	}

	public Integer ticks() {
	}

	public Axis tickSize(Double tickSize) {
	}

	public Double tickSize() {
	}

	public Axis tickSizeArr(java.util.List tickSizeArr) {
	}

	public java.util.List tickSizeArr() {
	}

	public Axis ticksArr(java.util.List ticksArr) {
	}

	public java.util.List ticksArr() {
	}

	public Axis minTickSize(Double minTickSize) {
	}

	public Double minTickSize() {
	}

	public Axis labelWidth(Double labelWidth) {
	}

	public Double labelWidth() {
	}

	public Axis labelHeight(Double labelHeight) {
	}

	public Double labelHeight() {
	}

	public Axis reserveSpace(Double reserveSpace) {
	}

	public Double reserveSpace() {
	}

	public Axis tickLength(Integer tickLength) {
	}

	public Integer tickLength() {
	}

	public Axis alignTicksWithAxis(Integer alignTicksWithAxis) {
	}

	public Integer alignTicksWithAxis() {
	}

	public static final class Position {


		public static final Axis.Position BOTTOM;

		public static final Axis.Position TOP;

		public static final Axis.Position LEFT;

		public static final Axis.Position RIGHT;

		public static Axis.Position[] values() {
		}

		public static Axis.Position valueOf(String name) {
		}

		@java.lang.Override
		public String toString() {
		}
	}

	public static final class Mode {


		public static final Axis.Mode TIME;

		public static Axis.Mode[] values() {
		}

		public static Axis.Mode valueOf(String name) {
		}

		public String toString() {
		}
	}

	public static class Tick {


		public Axis.Tick() {
		}

		public Axis.Tick label(String label) {
		}

		public String label() {
		}

		public Axis.Tick value(Double value) {
		}

		public Double value() {
		}
	}
}
