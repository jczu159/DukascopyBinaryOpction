package com.binary.run;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class MainProgram {

	private JFrame frame;
	private JTextField timestampS;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;

	public static String timestampXy;
	public static String oneMinute;
	public static String twoMinute;
	public static String threeMinute;
	public static String fourMinute;
	public static String fiveMinute;
	public static String amountBoxXY;
	public static String callButtonXY;
	public static String putButtonXY;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainProgram window = new MainProgram();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainProgram() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		timestampS = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, timestampS, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, timestampS, 90, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(timestampS);
		timestampS.setColumns(10);

		JLabel label = new JLabel("時間戳座標");
		springLayout.putConstraint(SpringLayout.SOUTH, label, 0, SpringLayout.SOUTH, timestampS);
		springLayout.putConstraint(SpringLayout.EAST, label, -17, SpringLayout.WEST, timestampS);
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("一分鐘座標");
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 16, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.WEST, label_1, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_1);

		textField_1 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 0, SpringLayout.WEST, timestampS);
		springLayout.putConstraint(SpringLayout.SOUTH, textField_1, 0, SpringLayout.SOUTH, label_1);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel label_2 = new JLabel("兩分鐘座標");
		springLayout.putConstraint(SpringLayout.NORTH, label_2, 14, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_2);

		textField_2 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_2, -3, SpringLayout.NORTH, label_2);
		springLayout.putConstraint(SpringLayout.WEST, textField_2, 0, SpringLayout.WEST, timestampS);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JLabel label_3 = new JLabel("三分鐘座標");
		springLayout.putConstraint(SpringLayout.NORTH, label_3, 16, SpringLayout.SOUTH, label_2);
		springLayout.putConstraint(SpringLayout.WEST, label_3, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_3);

		textField_3 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_3, 0, SpringLayout.NORTH, label_3);
		springLayout.putConstraint(SpringLayout.WEST, textField_3, 0, SpringLayout.WEST, timestampS);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		JLabel label_4 = new JLabel("五分鐘座標");
		springLayout.putConstraint(SpringLayout.NORTH, label_4, 20, SpringLayout.SOUTH, label_3);
		springLayout.putConstraint(SpringLayout.WEST, label_4, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_4);

		textField_4 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_4, 0, SpringLayout.WEST, timestampS);
		springLayout.putConstraint(SpringLayout.SOUTH, textField_4, 0, SpringLayout.SOUTH, label_4);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		JLabel label_5 = new JLabel("金額框座標");
		springLayout.putConstraint(SpringLayout.NORTH, label_5, 22, SpringLayout.SOUTH, label_4);
		springLayout.putConstraint(SpringLayout.WEST, label_5, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_5);

		textField_5 = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, textField_5, 0, SpringLayout.SOUTH, label_5);
		springLayout.putConstraint(SpringLayout.EAST, textField_5, 0, SpringLayout.EAST, timestampS);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

		JLabel lblNewLabel = new JLabel("CALL按鈕座標");
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 44, SpringLayout.EAST, timestampS);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNewLabel, 0, SpringLayout.SOUTH, timestampS);
		frame.getContentPane().add(lblNewLabel);

		textField_6 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_6, 0, SpringLayout.NORTH, timestampS);
		springLayout.putConstraint(SpringLayout.WEST, textField_6, 6, SpringLayout.EAST, lblNewLabel);
		frame.getContentPane().add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_7, 0, SpringLayout.NORTH, textField_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_7, 0, SpringLayout.EAST, textField_6);
		frame.getContentPane().add(textField_7);
		textField_7.setColumns(10);

		JLabel lblPut = new JLabel("PUT按鈕座標");
		springLayout.putConstraint(SpringLayout.WEST, lblPut, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPut, 0, SpringLayout.SOUTH, label_1);
		frame.getContentPane().add(lblPut);

		JButton button = new JButton("保存設定");
		springLayout.putConstraint(SpringLayout.WEST, button, 0, SpringLayout.WEST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.SOUTH, button, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("開始運行");
		springLayout.putConstraint(SpringLayout.SOUTH, button_1, 0, SpringLayout.SOUTH, button);
		springLayout.putConstraint(SpringLayout.EAST, button_1, 0, SpringLayout.EAST, textField_6);
		frame.getContentPane().add(button_1);
	}
}
