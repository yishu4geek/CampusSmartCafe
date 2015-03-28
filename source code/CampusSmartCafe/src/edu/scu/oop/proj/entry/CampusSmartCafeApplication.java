package edu.scu.oop.proj.entry;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.scu.oop.proj.dao.MySQLAccess;
import edu.scu.oop.proj.view.MainFrame;


public class CampusSmartCafeApplication {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//initialize the main GUI 
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame mainFrame = new MainFrame(); 
				mainFrame.setVisible(true);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}
}
