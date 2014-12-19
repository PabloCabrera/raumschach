package raumschach.view;

import java.awt.Image;

class Sprite {
	private int posX;
	private int posY;
	private Image image;

	public Sprite (Image image, int posX, int posY) {
		this.image = image;
		this.posX = posX;
		this.posY = posY;
	}

	public int getX () {
		return posX;
	}

	public int getY () {
		return posY;
	}

	public Image getImage () {
		return this.image;
	}
}
