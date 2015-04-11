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
	public Thread thread;
	boolean gameover;
	private int N = 6;
	private Rectangle food;
	private Random r;
	private volatile boolean pause = false;
	//constructor declaration
	public SnakeBoard() {
		setSize(400, 400);
		setBackground(Color.BLACK);
		setFocusable(true);
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
	synchronized void isAlive() {
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
		g.fillOval(food.x, food.y, food.width, food.height);
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
			if(!pause) {
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
				//System.out.println("Snake Running");
			}
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
		int foodX = r.nextInt(20)* 20;
		int foodY = r.nextInt(20)* 20;
		if(!isLieOnSnakeBody(foodX, foodY)) 
			food = new Rectangle(foodX, foodY, 18, 18);
		else
			getFood();
	}
	private boolean isLieOnSnakeBody(int foodX, int foodY) {
		for (Block block2 : block) {
			if(block2.x == foodX && block2.y == foodY)
				return true;
		}
		return false;
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
	public boolean isPause(){
		return pause;
	}
	public void setPause() {
		this.pause = true;
		//System.out.println("set pause");
	}
	public void setResume() {
		this.pause = false;
		//System.out.println("set resume");
	}
}
