package com.binary.run;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.alibaba.fastjson.JSONObject;

public class WindowsBulider {

	private JFrame frame;
	private JTextField accountText;
	private String version = "1.0.0.0";
	private JTextField chromePath;
	private JTextField AmountSetting;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowsBulider window = new WindowsBulider();
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
	public WindowsBulider() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("杜卡斯貝全自動下單系統");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel label = new JLabel("帳號");
		springLayout.putConstraint(SpringLayout.NORTH, label, 26, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, label, 30, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(label);

		JLabel label_1 = new JLabel("密碼");
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 18, SpringLayout.SOUTH, label);
		springLayout.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, label);
		frame.getContentPane().add(label_1);

		accountText = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, accountText, -3, SpringLayout.NORTH, label);
		springLayout.putConstraint(SpringLayout.WEST, accountText, 57, SpringLayout.EAST, label);
		springLayout.putConstraint(SpringLayout.EAST, accountText, 208, SpringLayout.EAST, label);
		frame.getContentPane().add(accountText);
		accountText.setColumns(10);

		JLabel lblVersion = new JLabel("Version " + version);
		springLayout.putConstraint(SpringLayout.SOUTH, lblVersion, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblVersion, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(lblVersion);

		JButton startButton = new JButton("啟動程式");
		springLayout.putConstraint(SpringLayout.WEST, startButton, 66, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, startButton, 0, SpringLayout.SOUTH, lblVersion);
		frame.getContentPane().add(startButton);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

//					Samantha92    40031810
					if (!accountText.getText().equals("") && !passwordField.getText().equals("")
							&& !chromePath.getText().equals("") && !AmountSetting.getText().equals("")) {
						boolean isVerified = sendGet(accountText.getText(), version);
						if (!isVerified) {
							throw new Exception("帳號或者版本號碼驗證不通過!!");
						} else {
							String path = chromePath.getText();
							String outpath = path.replace('\\', '/');
							String amount[] = AmountSetting.getText().split(",");

							System.out.println("驗證通過");
							ClientTest run = new ClientTest(accountText.getText(), passwordField.getText(), outpath,
									amount);
						}
					} else {
						JOptionPane.showMessageDialog(frame, "欄位必填寫之處，全數不得為空!!");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, e1);
					e1.printStackTrace();
					System.exit(0);
				}

			}
		});

		JButton closeButton = new JButton("關閉程式");
		springLayout.putConstraint(SpringLayout.NORTH, closeButton, 0, SpringLayout.NORTH, startButton);
		springLayout.putConstraint(SpringLayout.WEST, closeButton, 17, SpringLayout.EAST, startButton);
		frame.getContentPane().add(closeButton);

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("正在為您終止程式 -- >>");
				try {
					System.exit(0);
				} catch (Exception e1) {
					JOptionPane.showConfirmDialog(null, "choose one", "choose one", JOptionPane.YES_NO_OPTION);
				}
			}
		});

		JLabel lblChrome = new JLabel("Chrome路徑");
		springLayout.putConstraint(SpringLayout.NORTH, lblChrome, 17, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.WEST, lblChrome, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(lblChrome);

		chromePath = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, chromePath, -3, SpringLayout.NORTH, lblChrome);
		springLayout.putConstraint(SpringLayout.WEST, chromePath, 0, SpringLayout.WEST, accountText);
		springLayout.putConstraint(SpringLayout.EAST, chromePath, 170, SpringLayout.EAST, lblChrome);
		frame.getContentPane().add(chromePath);
		chromePath.setColumns(10);

		JLabel label_2 = new JLabel("加碼配置金額");
		springLayout.putConstraint(SpringLayout.WEST, label_2, 0, SpringLayout.WEST, label);
		frame.getContentPane().add(label_2);

		AmountSetting = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, AmountSetting, 12, SpringLayout.SOUTH, chromePath);
		springLayout.putConstraint(SpringLayout.SOUTH, label_2, 0, SpringLayout.SOUTH, AmountSetting);
		springLayout.putConstraint(SpringLayout.WEST, AmountSetting, 0, SpringLayout.WEST, accountText);
		springLayout.putConstraint(SpringLayout.EAST, AmountSetting, 0, SpringLayout.EAST, accountText);
		frame.getContentPane().add(AmountSetting);
		AmountSetting.setColumns(10);

		passwordField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, accountText);
		springLayout.putConstraint(SpringLayout.SOUTH, passwordField, 0, SpringLayout.SOUTH, label_1);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, 151, SpringLayout.WEST, accountText);
		frame.getContentPane().add(passwordField);
		passwordField.setColumns(10);

		accountText.setText("Samantha92");
		chromePath.setText("C:/Users/admin/Desktop/chromedriver.exe");
		passwordField.setText("40031810");
		AmountSetting.setText("10,23,46,92,184,368,640");


	}

	private static boolean sendGet(String account, String version) throws Exception {
		boolean isVerified = false;
		String url = "https://huirui.online/AirShipServer/getVerified?account=" + account + "&version=" + version + "";
		HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
		// optional default is GET
		httpClient.setRequestMethod("GET");
		// add request header
		httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
		int responseCode = httpClient.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {

			StringBuilder response = new StringBuilder();
			String line;

			while ((line = in.readLine()) != null) {
				response.append(line);
			}
			JSONObject json = new JSONObject();
			JSONObject jsOBj = (JSONObject) json.parse(response.toString());
			System.out.println(jsOBj.toString());
			if (jsOBj.getString("code").equals("0")) {
				isVerified = true;
			}

		}
		return isVerified;

	}
}
