package com.HIS.Controller;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer{
	public ButtonRenderer() {
		
		URL imageUrl = getClass().getResource("/pencil-revision-svgrepo-com-ezgif.com-png-to-jpg-converter.jpg");
		
		ImageIcon originalIcon = new ImageIcon(imageUrl); 
        Image resizedImage = originalIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        setIcon(resizedIcon);
        setOpaque(true);
//        setContentAreaFilled(false); //  Transparent background
        setBorderPainted(false);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		return this;
	}
	
	

}
