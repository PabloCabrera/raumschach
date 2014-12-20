package raumschach.view;

import java.awt.Image;

class Sprite {
	private float posX;
	private float posY;
	private Image image;

	public Sprite (Image image, float posX, float posY) {
		this.image = image;
		this.posX = posX;
		this.posY = posY;
	}

	public float getX () {
		return posX;
	}

	public float getY () {
		return posY;
	}

	public Image getImage () {
		return this.image;
	}

	public float getWidth () {
		return this.image.getWidth (null);
	}

	public float getHeight () {
		return this.image.getHeight (null);
	}
}
