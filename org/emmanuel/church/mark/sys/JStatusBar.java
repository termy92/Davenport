/*
 * Created on 13-May-2005
 *
 */
package org.emmanuel.church.mark.sys;

/**
 * @author David
 * 
 */
// Class defining a status bar
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import org.emmanuel.church.mark.util.JWait;

class JStatusBar extends JPanel {
	private static final long serialVersionUID = 1;
	private final Font titleFont = new Font("Dialog", Font.BOLD, 12);
	private final Font dataFont = new Font("Dialog", Font.PLAIN, 12);
	// private Font timeFont = new Font("Dialog", Font.ITALIC, 12);

	private final titlePane statusTitle = new titlePane("Status :");
	public dataPane statusData = new dataPane("");

	private final titlePane userTitle = new titlePane("User :");
	private final titlePane hostTitle = new titlePane("Host :");

	private final titlePane jdbcServerTitle = new titlePane("Database :");

	private final dataPane clock = new dataPane("");
	Calendar rightNow = Calendar.getInstance();
	Date date = rightNow.getTime();
	DateFormat df1 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);

	public JStatusBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEtchedBorder());

		add(statusTitle);
		statusData.setPreferredSize(new Dimension(200, 20));
		statusData.setHorizontalAlignment(JLabel.LEFT);
		add(statusData);
		setStatusReady();
		add(new Separator());

		add(userTitle);
		add(new Separator());

		add(hostTitle);
		add(new Separator());

		add(jdbcServerTitle);
		add(new Separator());

		clock.setFont(dataFont);
		add(clock);
		add(new Separator());

		ClockListener clocklistener = new ClockListener();
		new Timer(1000, clocklistener).start();

	}

	public void setStatus(String status) {
		statusData.setText(status);
		JWait.milliSec(1000);
	}

	public void setStatusReady() {
		statusData.setText("Ready");
		JWait.milliSec(1000);
	}

	class Separator extends JLabel {
		private static final long serialVersionUID = 1;

		public Separator() {
			setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			setPreferredSize(new Dimension(1, 23));
		}
	}

	class titlePane extends JLabel {
		private static final long serialVersionUID = 1;

		public titlePane(String text) {
			setBackground(Color.lightGray); // Set background color
			setForeground(Color.black);

			setHorizontalAlignment(CENTER); // Center the pane text
			setBorder(BorderFactory.createCompoundBorder());
			setText(text); // Set the text in the pane
			setFont(titleFont); // Set the fixed font
		}
	}

	class dataPane extends JLabel {
		private static final long serialVersionUID = 1;

		public dataPane(String text) {
			setBackground(Color.lightGray); // Set background color
			setForeground(Color.black);

			setHorizontalAlignment(CENTER); // Center the pane text
			setBorder(BorderFactory.createCompoundBorder());
			setText(text); // Set the text in the pane
			setFont(dataFont); // Set the fixed font
		}
	}

	public class ClockListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			rightNow = Calendar.getInstance();
			date = rightNow.getTime();
			clock.setText(df1.format(date));

		}
	}

}
