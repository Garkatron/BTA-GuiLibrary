package deus.guilib.element.elements.representation;

import deus.guilib.element.Node;
import deus.guilib.interfaces.element.INode;
import deus.guilib.resource.Texture;

public class ProgressBar extends Node {

	protected int progress = 0;  // Represents the progress from 0 to 100
	protected Texture fullTexture;
	private long previousTime = System.currentTimeMillis();
	private float currentProgress = 0f;  // For smooth transition
	private float smoothingFactor = 0.1f;  // For smoother transition

	public INode setFullTexture(Texture fullTexture) {
		this.fullTexture = fullTexture;
		return this;
	}

	public int getProgress() {
		return progress;
	}

	public ProgressBar setSmoothingFactor(float smoothingFactor) {
		this.smoothingFactor = smoothingFactor;
		return this;
	}

	@Override
	protected void drawIt() {
		super.drawIt();
		long currentTime = System.currentTimeMillis();
		float delta = (currentTime - previousTime) / 1000f;

		currentProgress += (progress - currentProgress) * smoothingFactor;

		int filledWidth = (int) (currentProgress / 100f * getWidth());
		filledWidth = Math.min(filledWidth, getWidth());

		fullTexture.draw(mc,gx,gy,filledWidth, getHeight());
		previousTime = currentTime;
	}

	public void setProgress(int progress) {
		this.progress = Math.max(0, Math.min(progress, 100));  // Clamp the progress between 0 and 100
	}
}
