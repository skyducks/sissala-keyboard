/**
 * Copyright 2015 Sissala Heritage Foundation
 * Copyright 2015 Yeongdeok Suh <skyducks111@gmail.com>
 */

package org.sissala.keyboard.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SissalaEditorFrame extends JFrame implements ActionListener {
	private static final String TITLE = "Sissala Keyboard";
	private static final String VERSION = "0.4";
	private static final String COPYRIGHT = "© 2015 Sissala Heritage Foundation";
	private static final String OPENSOURCE = "This software is under LGPL 2.1 License";
	private static final String GITHUB_URL = "https://github.com/skyducks/sissala-keyboard";
	private static final int DEFAULT_WINDOW_WIDTH = 720;
	private static final int DEFAULT_WINDOW_HEIGHT = 350;
	//for Windows
	private static final Font DEFAULT_FONT = new Font("MS Serif", Font.PLAIN, 15);

	private static JTextArea uiTextArea;

	public void start() {
		uiTextArea = new JTextArea();
		this.setTitle(TITLE);
		this.setBounds(200, 200, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// this.setIconImage(new ImageIcon(this.getClass().getClassLoader()
		// .getResource("SHF_Logo_16.png").getFile()).getImage());
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"resources\\SHF_Logo_16.png"));

		this.setJMenuBar(createMenuBar());

		uiTextArea.setPreferredSize(new Dimension(DEFAULT_WINDOW_WIDTH, 100));
		uiTextArea.setFont(DEFAULT_FONT);
		uiTextArea.setLineWrap(true);
		uiTextArea.setEditable(true);
		this.add(new JScrollPane(uiTextArea), BorderLayout.NORTH);
		uiTextArea.setInputMap(JComponent.WHEN_FOCUSED, new SissalaKeyInputMap(
				uiTextArea));

		this.add(createKeyboardPanel(), BorderLayout.SOUTH);
		pack();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.updateComponentTreeUI(this);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");

		final JMenuItem helpMenuItem = new JMenuItem("About Sissala Keyboard");
		helpMenuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final int DEFAULT_POPUP_WIDTH = 550;
				final int DEFAULT_POPUP_HEIGHT = 300;
				JFrame popupFrame = new JFrame(helpMenuItem.getName());
				popupFrame.setBounds(500, 250, DEFAULT_POPUP_WIDTH,
						DEFAULT_POPUP_HEIGHT);
				popupFrame.setVisible(true);
				popupFrame.setResizable(false);
				popupFrame.setLayout(new GridBagLayout());

				JPanel logoPanel = new JPanel();
				logoPanel.setPreferredSize(new Dimension(
						(int) (DEFAULT_POPUP_WIDTH*0.4),
						DEFAULT_POPUP_HEIGHT-25));
				logoPanel.setLayout(new BorderLayout());
				JPanel contentsPanel = new JPanel();
				contentsPanel.setPreferredSize(new Dimension(
						(int) (DEFAULT_POPUP_WIDTH*0.6),
						DEFAULT_POPUP_HEIGHT-25));

				//for logoPanel
				JLabel imageLabel = new JLabel(loadSHFLogoImageIcon(logoPanel.getPreferredSize()));
				logoPanel.add(BorderLayout.CENTER, imageLabel);

				//for contentsPanel
				JPanel titlePanel = new JPanel();
				titlePanel.setPreferredSize(new Dimension(contentsPanel.getPreferredSize().width-10, contentsPanel.getPreferredSize().height/4));
				titlePanel.setLayout(new BorderLayout());
				JLabel titleLabel = new JLabel(TITLE);
				titleLabel.setFont(new Font("Arial", Font.BOLD, 23));
				titlePanel.add(BorderLayout.SOUTH, titleLabel);

				JLabel githubLabel = new JLabel(GITHUB_URL);
				githubLabel.setFont(new Font("Arial", Font.ITALIC, 12));
				githubLabel.setForeground(Color.BLUE.darker());
				Map attr = githubLabel.getFont().getAttributes();
				attr.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				githubLabel.setFont(githubLabel.getFont().deriveFont(attr));
				githubLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
				githubLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (Desktop.isDesktopSupported()) {
							try {
								Desktop.getDesktop()
										.browse(new URI(GITHUB_URL));
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				});

				JPanel infoPanel = new JPanel();
				infoPanel.setLayout(new GridLayout(5, 1));
				infoPanel.setPreferredSize(new Dimension(contentsPanel.getPreferredSize().width-10, contentsPanel.getPreferredSize().height/3));
				
				JLabel versionLabel = new JLabel("Version " + VERSION);
				versionLabel.setFont(new Font("Arial", Font.PLAIN, 13));
				JLabel copyrightLabel = new JLabel(COPYRIGHT);
				copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 13));
				JLabel opensourceLabel = new JLabel(OPENSOURCE);
				opensourceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
				
				infoPanel.add(versionLabel);
				infoPanel.add(copyrightLabel);
				infoPanel.add(opensourceLabel);
				infoPanel.add(githubLabel);
				
				contentsPanel.add(BorderLayout.NORTH, titlePanel);
				contentsPanel.add(BorderLayout.SOUTH, infoPanel);

				popupFrame.add(logoPanel);
				popupFrame.add(contentsPanel);
				popupFrame.pack();
				popupFrame.setVisible(true);
			}
		});

		helpMenu.add(helpMenuItem);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		return menuBar;
	}

	/**
	 * @param size
	 * @return
	 */
	private ImageIcon loadSHFLogoImageIcon(Dimension size) {
		ImageIcon logoImageIcon = new ImageIcon(getClass().getClassLoader()
				.getResource("SHF_Logo_180.png"));
		Image logoImage = logoImageIcon.getImage();

		double ratio = (size.getWidth() - 50) / logoImage.getWidth(null);

		logoImageIcon = new ImageIcon(logoImage.getScaledInstance(
				(int) (logoImage.getWidth(null) * ratio),
				(int) (logoImage.getHeight(null) * ratio), Image.SCALE_SMOOTH));

		return logoImageIcon;
	}

	private JPanel createKeyboardPanel() {
		final int DEFAULT_KEY_SIZE = 50;
		JPanel keyPanel = new JPanel();
		keyPanel.setPreferredSize(new Dimension(DEFAULT_WINDOW_WIDTH,
				DEFAULT_WINDOW_HEIGHT - 100));
		keyPanel.setLayout(new GridLayout(5, 1));

		final String[][][] KEYS = {
				{ { "~", "`" }, { "!", "1" }, { "@", "2" }, { "#", "3" },
						{ "$", "4" }, { "%", "5" }, { "^", "6" }, { "&", "7" },
						{ "*", "8" }, { "(", "9" }, { ")", "0" }, { "_", "-" },
						{ "+", "=" }, { "delete" } },
				{ { "tab" }, { "Q" }, { "W" }, { "Ɛ", "E" }, { "R" }, { "T" },
						{ "Y" }, { "Ʋ", "U" }, { "Ɩ", "I" }, { "Ɔ", "O" },
						{ "P" }, { "{", "[" }, { "}", "]" }, { "|", "\\" } },
				{ { "capslock" }, { "A" }, { "S" }, { "D" }, { "F" }, { "G" },
						{ "H" }, { "J" }, { "K" }, { "L" }, { ":", ";" },
						{ "\"", "\'" }, { "return" } },
				{ { "shift" }, { "Z" }, { "X" }, { "C" }, { "V" }, { "B" },
						{ "Ŋ", "N" }, { "M" }, { "<", "," }, { ">", "." },
						{ "?", "/" }, { "shift" } },
				{ { "ctrl" }, { "alt" }, { "command" }, { " " }, { "toggle" },
						{ "alt" }, { "ctrl" } } };

		for (int i = 0; i < KEYS.length; i++) {
			JPanel rowPanel = new JPanel(new GridBagLayout());
			for (int j = 0; j < KEYS[i].length; j++) {
				KeyButton b;
				
				if (KEYS[i][j].length > 1)
					b = new KeyButton(KEYS[i][j][0], KEYS[i][j][1], uiTextArea);
				else
					b = new KeyButton(KEYS[i][j][0], uiTextArea);

				//common settings for KEYS
				b.setFont(DEFAULT_FONT);
				b.setPreferredSize(new Dimension(DEFAULT_KEY_SIZE,
						DEFAULT_KEY_SIZE));

				// setting system KEYS size
				if (KEYS[i][j][0].contains("delete")
						|| KEYS[i][j][0].contains("ctrl")
						|| KEYS[i][j][0].contains("alt")
						|| KEYS[i][j][0].contains("tab")
						|| KEYS[i][j][0].contains("command")) {
					b.setSystemKey(true);
					b.setPreferredSize(new Dimension(
							(int) (DEFAULT_KEY_SIZE * 1.4), DEFAULT_KEY_SIZE));
				} else if (KEYS[i][j][0].contains("capslock")
						|| KEYS[i][j][0].contains("return")) {
					b.setSystemKey(true);
					b.setPreferredSize(new Dimension(
							(int) (DEFAULT_KEY_SIZE * 1.7), DEFAULT_KEY_SIZE));
				} else if (KEYS[i][j][0].contains("shift")) {
					b.setSystemKey(true);
					b.setPreferredSize(new Dimension(
							(int) (DEFAULT_KEY_SIZE * 2.2), DEFAULT_KEY_SIZE));
				} else if (KEYS[i][j][0].contains("toggle")) {
					b.setSystemKey(true);
					b.setToggleKey(true);
				} else if (KEYS[i][j][0].equals(" ")) {
					b.setSystemKey(false);
					b.setPreferredSize(new Dimension(
							(int) (DEFAULT_KEY_SIZE * 6.4), DEFAULT_KEY_SIZE));
				}

				rowPanel.add(b);
			}
			keyPanel.add(rowPanel);
		}

		return keyPanel;
	}

	public void actionPerformed(ActionEvent e) {

	}

}
