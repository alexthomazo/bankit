/*
 * Copyright (C) 2012 Alexandre Thomazo
 *
 * This file is part of BankIt.
 *
 * BankIt is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BankIt is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BankIt. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alexlg.bankit;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 * Frame which display a waiting message during
 * the war deployment and the start of Jetty.
 * 
 * @author Alexandre Thomazo
 */
public class LoadingFrame extends JFrame {
	private static final long serialVersionUID = -1896822840336955343L;


	public LoadingFrame() {
		super("BankIt");
		
		//try to set system L&F
		this.setSystemLnF();
		
		JLabel label = new JLabel("Chargement de BankIt en cours, merci de patienter...");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//adding some margin around text
		JPanel panel = new JPanel();
		panel.add(label);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(panel, BorderLayout.CENTER);

		//resize around component
		this.pack();
		//center frame on screen
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	
	public void display() {
		this.setVisible(true);
	}
	
	public void close() {
		this.setVisible(false);
		this.dispose();
	}
	
	public void setSystemLnF() {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new LoadingFrame().display();
	}
}
