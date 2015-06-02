/**
 * Copyright 2015 Yeongdeok Suh <skyducks111@gmail.com>
 */

package org.sissala.keyboard.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

class KeyButton extends JButton {
	private boolean isSystemKey = false;
	private boolean isToggleKey = false;
	private boolean isEngToggled = false;

	private JLabel labelN;
	private JLabel labelS;
	private JTextArea uiTextArea;

	private void initKeys(final String string) {
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isSystemKey)
					uiTextArea.insert(string.toLowerCase(),
							uiTextArea.getSelectionEnd());
				else if (isToggleKey)
					toggle();
			}
		});
	}

	public KeyButton(final String string, final JTextArea uiTextArea) {
		super();
		this.setText(string);
		
		initKeys(string);
	}

	public KeyButton(final String stringN, final String stringS,
			final JTextArea uiTextArea) {
		labelN = new JLabel(stringN);
		labelS = new JLabel(stringS);

		this.setLayout(new BorderLayout());
		this.add(BorderLayout.NORTH, labelN);
		this.add(BorderLayout.SOUTH, labelS);

		initKeys(stringS);
	}

	public void setSystemKey(boolean isSystemKey) {
		this.isSystemKey = isSystemKey;
	}

	public void setToggleKey(boolean isToggleKey) {
		this.isToggleKey = isToggleKey;
	}

	private void toggle() {
		if (isEngToggled) {
			isEngToggled = false;
		} else {
			isEngToggled = true;
		}
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		
		if(labelN != null && labelS != null) {
			labelN.setFont(font);
			labelS.setFont(font);
		}
	}
}
