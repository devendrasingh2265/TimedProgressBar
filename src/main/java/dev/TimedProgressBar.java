package dev;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JProgressBar;

/**
 * @author DeV
 *
 */
public final class TimedProgressBar {
	private TimedProgressBar() {

	}

	/**
	 * When you already have a JprogressBar and want to use it as TimedProgressBar
	 * 
	 * @param jProgressBar
	 */
	private TimedProgressBar(JProgressBar jProgressBar) {
		this.jProgressBar = jProgressBar;
	}

	/**
	 * Refresh rate of TimedProgrssBar in <b>MilliSeconds</b> <br>
	 * <b>{@code Default Value: 200}</b>
	 */
	private int refreshRate = 200;

	private final Timer timer = new Timer(true);
	private JProgressBar jProgressBar;
	private int maxTimeOut;

	private TimedEvent finishEvent;

	private TimedEvent updateEvent;

	/**
	 * @param maxTimeOut the maximum end time value of the Progress bar
	 *                   <b>(Milliseconds)</b>
	 * @return
	 */
	public static final TimedProgressBar getProgressBar(int maxTimeOut) {
		return getProgressBar(0, maxTimeOut, 40, 40, 160, 30, 0, true);
	}

	/**
	 * @param minTime            the minimum value of the progress bar
	 *                           <b>(Milliseconds)</b>
	 * @param maxTimeOut         the maximum end time value of the Progress bar
	 *                           <b>(Milliseconds)</b>
	 * @param x                  the new <i>x</i>-coordinate of JProgressBar
	 *                           component
	 * @param y                  the new <i>y</i>-coordinate of JProgressBar
	 *                           component
	 * @param width              the new <code>width</code> of JProgressBar
	 *                           component
	 * @param height             the new <code>height</code> of JProgressBar
	 *                           component
	 * @param initialValue       initial Value of Progress Bar
	 * @param showProgressString same as
	 *                           {@link javax.swing.JProgressBar#setStringPainted(boolean)}
	 * @return {@link TimedProgressBar}
	 */
	public static TimedProgressBar getProgressBar(int minTime, int maxTimeOut, int x, int y, int width, int height,
			int initialValue, boolean showProgressString) {
		TimedProgressBar timedProgressBar = new TimedProgressBar();
		timedProgressBar.maxTimeOut = maxTimeOut;
		timedProgressBar.jProgressBar = new JProgressBar(minTime, maxTimeOut);
		timedProgressBar.jProgressBar.setBounds(x, y, width, height);
		timedProgressBar.jProgressBar.setValue(initialValue);
		timedProgressBar.jProgressBar.setStringPainted(showProgressString);
		return timedProgressBar;
	}

	/**
	 * When you already have a JprogressBar and want to use it as TimedProgressBar
	 * 
	 * @param jProgressBar existing JProgressBar
	 * @param maxTimeOut   the maximum end time value of the Progress bar
	 *                     <b>(Milliseconds)</b>
	 * @return
	 */
	public static TimedProgressBar getProgressBar(JProgressBar jProgressBar, int maxTimeOut) {
		TimedProgressBar timedProgressBar = new TimedProgressBar(jProgressBar);
		timedProgressBar.maxTimeOut = maxTimeOut;
		jProgressBar.setMaximum(maxTimeOut);
		return timedProgressBar;
	}

	/**
	 * Start Progress on given refresh rate
	 * 
	 * @param refreshRate <b>(MilliSeconds)</b>
	 */
	public void startProgress(int refreshRate) {
		this.refreshRate = refreshRate;
		startProgress();
	}

	/**
	 * start Progress with default refresh rate
	 * 
	 * @see #refreshRate
	 */
	public void startProgress() {
		reValidate();
		timer.scheduleAtFixedRate(new Progressable(this), 0, refreshRate);
	}

	private void reValidate() {
		reset();
		jProgressBar.setVisible(true);

	}

	public JProgressBar getJprogressBar() {
		return jProgressBar;
	}

	private void stopProgrees() {
		timer.cancel();
		hide();
		if (finishEvent != null)
			finishEvent.perform();
	}

	public int getMaxTimeOut() {
		return maxTimeOut;
	}

	private void refresh() {
		jProgressBar.setValue(jProgressBar.getValue() + refreshRate);
		if (jProgressBar.getValue() >= maxTimeOut) {
			stopProgrees();
		} else {
			if (updateEvent != null)
				updateEvent.perform();
		}
	}

	private class Progressable extends TimerTask {
		private TimedProgressBar timedProgressBar;

		public Progressable(TimedProgressBar timedProgressBar) {
			this.timedProgressBar = timedProgressBar;
		}

		@Override
		public void run() {
			timedProgressBar.refresh();
		}

	}

	/**
	 * Event triggered on Progressbar Completion.
	 * 
	 * @param timedEvent
	 */
	public void setOnFinish(TimedEvent timedEvent) {
		this.finishEvent = timedEvent;
	}

	public void setOnUpdate(TimedEvent timedEvent) {
		this.updateEvent = timedEvent;
	}

	private void reset() {
		jProgressBar.setValue(0);
	}

	public void hide() {
		reset();
		jProgressBar.setVisible(false);

	}
}
