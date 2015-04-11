import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


@SuppressWarnings("serial")
public class Block extends Rectangle{
	Direction direction;
	/**
	 * Initializes each block
	 * default direction is RIGHT
	 * visibility false;
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Block(int x, int y, int width, int height) {
		super(x,y,width,height);
		this.direction = Direction.RIGHT;
	}
	/**
	 * Move each block according to their direction
	 */
	public void update() {
		
		if(direction.equals(Direction.LEFT)) {
			x = x == 0 ? 380 : x-20;
		} else if(direction.equals(Direction.RIGHT)) {
			x = x == 380 ? 0 : x+20;
		} else if(direction.equals(Direction.UP)) {
			y = y == 0 ? 380 : y-20;
		} else {
			y = y == 380 ? 0 : y+20;
		}
	}
	/**
	 * 
	 * @param g paint each block in graphics mode
	 */
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(this.x, this.y, this.width, this.height);
	}

}
