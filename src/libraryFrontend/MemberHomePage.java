package libraryFrontend;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class MemberHomePage extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public MemberHomePage() {
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(1100, 750));
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 1100, 77);
		add(panel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(MemberHomePage.class.getResource("/adminIcons/icons8-book-48.png")));
		lblNewLabel.setBounds(419, 11, 50, 50);
		panel.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(419, 59, 362, 4);
		panel.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to Online Library");
		lblNewLabel_1.setPreferredSize(new Dimension(150, 14));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Yu Gothic Medium", Font.BOLD, 18));
		lblNewLabel_1.setBounds(491, 20, 253, 46);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(MemberHomePage.class.getResource("/icons/library-3.png.png")));
		lblNewLabel_2.setBounds(0, 77, 1100, 618);
		add(lblNewLabel_2);

	}

}
