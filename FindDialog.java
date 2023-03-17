package edu.najah.cap;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial" )
public class FindDialog extends JDialog implements ActionListener, KeyListener {

	Editor root;
	JLabel label;
	JTextField textField;
	JCheckBox caseSensitive;
	JButton find;
	JButton close;
	boolean finishedFinding = true;
	transient Matcher matcher;


	public FindDialog(Editor parent, boolean modal) { // R
		super(parent, modal);
		this.root = parent;
		getContentPane().addKeyListener(this);
		getContentPane().setFocusable(true);
		initComponents();
		setTitle("Find");
		setLocationRelativeTo(parent);
		pack();
	}

	public void showDialog() { // R
		setVisible(true);
	}

	private void initComponents() {
		setLayout(new GridLayout(3, 1));
		JPanel panel1 = new JPanel();
		label = new JLabel("Find : ");
		label.setDisplayedMnemonic('F');
		panel1.add(label);
		textField = new JTextField(15);
		panel1.add(textField);
		label.setLabelFor(textField);
		add(panel1);
		JPanel panel2 = new JPanel();
		caseSensitive = new JCheckBox("Case sensitive");
		panel2.add(caseSensitive);
		add(panel2);
		JPanel panel3 = new JPanel();
		find = new JButton("Find");
		close = new JButton("Close");
		find.addActionListener(this);
		close.addActionListener(this);
		panel3.add(find);
		panel3.add(close);
		add(panel3);
		textField.addKeyListener(this);
		find.addKeyListener(this);
		close.addKeyListener(this);
		caseSensitive.addKeyListener(this);
	}

	private void find(String pattern) {
		if (!finishedFinding) {
			if (matcher.find()) {
				int selectionStart = matcher.start();
				int selectionEnd = matcher.end();
				root.TP.moveCaretPosition(matcher.start());
				root.TP.select(selectionStart, selectionEnd);
			} else {
				finishedFinding = true;
				JOptionPane.showMessageDialog(this, "You have reached the end of the file", "End of file",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			matcher = Pattern.compile(pattern).matcher(root.TP.getText());
			finishedFinding = false;
			find(pattern);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //R
		String cmd = e.getActionCommand();
		if (cmd.equals("Find")) {
			String input = textField.getText();
			StringBuilder pattern = new StringBuilder();
			if (!caseSensitive.isSelected()) {
				pattern.append("(?i)");
			}
			pattern.append(input);
			find(pattern.toString());
		} else if (cmd.equals("Close")) {
			closeDialog();
		}
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// nothing happened when type a key

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			closeDialog();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//nothing will happen when a key is released on that component  M

	}

}