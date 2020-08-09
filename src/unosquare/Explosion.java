package unosquare;

public class Explosion {

	private float x;
	private float y;
	private float r;
	private int maxMines;

	public Explosion(float x, float y, float rad) {
		this.x = x;
		this.y = y;
		this.r = rad;
	}
	
	public int getMaxMines() {
		return maxMines;
	}

	public void setMaxMines(int maxMines) {
		this.maxMines = maxMines;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}

}
