import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SnakeBoard extends JPanel implements Runnable {
	
	private Vector<Block> block;
	private Block head;
	private Thread thread;
	boolean gameover;
	private int N = 6;
	private Rectangle food;
	private Random r;
	
	public SnakeBoard() {
		setSize(400, 400);
		setBackground(Color.BLACK);
		thread = new Thread(this);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt ) {
				actionPerformed(evt);
			}
		});
		initComponents();
		gameover = false;
	}
	/**
	 * 
	 * @return
	 */
	void isAlive() {
		for (int i = 0; i < N-1; i++) {
			if(block.get(i).x == block.get(N-1).x && block.get(i).y == block.get(N-1).y) {
				gameover = true;
				JOptionPane.showMessageDialog(this, "Game Over !\n Your Score " + (N-6));
				break;
			}
		}
	}
	/**
	 * Painting snake body , food and Score
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); 
		for (int i = 0; i < N; i++) {
			block.get(i).paint(g);
			block.get(i).update();
		}
		g.setColor(Color.RED);
		g.fillRect(food.x, food.y, food.width, food.height);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Score : "+(N-6), 300, 20);
		updateDirection();
	}
	/**
	 * 
	 * @param evt when any key event occurs this method get invoked
	 */
	private void actionPerformed(KeyEvent evt) {
		switch(evt.getKeyCode()){
		case KeyEvent.VK_LEFT:
			if( block.get(N-1).direction != Direction.RIGHT) {
				block.get(N-1).direction = Direction.LEFT;
			}
			break;
		case KeyEvent.VK_RIGHT:
			if( block.get(N-1).direction != Direction.LEFT) {
				block.get(N-1).direction = Direction.RIGHT;
			}
			break;
		case KeyEvent.VK_UP:
			if( block.get(N-1).direction != Direction.DOWN) {
				block.get(N-1).direction = Direction.UP;
			}
			break;
		case KeyEvent.VK_DOWN:
			if( block.get(N-1).direction != Direction.UP) {
				block.get(N-1).direction = Direction.DOWN;
			}
			break;
		default:
		}
	}
	@Override
	public void run() {
		while(!gameover) {
			// checking food is eaten or not
			if(block.get(N-1).x  == food.x && block.get(N-1).y == food.y) {
				getFood();
				head = new Block(block.get(N-1).x, block.get(N-1).y, block.get(N-1).width, block.get(N-1).height);
				head.direction = block.get(N-1).direction;
				N++;    // snake size increases
				head.update();
				block.add(head);
			}
			isAlive();
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}
	}
	/**
	 * initializes snake body
	 */
	private void initComponents() {
		block = new Vector<Block>();
		for (int i = 0; i < N; i++) {
			block.add(new Block(140+20*i, 200 , 18, 18));
		}
		r = new Random();
		getFood();
	}
	/**
	 * generates a new food item when invoked
	 */
	public void getFood(){
		
		food = new Rectangle(r.nextInt(20)* 20, r.nextInt(20)*20, 18, 18);
	}
	/**
	 * Update direction of each block to its next block
	 * such that it follow head
	 */
	private void updateDirection() {
		for (int i = 0; i < N-1; i++) {
			block.get(i).direction = block.get(i+1).direction;
		}
	}
	/**
	 * starts the thread
	 */
	public void start() {
		thread.start();
	}
}
