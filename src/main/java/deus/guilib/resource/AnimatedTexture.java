package deus.guilib.resource;

import net.minecraft.client.Minecraft;

public class AnimatedTexture extends Texture {

	private final int[][] frames;
	private final float animSpeed;
	private int currentFrame = 0;
	private float timeElapsed = 0.0f;
	private long lastUpdateTime = 0;
	private boolean isAnimating = false;

	public AnimatedTexture(int width, int height, int[][] frames, String path) {
		this(width, height, 0.5f, frames, path);
	}

	public AnimatedTexture(int width, int height, float animSpeed, int[][] frames, String path) {
		super(path, width, height);
		this.frames = frames;
		this.animSpeed = animSpeed;
		this.lastUpdateTime = System.currentTimeMillis();
	}

	public AnimatedTexture(int width, int height, int totalTextureSize, float uvScale, int[][] frames, String path) {
		super(path, width, height, totalTextureSize, uvScale);
		this.frames = frames;
		this.animSpeed = 0.5f; // Default animation speed
		this.lastUpdateTime = System.currentTimeMillis();
	}

	public void startAnimation() {
		isAnimating = true;
	}

	public void stopAnimation() {
		isAnimating = false;
	}

	public void update() {
		if (isAnimating) {
			long currentTime = System.currentTimeMillis();
			float deltaTime = (currentTime - lastUpdateTime) / 1000.0f;

			timeElapsed += deltaTime;

			if (timeElapsed >= animSpeed) {
				timeElapsed -= animSpeed;
				currentFrame = (currentFrame + 1) % frames.length;
				this.offsetX = frames[currentFrame][0];
				this.offsetY = frames[currentFrame][1];
			}

			lastUpdateTime = currentTime;
		}
	}

	@Override
	public void draw(Minecraft mc, int x, int y) {
		update();
		super.draw(mc, x, y);
	}

	@Override
	public void draw(Minecraft mc, int x, int y, int w, int h) {
		update();
		super.draw(mc, x, y, w, h);
	}
}
