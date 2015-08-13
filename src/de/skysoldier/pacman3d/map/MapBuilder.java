package de.skysoldier.pacman3d.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MapBuilder extends JPanel {
	
	private int width, height;
	private FieldButton fieldButtons[][];
	
	public MapBuilder(){
		String rawDim[] = JOptionPane.showInputDialog("dimension: (x,y)").split(",");
		width = Integer.parseInt(rawDim[0]);
		height = Integer.parseInt(rawDim[1]);
		fieldButtons = new FieldButton[height][width];
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(height, width));
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				FieldButton button = new FieldButton(j, i);
				button.setPreferredSize(new Dimension(45, 45));
				button.setBackground(FieldButton.typeColors[0]);
				button.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						FieldButton source = (FieldButton) e.getSource();
						source.switchType();
					}
				});
				add(button);
				fieldButtons[i][j] = button;
			}
		}
		frame.add(this, BorderLayout.CENTER);
		JPanel controlButtons = new JPanel();
		controlButtons.setPreferredSize(new Dimension(0, 30));
		JButton saveMap = new JButton("save..");
		saveMap.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser chooser = new JFileChooser("W:\\workspace\\Pacman3d_new\\res");
				chooser.showSaveDialog(null);
				File f = chooser.getSelectedFile();
				try{
					String lineSeperator = System.getProperty("line.separator");
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
					for(int gridy = 0; gridy < fieldButtons.length; gridy++){
						for(int gridx = 0; gridx < fieldButtons[gridy].length; gridx++){
							FieldButton b = fieldButtons[gridy][gridx];
							int type = b.getType();
							if(type == 1){
								writer.write('2');
							}
							else if(shouldBeWall(b)){
								writer.write('1');
							}
							else {
								writer.write('0');
							}
						}
						writer.write(lineSeperator);
						System.out.println(Arrays.toString(fieldButtons[gridy]));
					}
					writer.flush();
					writer.close();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		controlButtons.add(saveMap);
		frame.add(controlButtons, BorderLayout.NORTH);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	boolean shouldBeWall(FieldButton b){
		HashSet<FieldButton> fieldButtonsToTest = new HashSet<>();
		fieldButtonsToTest.add(getFieldButton(b.getGridX() - 1, b.getGridY() - 1));
		fieldButtonsToTest.add(getFieldButton(b.getGridX(), b.getGridY() - 1));
		fieldButtonsToTest.add(getFieldButton(b.getGridX() + 1, b.getGridY() - 1));
		fieldButtonsToTest.add(getFieldButton(b.getGridX() - 1, b.getGridY()));
		fieldButtonsToTest.add(getFieldButton(b.getGridX() + 1, b.getGridY()));
		fieldButtonsToTest.add(getFieldButton(b.getGridX() - 1, b.getGridY() + 1));
		fieldButtonsToTest.add(getFieldButton(b.getGridX(), b.getGridY() - 1));
		fieldButtonsToTest.add(getFieldButton(b.getGridX() + 1, b.getGridY() + 1));
		for(FieldButton button : fieldButtonsToTest){
			if(button != null && button.getType() == 1){
				return true;
			}
		}
		return false;
	}
	
	FieldButton getFieldButton(int gridx, int gridy){
		try{
			return fieldButtons[gridy][gridx];
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	static class FieldButton extends JButton {
		
		private int gridx, gridy;
		private static Font font = new Font("Consolas", Font.PLAIN, 11);
		private int type = 0;
		private static Color typeColors[] = new Color[]{
			Color.GRAY,
			Color.RED,
		};
		
		public FieldButton(int gridx, int gridy){
			super(gridx + "|" + gridy);
			this.gridx = gridx;
			this.gridy = gridy;
			setMargin(new Insets(0, 0, 0, 0));
			setFont(font);
		}
		
		public int getGridX(){
			return gridx;
		}
		
		public int getGridY(){
			return gridy;
		}
		
		public void switchType(){
			type++;
			if(type > 1) type = 0;
			setBackground(typeColors[type]);
		}
		
		public int getType(){
			return type;
		}
		
		public String toString(){
			return "[" + gridx + "|" + gridy + "](" + type + ")";
		}
	}
	
	public static void main(String[] args){
		new MapBuilder();
	}
}