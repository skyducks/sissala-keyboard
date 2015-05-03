/**
 * Copyright 2015 Sissala Heritage Foundation
 * Copyright 2015 Yeongdeok Suh <skyducks111@gmail.com>
 */

package org.sissala.keyboard.ui;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import org.sissala.keyboard.SissalaKeyMapper;

import com.sun.glass.events.KeyEvent;

public class SissalaKeyInputMap extends InputMap {
	private boolean isEngToggled;
	private JTextArea textArea;

	public SissalaKeyInputMap(JTextArea uiTextArea) {
		super();
		textArea = uiTextArea;
		this.setParent(textArea.getInputMap(JComponent.WHEN_FOCUSED));

		addToggleKey();
		isEngToggled = false;
		addConvertAll();
	}

	private void addConvertAll() {
		addConvert(KeyStroke.getKeyStroke("typed c"));
		addConvert(KeyStroke.getKeyStroke("typed C"));
		addConvert(KeyStroke.getKeyStroke("typed e"));
		addConvert(KeyStroke.getKeyStroke("typed E"));
		addConvert(KeyStroke.getKeyStroke("typed i"));
		addConvert(KeyStroke.getKeyStroke("typed I"));
		addConvert(KeyStroke.getKeyStroke("typed n"));
		addConvert(KeyStroke.getKeyStroke("typed N"));
		addConvert(KeyStroke.getKeyStroke("typed o"));
		addConvert(KeyStroke.getKeyStroke("typed O"));
		addConvert(KeyStroke.getKeyStroke("typed u"));
		addConvert(KeyStroke.getKeyStroke("typed U"));
	}

	private void addConvert(final KeyStroke keyStroke) {
		super.put(keyStroke, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				textArea.insert(Character
						.toString((char) (isEngToggled ? keyStroke.getKeyChar()
								: SissalaKeyMapper.getConvertedKey(keyStroke
										.getKeyChar()))), textArea
						.getSelectionEnd());
			}
		});
	}

	private void removeConvertAll() {
		super.remove(KeyStroke.getKeyStroke("typed c"));
		super.remove(KeyStroke.getKeyStroke("typed C"));
		super.remove(KeyStroke.getKeyStroke("typed e"));
		super.remove(KeyStroke.getKeyStroke("typed E"));
		super.remove(KeyStroke.getKeyStroke("typed i"));
		super.remove(KeyStroke.getKeyStroke("typed I"));
		super.remove(KeyStroke.getKeyStroke("typed n"));
		super.remove(KeyStroke.getKeyStroke("typed N"));
		super.remove(KeyStroke.getKeyStroke("typed o"));
		super.remove(KeyStroke.getKeyStroke("typed O"));
		super.remove(KeyStroke.getKeyStroke("typed u"));
		super.remove(KeyStroke.getKeyStroke("typed U"));

	}

	private void addToggleKey() {
		super.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK),
				new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						if (isEngToggled) {
							isEngToggled = false;
							addConvertAll();
						} else {
							isEngToggled = true;
							removeConvertAll();
						}
					}
				});
	}
}
