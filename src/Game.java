import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Game extends JFrame {
	
	SnakeBoard board;
	JButton btnReset, btnPause;
	JPanel pnl; JLabel lbl;
	public Game() {
		super("Snake");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(410, 480);
		
		lbl =new JLabel("Snake Game");
		lbl.setHorizontalAlignment(JLabel.CENTER);
		pnl = new JPanel();
		/* button customization */
		btnReset = new JButton("Reset");
		btnReset.setSize(100, 40);
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				new Game();
			}
		});
		
		btnPause = new JButton("Pause");
		btnPause.setSize(100, 40);
		btnPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		pnl.add(btnPause);
		pnl.add(btnReset);
		board = new SnakeBoard();
		getContentPane().add(lbl, BorderLayout.NORTH);
		getContentPane().add(pnl, BorderLayout.SOUTH);
		getContentPane().add(board, BorderLayout.CENTER);
		board.setFocusable(true);
		board.requestFocus(true);
		requestFocusInWindow();
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		Game g = new Game();
		g.board.start();
	}
}
