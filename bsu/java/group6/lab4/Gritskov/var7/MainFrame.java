package bsu.java.group6.lab4.Gritskov.var7;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MainFrame extends JFrame {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private JFileChooser fileChooser = null;
	private JCheckBoxMenuItem showAxisMenuItem;
	private JCheckBoxMenuItem showMarkersMenuItem;
	private GraphicsDisplay display = new GraphicsDisplay();
	private boolean fileLoaded = false;
	
	public MainFrame() {
		super("Построение графиков функций на основе подготовленных файлов");
		setSize(WIDTH, HEIGHT);
		Toolkit kit = Toolkit.getDefaultToolkit();	
		setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
		setExtendedState(MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		Action openGraphicsAction = new AbstractAction("Open") {
			public void actionPerformed(ActionEvent event) {
			if (fileChooser==null) {
				fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
			}
			if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION)
				openGraphics(fileChooser.getSelectedFile());
			}
		};
		fileMenu.add(openGraphicsAction);
		JMenu graphicsMenu = new JMenu("Graphic");
		menuBar.add(graphicsMenu);
		
		Action showAxisAction = new AbstractAction("Show Axis") {
			public void actionPerformed(ActionEvent event) {
				display.setShowAxis(showAxisMenuItem.isSelected());
			}
		};
		showAxisMenuItem = new JCheckBoxMenuItem(showAxisAction);
		graphicsMenu.add(showAxisMenuItem);
		showAxisMenuItem.setSelected(true);
		
		Action showMarkersAction = new AbstractAction("Show markers") {
				public void actionPerformed(ActionEvent event) {
					display.setShowMarkers(showMarkersMenuItem.isSelected());
				}
		};
		showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
		graphicsMenu.add(showMarkersMenuItem);
		showMarkersMenuItem.setSelected(false);
		graphicsMenu.addMenuListener(new GraphicsMenuListener());
		
		getContentPane().add(display, BorderLayout.CENTER);
		
	}
	
	
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
}
