import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.math.BigInteger;

import javax.swing.*;
//import javax.swing.border.Border;

public class Calculator extends JFrame implements ActionListener, MouseListener
{
	private String buffer = "0", total = "0", currentOperation, lastOperation, binaryString, currentFormat = "dec", lastFormat;
	private JPanel radioPanel1, radioPanel2, buttonPanel1;
	private ButtonGroup radioButtons1, radioButtons2;
	private JMenuBar menubar;
	private JMenu help, edit, view;
	private JMenuItem helpItem, editItem, viewItem;
	private JRadioButton hexButton, decButton, octButton, binButton, qwordButton, dwordButton, wordButton, byteButton;
	//private JLabel blank;//26 total
	private JButton blank, blank2, blank3, blank4, blank5, blank6, blank7, blank8, blank9, blank10, blank11, blank12, blank13, blank14, blank15,
					a, b, c, d, e, f, mod, backspace, ce, clear, plusMinus, root, percent, fraction, equals, plus, minus, multiply, divide, period,
					one, two, three, four, five, six, seven, eight, nine, zed, quot;
	private JTextField num1Field, resultField;
	private JTextArea hexField;
	private Clipboard clipboard;
	
	public Calculator()
	{
		GroupLayout layout = new GroupLayout(getContentPane());
		setLayout(layout);
		
		createMenuBar();
		createResultArea();
		createRadioButtons();
		createButtons();
		getClipboard();
		//layout.setHorizontalGroup(
			//	layout.createSequentialGroup()
				//	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
					//		.addComponent(buttonPanel1)));
	//	layout.setVerticalGroup(
		//		layout.createSequentialGroup()
			//		.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
				//	.addComponent(buttonPanel1));
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(menubar)
						.addGap(10)
						.addGroup(layout.createSequentialGroup()
							.addGap(35)
							.addComponent(resultField))
						.addGroup(layout.createSequentialGroup()
							.addGap(35)
							.addComponent(hexField))
						.addComponent(buttonPanel1, GroupLayout.Alignment.CENTER, 200, 250, 500)));
						//.addGroup(layout.createSequentialGroup()
							//.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								//.addComponent(radioPanel1)
								//.addComponent(radioPanel2))
							//.addComponent(buttonPanel1))));
		layout.setVerticalGroup(
				layout.createSequentialGroup()
					.addComponent(menubar)
					.addGap(10)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGap(35)
						.addComponent(resultField))
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
							.addGap(35)
							.addComponent(hexField))
					.addComponent(buttonPanel1, 200, 250, 500));
					//.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						//.addGroup(layout.createSequentialGroup()
							//.addComponent(radioPanel1)
							//.addComponent(radioPanel2))
						//.addComponent(buttonPanel1)));
		
		//num1Label = new JLabel("Number 1: ");
		//num2Label = new JLabel("Number 2: ");
		//num1Field = new JTextField(10);
		//num2Field = new JTextField(10);
		
		//panel1.add(num1Label);
		//panel1.add(num1Field);
		//panel1.add(num2Label);
		//panel1.add(num2Field);
		//add(panel1, BorderLayout.NORTH);
		
		//panel2 = new JPanel();
		//addButton = new JButton("Add");
		//panel2.add(addButton);
		//add(panel2, BorderLayout.CENTER);
		
		//panel3 = new JPanel();
		//resultLabel = new JLabel("Result: ");
		//resultField = new JTextField(10);
		//resultField.setEditable(false);
		//panel3.add(resultLabel);
		//panel3.add(resultField);
		//add(panel3, BorderLayout.SOUTH);
		//addButton.addActionListener(this);
	}
	
	private void createMenuBar()
	{
		menubar = new JMenuBar();
		
		//View
		view = new JMenu("View");
		view.setMnemonic(KeyEvent.VK_V);
		viewItem = new JMenuItem("Hide/Show Calculator");
		view.add(viewItem);
		viewItem.addActionListener(this);

		//Edit
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		editItem = new JMenuItem("Copy");
		edit.add(editItem);
		editItem.addActionListener(this);
		
		//Help
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		helpItem = new JMenuItem("About Calculator");
		help.add(helpItem);
		helpItem.addActionListener(this);
		
		menubar.add(view);
		menubar.add(edit);
		menubar.add(help);
		menubar.setMaximumSize(new Dimension(525, 25));
	}
	
	private void createResultArea()
	{
		//Result Field
		resultField = new JTextField(10);
		resultField.setEditable(false);
		resultField.setBackground(new Color(209, 209, 209));
		resultField.setHorizontalAlignment(JTextField.RIGHT);
		resultField.setFont(new Font("SansSerif", Font.BOLD, 35));
		resultField.setText("0");
		resultField.setMaximumSize(new Dimension(425, 75));
		
		hexField = new JTextArea();
		hexField.setEditable(false);
		hexField.setBackground(this.getBackground());
		hexField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		hexField.setMaximumSize(new Dimension(425, 75));
		hexField.setText("0000 0000 0000 0000 0000 0000 0000 0000\n"
					   + "63                  47               32\n"
					   + "0000 0000 0000 0000 0000 0000 0000 0000\n"
					   + "31                  15                0");
		
		//hexField2.setText("63                  47               32");
		//hexField3.setText("0000 0000 0000 0000 0000 0000 0000 0000");
		//hexField4.setText("31                  15                0");
		
		hexField.setBorder(BorderFactory.createEtchedBorder());
		//hexPanel.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private void createRadioButtons()
	{
		//Panel 1
		radioPanel1 = new JPanel();
		radioPanel1.setLayout(new GridLayout(4, 1));
		radioButtons1 = new ButtonGroup();
		
		hexButton = new JRadioButton("Hex");
		decButton = new JRadioButton("Dec");
		octButton = new JRadioButton("Oct");
		binButton = new JRadioButton("Bin");
		
		hexButton.addActionListener(this);
		decButton.addActionListener(this);
		octButton.addActionListener(this);
		binButton.addActionListener(this);
		
		radioButtons1.add(hexButton);
		radioButtons1.add(decButton);
		radioButtons1.add(octButton);
		radioButtons1.add(binButton);
		
		radioPanel1.add(hexButton);
		radioPanel1.add(decButton);
		radioPanel1.add(octButton);
		radioPanel1.add(binButton);
	
		radioPanel1.setBorder(BorderFactory.createEtchedBorder());
		//radioPanel1.setMaximumSize(new Dimension(100, 100));
		
		decButton.setSelected(true);
		
		//Panel 2
		radioPanel2 = new JPanel();
		radioPanel2.setLayout(new GridLayout(4, 1));
		radioButtons2 = new ButtonGroup();
		
		qwordButton = new JRadioButton("Qword");
		dwordButton = new JRadioButton("Dword");
		wordButton = new JRadioButton("Word");
		byteButton = new JRadioButton("Byte");
		
		radioButtons2.add(qwordButton);
		radioButtons2.add(dwordButton);
		radioButtons2.add(wordButton);
		radioButtons2.add(byteButton);
		
		radioPanel2.add(qwordButton);
		radioPanel2.add(dwordButton);
		radioPanel2.add(wordButton);
		radioPanel2.add(byteButton);
		
		//radioPanel2.setMaximumSize(new Dimension(100, 100));
		radioPanel2.setBorder(BorderFactory.createEtchedBorder());
		
		qwordButton.setSelected(true);
	}
	
	private void createButtons()
	{
		buttonPanel1 = new JPanel();
		//buttonPanel1.setLayout(new GridLayout(5, 7));
		buttonPanel1.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		Dimension buttonSize = new Dimension(45, 25);
		
		//Blank buttons
		blank = new JButton();
		blank.setOpaque(true);
		blank.setPreferredSize(buttonSize);
		blank.setEnabled(false);
		
		blank2 = new JButton();
		blank2.setOpaque(true);
		blank2.setPreferredSize(buttonSize);
		blank2.setEnabled(false);
		
		blank3 = new JButton();
		blank3.setOpaque(true);
		blank3.setPreferredSize(buttonSize);
		blank3.setEnabled(false);
		
		blank4 = new JButton();
		blank4.setOpaque(true);
		blank4.setPreferredSize(buttonSize);
		blank4.setEnabled(false);
		
		blank5 = new JButton();
		blank5.setOpaque(true);
		blank5.setPreferredSize(buttonSize);
		blank5.setEnabled(false);
		
		blank6 = new JButton();
		blank6.setOpaque(true);
		blank6.setPreferredSize(buttonSize);
		blank6.setEnabled(false);
		
		blank7 = new JButton();
		blank7.setOpaque(true);
		blank7.setPreferredSize(buttonSize);
		blank7.setEnabled(false);
		
		blank8 = new JButton();
		blank8.setOpaque(true);
		blank8.setPreferredSize(buttonSize);
		blank8.setEnabled(false);
		
		blank9 = new JButton();
		blank9.setOpaque(true);
		blank9.setPreferredSize(buttonSize);
		blank9.setEnabled(false);
		
		blank10 = new JButton();
		blank10.setOpaque(true);
		blank10.setPreferredSize(buttonSize);
		blank10.setEnabled(false);
		
		blank11 = new JButton();
		blank11.setOpaque(true);
		blank11.setPreferredSize(buttonSize);
		blank11.setEnabled(false);
		
		blank12 = new JButton();
		blank12.setOpaque(true);
		blank12.setPreferredSize(buttonSize);
		blank12.setEnabled(false);
		
		blank13 = new JButton();
		blank13.setOpaque(true);
		blank13.setPreferredSize(buttonSize);
		blank13.setEnabled(false);
		
		blank14 = new JButton();
		blank14.setOpaque(true);
		blank14.setPreferredSize(buttonSize);
		blank14.setEnabled(false);
		
		blank15 = new JButton();
		blank15.setOpaque(true);
		blank15.setPreferredSize(buttonSize);
		blank15.setEnabled(false);
		
		//Quot? What is this?
		quot = new JButton("Quot");
		quot.setOpaque(false);
		quot.setPreferredSize(buttonSize);
		quot.setFont(new Font("Arial", Font.PLAIN, 8));
		
		//Mod button
		mod = new JButton("Mod");
		mod.setPreferredSize(buttonSize);
		mod.setFont(new Font("Arial", Font.PLAIN, 9));

		//A button
		a = new JButton("A");
		a.setPreferredSize(buttonSize);
		a.setEnabled(false);
		
		b = new JButton("B");
		b.setPreferredSize(buttonSize);
		b.setEnabled(false);
		
		c = new JButton("C");
		c.setPreferredSize(buttonSize);
		c.setEnabled(false);
		
		d = new JButton("D");
		d.setPreferredSize(buttonSize);
		d.setEnabled(false);
		
		e = new JButton("E");
		e.setPreferredSize(buttonSize);
		e.setEnabled(false);
		
		f = new JButton("F");
		f.setPreferredSize(buttonSize);
		f.setEnabled(false);
		
		//Numbers
		zed = new JButton("0");
		zed.setPreferredSize(new Dimension(20, 10));
		zed.addActionListener(this);
		
		one = new JButton("1");
		one.setPreferredSize(buttonSize);
		one.addActionListener(this);
		
		two = new JButton("2");
		two.setPreferredSize(buttonSize);
		two.addActionListener(this);
		
		three = new JButton("3");
		three.setPreferredSize(buttonSize);
		three.addActionListener(this);
		
		four = new JButton("4");
		four.setPreferredSize(buttonSize);
		four.addActionListener(this);
		
		five = new JButton("5");
		five.setPreferredSize(buttonSize);
		five.addActionListener(this);
		
		six = new JButton("6");
		six.setPreferredSize(buttonSize);
		six.addActionListener(this);
		
		seven = new JButton("7");
		seven.setPreferredSize(buttonSize);
		seven.addActionListener(this);
		
		eight = new JButton("8");
		eight.setPreferredSize(buttonSize);
		eight.addActionListener(this);
		
		nine = new JButton("9");
		nine.setPreferredSize(buttonSize);
		nine.addActionListener(this);
		
		//Backspace
		backspace = new JButton("\u2190");
		backspace.setPreferredSize(buttonSize);
		backspace.addActionListener(this);
		
		//CE
		ce = new JButton("CE");
		ce.setPreferredSize(buttonSize);
		ce.addActionListener(this);
		
		clear = new JButton("C");
		clear.setPreferredSize(buttonSize);
		clear.addActionListener(this);
		
		//Operations
		plusMinus = new JButton("\u00B1");
		plusMinus.setPreferredSize(buttonSize);
		plusMinus.addActionListener(this);
		
		divide = new JButton("/");
		divide.setPreferredSize(buttonSize);
		divide.addActionListener(this);
		
		multiply = new JButton("*");
		multiply.setPreferredSize(buttonSize);
		multiply.addActionListener(this);
		
		minus = new JButton("-");
		minus.setPreferredSize(buttonSize);
		minus.addActionListener(this);
		
		plus = new JButton("+");
		plus.setPreferredSize(buttonSize);
		plus.addActionListener(this);
		
		root = new JButton("\u221A");
		root.setPreferredSize(buttonSize);
		
		percent = new JButton("%");
		percent.setPreferredSize(buttonSize);
		
		fraction = new JButton("1\u2044x");
		fraction.setPreferredSize(buttonSize);
		
		equals = new JButton("=");
		equals.setPreferredSize(new Dimension(10, 20));
		equals.addActionListener(this);
		
		period = new JButton(".");
		period.setPreferredSize(buttonSize);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.ipady = 10;
		
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 0;
		buttonPanel1.add(radioPanel1, constraints);
		constraints.gridheight = 3;
		constraints.gridx = 0;
		constraints.gridy = 3;
		buttonPanel1.add(radioPanel2, constraints);
		
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 0;
		buttonPanel1.add(quot, constraints);
		constraints.gridx = 2;
		constraints.gridy = 0;
		buttonPanel1.add(mod, constraints);
		constraints.gridx = 3;
		constraints.gridy = 0;
		buttonPanel1.add(a, constraints);
		constraints.gridx = 4;
		constraints.gridy = 0;
		buttonPanel1.add(blank9, constraints);
		constraints.gridx = 5;
		constraints.gridy = 0;
		buttonPanel1.add(blank10, constraints);
		constraints.gridx = 6;
		constraints.gridy = 0;
		buttonPanel1.add(blank11, constraints);
		constraints.gridx = 7;
		constraints.gridy = 0;
		buttonPanel1.add(blank12, constraints);
		constraints.gridx = 8;
		constraints.gridy = 0;
		buttonPanel1.add(blank13, constraints);

		//Row 2
		constraints.gridx = 1;
		constraints.gridy = 1;
		buttonPanel1.add(blank, constraints);
		constraints.gridx = 2;
		constraints.gridy = 1;
		buttonPanel1.add(blank2, constraints);
		constraints.gridx = 3;
		constraints.gridy = 1;
		buttonPanel1.add(b, constraints);
		constraints.gridx = 4;
		constraints.gridy = 1;
		buttonPanel1.add(backspace, constraints);
		constraints.gridx = 5;
		constraints.gridy = 1;
		buttonPanel1.add(ce, constraints);
		constraints.gridx = 6;
		constraints.gridy = 1;
		buttonPanel1.add(clear, constraints);
		constraints.gridx = 7;
		constraints.gridy = 1;
		buttonPanel1.add(plusMinus, constraints);
		constraints.gridx = 8;
		constraints.gridy = 1;
		buttonPanel1.add(root, constraints);

		//Row 3
		constraints.gridx = 1;
		constraints.gridy = 2;
		buttonPanel1.add(blank3, constraints);
		constraints.gridx = 2;
		constraints.gridy = 2;
		buttonPanel1.add(blank4, constraints);
		constraints.gridx = 3;
		constraints.gridy = 2;
		buttonPanel1.add(c, constraints);
		constraints.gridx = 4;
		constraints.gridy = 2;
		buttonPanel1.add(seven, constraints);
		constraints.gridx = 5;
		constraints.gridy = 2;
		buttonPanel1.add(eight, constraints);
		constraints.gridx = 6;
		constraints.gridy = 2;
		buttonPanel1.add(nine, constraints);
		constraints.gridx = 7;
		constraints.gridy = 2;
		buttonPanel1.add(divide, constraints);
		constraints.gridx = 8;
		constraints.gridy = 2;
		buttonPanel1.add(percent, constraints);
		
		//Row 4
		constraints.gridx = 1;
		constraints.gridy = 3;
		buttonPanel1.add(blank5, constraints);
		constraints.gridx = 2;
		constraints.gridy = 3;
		buttonPanel1.add(blank6, constraints);
		constraints.gridx = 3;
		constraints.gridy = 3;
		buttonPanel1.add(d, constraints);
		constraints.gridx = 4;
		constraints.gridy = 3;
		buttonPanel1.add(four, constraints);
		constraints.gridx = 5;
		constraints.gridy = 3;
		buttonPanel1.add(five, constraints);
		constraints.gridx = 6;
		constraints.gridy = 3;
		buttonPanel1.add(six, constraints);
		constraints.gridx = 7;
		constraints.gridy = 3;
		buttonPanel1.add(multiply, constraints);
		constraints.gridx = 8;
		constraints.gridy = 3;
		buttonPanel1.add(fraction, constraints);

		//Row 5
		constraints.gridx = 1;
		constraints.gridy = 4;
		buttonPanel1.add(blank7, constraints);
		constraints.gridx = 2;
		constraints.gridy = 4;
		buttonPanel1.add(blank8, constraints);
		constraints.gridx = 3;
		constraints.gridy = 4;
		buttonPanel1.add(e, constraints);
		constraints.gridx = 4;
		constraints.gridy = 4;
		buttonPanel1.add(one, constraints);
		constraints.gridx = 5;
		constraints.gridy = 4;
		buttonPanel1.add(two, constraints);
		constraints.gridx = 6;
		constraints.gridy = 4;
		buttonPanel1.add(three, constraints);
		constraints.gridx = 7;
		constraints.gridy = 4;
		buttonPanel1.add(minus, constraints);
		constraints.gridx = 8;
		constraints.gridy = 4;
		constraints.gridheight = 2;
		buttonPanel1.add(equals, constraints);
		
		//Row 6
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 5;
		buttonPanel1.add(blank14, constraints);
		constraints.gridx = 2;
		constraints.gridy = 5;
		buttonPanel1.add(blank15, constraints);
		constraints.gridx = 3;
		constraints.gridy = 5;
		buttonPanel1.add(f, constraints);
		constraints.gridx = 4;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		buttonPanel1.add(zed, constraints);
		constraints.gridwidth = 1;
		constraints.gridx = 6;
		constraints.gridy = 5;
		buttonPanel1.add(period, constraints);
		constraints.gridx = 7;
		constraints.gridy = 5;
		buttonPanel1.add(plus, constraints);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{	
		if (e.getSource() == viewItem)
		{
			if (getState() == Frame.ICONIFIED)
			{
				setState(Frame.NORMAL);
			}
			else
			{				
				setState(Frame.ICONIFIED);
			}
		}
		else if (e.getSource() == editItem)
		{
			clipboard.setContents(new StringSelection(buffer), null);
		}
		else if (e.getSource() == helpItem)
		{
			JOptionPane.showMessageDialog(this, "Test");
		}
		
		else if (e.getSource() == zed)
		{
			if (buffer != "0")
			{
				addToBuffer("0");
				displayBuffer();
				convertBufferToBinary();
				displayBinaryBuffer();
			}
		}
		else if (e.getSource() == one)
		{
			addToBuffer("1");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == two)
		{
			addToBuffer("2");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == three)
		{
			addToBuffer("3");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == four)
		{
			addToBuffer("4");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == five)
		{
			addToBuffer("5");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == six)
		{
			addToBuffer("6");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == seven)
		{
			addToBuffer("7");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == eight)
		{
			addToBuffer("8");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == nine)
		{
			addToBuffer("9");
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == backspace)
		{
			if (buffer.length() == 1)
			{
				buffer = "0";
			}
			else
			{
				buffer = buffer.substring(0, buffer.length() - 1);
			}
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == clear)
		{
			buffer = "0";
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == ce)
		{
			buffer = "0";
			total = "0";
			//number1 = "0";
			//number2 = "0";
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == plus)
		{
			//TODO: if last operation was equals, set the buffer equal to the total unless the user enters another number
			if (lastOperation == "equals")
			{
				buffer = total;
			}
			long totalLong = Long.parseLong(buffer) + Long.parseLong(total);
			total = totalLong + "";
			displayTotal();
			buffer = "0";
			lastOperation = "add";
		}
		else if (e.getSource() == minus)
		{
			
		}
		else if (e.getSource() == plusMinus)
		{
			buffer = -Long.parseLong(buffer) + "";
			displayBuffer();
			convertBufferToBinary();
			displayBinaryBuffer();
		}
		else if (e.getSource() == equals)
		{
			if (lastOperation == "add")
			{				
				total = (Long.parseLong(buffer) + Long.parseLong(total)) + "";
				displayTotal();
				convertBufferToBinary();
				displayBinaryBuffer();
				lastOperation = "equals";
			}
			buffer = "0";
		}
		else if (e.getSource() == hexButton)
		{
			if (currentFormat == "dec")
			{
				buffer = convertDecToHex();				
			}
			else if (currentFormat == "oct")
			{
				buffer = convertOctToHex();
			}
			else if (currentFormat == "bin")
			{
				buffer = convertBinToHex();
			}
			currentFormat = "hex";
			displayBuffer();
			
			a.setEnabled(true);
			b.setEnabled(true);
			c.setEnabled(true);
			d.setEnabled(true);
			this.e.setEnabled(true);
			f.setEnabled(true);
		}
		else if (e.getSource() == octButton)
		{
			if (currentFormat == "dec")
			{
				buffer = convertDecToOct();
			}
			else if (currentFormat == "hex")
			{
				buffer = convertHexToOct();
			}
			else if (currentFormat == "bin")
			{
				buffer = convertBinToOct();
			}
			currentFormat = "oct";
			displayBuffer();
			
			if (a.isEnabled())
			{
				a.setEnabled(false);
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				this.e.setEnabled(false);
				f.setEnabled(false);
			}
		}
		else if (e.getSource() == binButton)
		{
			if (currentFormat == "dec")
			{
				buffer = convertDecToBin();
			}
			else if (currentFormat == "hex")
			{
				buffer = convertHexToBin();
			}
			else if (currentFormat == "oct")
			{
				buffer = convertOctToBin();
			}
			currentFormat = "bin";
			displayBuffer();
			
			if (a.isEnabled())
			{
				a.setEnabled(false);
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				this.e.setEnabled(false);
				f.setEnabled(false);
			}
		}
		else if (e.getSource() == decButton)
		{
			if (currentFormat == "hex")
			{
				buffer = convertHexToDec();
			}
			else if (currentFormat == "oct")
			{
				buffer = convertOctToDec();
			}
			else if (currentFormat == "bin")
			{
				buffer = convertBinToDec();
			}
			currentFormat = "dec";
			displayBuffer();
			
			if (a.isEnabled())
			{
				a.setEnabled(false);
				b.setEnabled(false);
				c.setEnabled(false);
				d.setEnabled(false);
				this.e.setEnabled(false);
				f.setEnabled(false);
			}
		}
	}

	public String convertHexToDec()
	{
		return new BigInteger(buffer, 16).toString(10);
		//return Long.toString(Long.parseLong(buffer, 16));
	}
	
	public String convertOctToDec()
	{
		return new BigInteger(buffer, 8).toString(10);
		//return Long.toString(Long.parseLong(buffer, 8));
	}
	
	public String convertBinToDec()
	{
		return new BigInteger(buffer, 2).toString(10);
		//return Long.toString(Long.parseLong(buffer, 2));
	}
	
	public String convertDecToOct()
	{
		return new BigInteger(buffer, 10).toString(8);
		//return Long.toOctalString(Long.parseLong(buffer));
	}
	
	public String convertHexToOct()
	{
		return new BigInteger(buffer, 16).toString(8);
		//return Long.toOctalString(Long.parseLong(buffer, 16));
	}
	
	public String convertBinToOct()
	{
		return new BigInteger(buffer, 2).toString(8);
		//return Long.toOctalString(Long.parseLong(buffer, 2));
	}
	
	public String convertDecToHex()
	{
		return new BigInteger(buffer, 10).toString(16);
		//return Long.toHexString(Long.parseLong(buffer));
	}
	
	public String convertOctToHex()
	{	
		return new BigInteger(buffer, 8).toString(16);
		//return Long.toHexString(Long.parseLong(buffer, 8));
	}
	
	public String convertBinToHex()
	{
		return new BigInteger(buffer, 2).toString(16);
		//return value.toString(16);
		//return Long.toHexString(Long.parseLong(buffer, 2));
	}
	
	public String convertDecToBin()
	{
		return new BigInteger(buffer, 10).toString(2);
		//return Long.toBinaryString(Long.parseLong(buffer));
	}
	
	public String convertHexToBin()
	{
		return new BigInteger(buffer, 16).toString(2);
		//return Long.toBinaryString(Long.parseLong(buffer, 16));
	}
	
	public String convertOctToBin()
	{
		return new BigInteger(buffer, 8).toString(2);
		//return Long.toBinaryString(Long.parseLong(buffer, 8));
	}
	
	public void addToBuffer(String num)
	{
		if (buffer == "0")
		{
			buffer = num;
		}
		else
		{
			buffer += num;
		}
	}
	
	public boolean isInt(String number)
	{
		//Figure out if the given number is an integer (has decimal).
		for (int i = 0; i < number.length(); ++i)
		{
			if (number.charAt(i) == ',')
			{
				return true;
			}
		}
		return false;
	}
	
	public void displayBuffer()
	{
		resultField.setText(buffer);
	}
	
	public void displayTotal()
	{
		resultField.setText(total);
	}
	
	public void convertBufferToBinary()
	{
		//if (Integer.parseInt(buffer) <= 0)
		//{
			//If the current number is negative, don't convert to binary
		//	binaryString = "0000000000000000000000000000000000000000000000000000000000000000";
		//}	

		if (!binButton.isSelected())
		{
			//Convert buffer only if binary mode is not selected
			binaryString = Long.toBinaryString(Long.parseLong(buffer));
			int binLength = binaryString.length();
			if (binLength != 64)
			{
				for (int i = 0; i < (64 - binLength); ++i)
				{
					binaryString = "0" + binaryString;
				}
			}
		}
		else
		{
			binaryString = buffer;
			int binLength = binaryString.length();
			if (binLength != 64)
			{
				for (int i = 0; i < (64 - binLength); ++i)
				{
					binaryString = "0" + binaryString;
				}
			}
			displayBinaryBuffer();
		}
	}
	
	public void displayBinaryBuffer()
	{
		//Replace characters in bit display with the correct substrings of the binary string
		int characterCount = -1;
		for (int i = 0; i < 80; i += 5)
		{
			//if (i < 36) //We are still on the first row
			//{
			//int length = hexField.getText().length();
				for (int j = 0; j < 4; ++j)
				{
					//For each number of the binary string, replace the character of the hexField at its correct location
					++characterCount;
					String currentChar = binaryString.substring(binaryString.length() - (characterCount + 1), binaryString.length() - (characterCount));
					if (characterCount >= 32)
					{
						hexField.replaceRange(currentChar, hexField.getText().length() - (80 + i + j + 1), hexField.getText().length() - (80 + i + j));						
					}
					else
					{						
						hexField.replaceRange(currentChar, hexField.getText().length() - (40 + i + j + 1), hexField.getText().length() - (40 + i + j));
					}
				}
			//}
			//else if (i >= 36) //We are on the second row
			//{
				//for (int j = 0; j < 4; ++j)
				//{
					//For each number of the binary string, replace the character of the hexField at its correct location
					//++characterCount;
					//String currentChar = binaryString.substring(binaryString.length() - (characterCount + 1), binaryString.length() - (characterCount));
				//}
			//}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub

		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public void getClipboard()
    {
        Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        clipboard = defaultToolkit.getSystemClipboard();
    }
}
