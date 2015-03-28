package edu.scu.oop.proj.view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*; 

import edu.scu.oop.proj.entity.UserSession;

//MapPanel holds the campus map and the buttons corresponding to cafes or vending machines
public class MapPanel extends JPanel {
	private BufferedImage map; 
	private JButton cafe1;
	private JButton cafe2; 
	private JButton cafe3; 
	private JButton vm1; 
	private JButton vm2; 
	private JButton vm3; 
	private JButton vm4; 
	private JButton vm5; 
	private String selectedButtonText; 
	
	private Image img; 
	private MenuPanel menuPanel; 
	private OrderPanel orderPanel; 
	
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	private static final String CAFE1 = "Cafe1"; 
	private static final String CAFE2 = "Cafe2"; 
	private static final String CAFE3 = "Cafe3"; 
	private static final String VENDING_MACHINE_1 = "Machine1"; 
	private static final String VENDING_MACHINE_2 = "Machine2";
	private static final String VENDING_MACHINE_3 = "Machine3";
	private static final String VENDING_MACHINE_4 = "Machine4";
	private static final String VENDING_MACHINE_5 = "Machine5";
	
	public MapPanel() {
		super(); 
		GUIManager.getInstance().setMap_Panel(this);
		
		//load the map img into the GUI 
		try {
			map = ImageIO.read(new File("img/map.png"));
		}
		catch (IOException ex) {
			System.out.println("Map not exist"); 
		}
		img = new ImageIcon(map).getImage(); 
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null)); 
		setPreferredSize(size); 

		GridBagLayout layout = new GridBagLayout(); 
		JPanel buttonPanel = new JPanel(); 
		cafe1 = new JButton(CAFE1); 
		cafe2 = new JButton(CAFE2); 
		cafe3 = new JButton(CAFE3); 
		vm1 = new JButton(VENDING_MACHINE_1); 
		vm2 = new JButton(VENDING_MACHINE_2); 
		vm3 = new JButton(VENDING_MACHINE_3); 
		vm4 = new JButton(VENDING_MACHINE_4); 
		vm5 = new JButton(VENDING_MACHINE_5); 
		
		
		GridBagConstraints gc = new GridBagConstraints(); 
		buttonPanel.setLayout(layout);
		buttonPanel.add(cafe1);
		buttonPanel.add(vm2); 
		buttonPanel.add(vm5); 
		buttonPanel.add(vm1);
		buttonPanel.add(cafe2);
		buttonPanel.add(vm3);
		buttonPanel.add(vm4);
		buttonPanel.add(cafe3);
		buttonPanel.setOpaque(false);
		
		//if user hasn't logged in yet, disable the buttons 
		if (UserSession.getInstance().getCurrentUser() == null) {
			cafe1.setEnabled(false);
			cafe2.setEnabled(false);
			cafe3.setEnabled(false);
			vm1.setEnabled(false);
			vm2.setEnabled(false);
			vm3.setEnabled(false);
			vm4.setEnabled(false);
			vm5.setEnabled(false);
		} 
		
		cafe1.setActionCommand(CAFE);
		cafe2.setActionCommand(CAFE);
		cafe3.setActionCommand(CAFE);
		vm1.setActionCommand(VENDING_MACHINE);
		vm2.setActionCommand(VENDING_MACHINE);
		vm3.setActionCommand(VENDING_MACHINE);
		vm4.setActionCommand(VENDING_MACHINE);
		vm5.setActionCommand(VENDING_MACHINE);
			
		
		SelectVenueActionListener svcl = new SelectVenueActionListener(); 
		cafe1.addActionListener(new SelectVenueActionListener());
		cafe2.addActionListener(new SelectVenueActionListener());
		cafe3.addActionListener(new SelectVenueActionListener());
		vm1.addActionListener(new SelectVenueActionListener());
		vm2.addActionListener(new SelectVenueActionListener());
		vm3.addActionListener(new SelectVenueActionListener());
		vm4.addActionListener(new SelectVenueActionListener());
		vm5.addActionListener(new SelectVenueActionListener());
		
		gc.insets = new Insets(20,20,6,20); 
		gc.gridx = 3; 
		gc.gridy = 1; 
		layout.setConstraints(cafe1, gc);
		
		gc.gridx = 5; 
		gc.gridy = 2; 
		layout.setConstraints(vm2, gc);
		
		gc.gridx = 1; 
		gc.gridy = 4; 
		layout.setConstraints(vm5, gc);
		
		gc.gridx = 7; 
		gc.gridy = 5; 
		layout.setConstraints(vm1, gc);
		
		gc.gridx = 4; 
		gc.gridy = 6; 
		layout.setConstraints(cafe2, gc);
		
		gc.gridx = 2; 
		gc.gridy = 7; 
		layout.setConstraints(vm3, gc);
		
		gc.gridx = 10; 
		gc.gridy = 8; 
		layout.setConstraints(vm4, gc);
		
		gc.gridx = 3; 
		gc.gridy = 9; 
		layout.setConstraints(cafe3, gc);
		
		this.add(buttonPanel); 
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null); 
	}
	
	//add event listners for the buttons 
	class SelectVenueActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { 
			MainFrame frame = (MainFrame)SwingUtilities.getRoot(MapPanel.this); 
			ContentPanel contentPanel = (ContentPanel)SwingUtilities.getAncestorOfClass(ContentPanel.class, MapPanel.this); 
			AbstractButton aButton = (AbstractButton)e.getSource(); 
			MapPanel.this.setSelectedButtonText(aButton.getText()); 
			if (CAFE.equals(e.getActionCommand())) {
				contentPanel.removeAll(); 
				orderPanel = new OrderPanel(CAFE); 
				contentPanel.add(orderPanel, BorderLayout.CENTER); 
				frame.validate();
		        frame.setVisible(true);
			}
			else if (VENDING_MACHINE.equals(e.getActionCommand())) {
				contentPanel.removeAll(); 
				orderPanel = new OrderPanel(VENDING_MACHINE); 
				contentPanel.add(orderPanel); 
				frame.validate();
		        frame.setVisible(true);
			}
		}
	}
	
	public String getSelectedButtonText() {
		return this.selectedButtonText; 
	}
	
	public void setSelectedButtonText(String buttonText) {
		this.selectedButtonText = buttonText;
	}
	
}


