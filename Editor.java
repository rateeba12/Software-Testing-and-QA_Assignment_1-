package edu.najah.cap;
import edu.najah.cap.ex.EditorSaveException;
import edu.najah.cap.ex.CanNotWriteFileException;

import java.io.*;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Editor extends JFrame implements ActionListener, DocumentListener {

	private static final String ACTION_1 = "Cannot write file!";
	private static final Logger logger = Logger.getLogger(Editor.class.getName());

	private void saveAs(String dialogTitle) {
		dialogTitle = dialogTitle.toUpperCase();
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setDialogTitle(dialogTitle);
		int result = dialog.showSaveDialog(this);

		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		file = dialog.getSelectedFile();

		try (PrintWriter writer = getWriter(file)) {
			writer.write(textPanel.getText());
			changed = false;
			setTitle("Editor - " + file.getName());
		} catch (IOException e) {
			showErrorDialog(e);
		}
	}

	public static  void main(String[] args) {
		new Editor();
	}

	protected  JEditorPane textPanel;//Text Panel
	JMenuBar menu;//Menu
	JMenuItem copy;
	JMenuItem paste;
	JMenuItem cut;

	boolean changed = false;
	protected File file;

	private final String[] actions = {"Open","Save","New","Edit","Quit", "Save as..."};

	protected JMenu jmFile;

	public Editor() {
		//Editor the name of our application
		super("Editor");
		textPanel = new JEditorPane();
		// center means middle of container.
		add(new JScrollPane(textPanel), "Center");
		textPanel.getDocument().addDocumentListener(this);

		menu = new JMenuBar();
		setJMenuBar(menu);
		buildMenu();
		//The size of window
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void buildMenu() {
		buildFileMenu();
		buildEditMenu();
	}

	private void buildFileMenu() {
		jmFile = new JMenu("File");
		jmFile.setMnemonic('F');
		menu.add(jmFile);
		JMenuItem n = new JMenuItem(actions[2]);
		n.setMnemonic('N');
		n.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		n.addActionListener(this);
		jmFile.add(n);
		JMenuItem open = new JMenuItem(actions[0]);
		jmFile.add(open);
		open.addActionListener(this);
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		JMenuItem save = new JMenuItem(actions[1]);
		jmFile.add(save);
		save.setMnemonic('S');
		save.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem saveAs = new JMenuItem(actions[5]);

		saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));


		jmFile.add(saveAs);


		saveAs.addActionListener(this);
		JMenuItem quit = new JMenuItem(actions[4]);
		jmFile.add(quit);
		quit.addActionListener(this);
		quit.setMnemonic('Q');
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private void buildEditMenu() {
		JMenu edit = new JMenu(actions[3]);
		menu.add(edit);
		edit.setMnemonic('E');
		// cut
		cut = new JMenuItem("Cut");
		cut.addActionListener(this);
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		cut.setMnemonic('T');
		edit.add(cut);
		// copy
		copy = new JMenuItem("Copy");
		copy.addActionListener(this);
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		edit.add(copy);
		// paste
		paste = new JMenuItem("Paste");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		edit.add(paste);
		paste.addActionListener(this);
		//move

		// find
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic('F');
		find.addActionListener(this);
		edit.add(find);
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
		// select all
		JMenuItem selectAll = new JMenuItem("Select All");
		selectAll.setMnemonic('A');
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		selectAll.addActionListener(this);
		edit.add(selectAll);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(actions[4])) {
			System.exit(0);
		}
		else if (action.equals(actions[0])) {
			loadFile();
		}
		else if (action.equals(actions[1])) actionOne() ;
		else if (action.equals(actions[2])) actionTow() ;
		else if (action.equals(actions[5])) {
			saveAs(actions[5]);
		}
		else if (action.equals("Select All")) {
			textPanel.selectAll();
		} else if (action.equals("Copy")) {
			textPanel.copy();
		} else if (action.equals("Cut")) {
			textPanel.cut();
		} else if (action.equals("Paste")) {
			textPanel.paste();
		} else if (action.equals("Find")) {
			FindDialog find = new FindDialog(this, true);
			find.showDialog();
		}
	}

	private void actionOne() {


		//Save file
		int ans = 0;
		if (changed) {
			// 0 means yes and no option, 2 Used for warning messages.

			ans = ansValue( );
		}
		//1 value from class method if NO is chosen.
		if (ans != 1) {
			if (file == null) {
				saveAs(actions[1]);
			} else {
				String text = textPanel.getText();
				logger.info(text);
				try (PrintWriter writer = new PrintWriter(file)){
					if (!file.canWrite())
						throw new EditorSaveException(ACTION_1);
					writer.write(text);
					changed = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	private int ansValue() {
		int res;
		res= JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		return res ;}


	private void actionTow( ) {

		if (changed) {
			int ans=ansValue();
			//1 value from class method if NO is chosen.
			if (ans == 1)
				return;

			if (file == null) {
				saveAs(actions[1]);
				return;
			}
			String text = textPanel.getText();
			logger.info(text);
			try (PrintWriter writer = new PrintWriter(file)){
				if (!file.canWrite())
					throw new CanNotWriteFileException(ACTION_1);
				writer.write(text);
				changed = false;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		file = null;
		textPanel.setText("");
		changed = false;
		setTitle("Editor");
	}

	private void loadFile() {
		JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
		dialog.setMultiSelectionEnabled(false);

		try {
			int result = dialog.showOpenDialog(this);

			if (result == 1) { // 1 value if cancel is chosen.
				return;
			}

			if (result == 0) { // value if approve (yes, ok) is chosen.
				checkChangedAndReadFile(dialog.getSelectedFile());
			}

		} catch (Exception e) {
			showErrorDialog(e);
		}
	}
	void checkChanged() {
		// Save file
		if (changed) {
			int ans = ansValue();
			// 0 means yes and no question and 2 mean warning dialog
			if (ans == 1) { // no option
				return;
			}
		} else {
			logger.info("No change");
			return;
		}
		if (file == null) {
			saveAs(actions[1]);
			return;
		}
		String text = textPanel.getText();
		logger.info(text);
		try (PrintWriter writer = new PrintWriter(file)) {
			if (!file.canWrite()) {
				throw new CanNotWriteFileException(ACTION_1);
			}
			writer.write(text);
			changed = false;
		} catch (IOException | CanNotWriteFileException e) {
			e.printStackTrace();
		}
	}

	private void checkChangedAndReadFile(File file) {
		checkChanged();
		StringBuilder rs = new StringBuilder();

		try (FileReader fr = new FileReader(file);
			 BufferedReader reader = new BufferedReader(fr)) {
			String line;
			while ((line = reader.readLine()) != null) {
				rs.append(line).append("\n");
			}

			textPanel.setText(rs.toString());
			changed = false;
			setTitle("Editor - " + file.getName());

		} catch (IOException e) {
			showErrorDialog(e);
		}
	}

	private void showErrorDialog(Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private PrintWriter getWriter(File file) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		return new PrintWriter(bw);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changed = true;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		changed = true;
	}

}
