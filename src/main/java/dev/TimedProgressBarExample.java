package dev;

import javax.swing.JFrame;

/**
 * @author DeV
 */
public class TimedProgressBarExample {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 150);
		frame.setLayout(null);
		frame.setVisible(true);

//	------------------THIS IS WHAT YOU NEED TO DO--------------------------------
		TimedProgressBar timedProgressBar = TimedProgressBar.getProgressBar(10_000);
//		TimedEvent finishEvent = () -> finishTask(timedProgressBar);
		frame.add(timedProgressBar.getJprogressBar());
//		timedProgressBar.setOnFinish(finishEvent);
		timedProgressBar.startProgress();

//		--------------------------------------------------

	}

	private static void finishTask(TimedProgressBar timedProgressBar) {
		timedProgressBar.hide();
	}
}
