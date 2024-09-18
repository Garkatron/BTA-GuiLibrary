package deus.guilib.resource;

import net.minecraft.client.Minecraft;

/**
 * Represents an animated texture with a series of offsets for frame-by-frame animation.
 */
public class AnimatedTexture extends Texture {

	private final int[][] frames; // Offsets for animation frames
	private final float animSpeed; // Time in seconds per frame
	private int currentFrame = 0;
	private float timeElapsed = 0.0f;
	private long lastUpdateTime = 0;
	private boolean isAnimating = false;

	// Constructor with default animation speed and offsets
	public AnimatedTexture(int width, int height, int[][] frames, String path) {
		this(width, height, 0.5f, frames, path); // Default animation speed of 0.5 seconds per frame
	}

	// Constructor with specified animation speed and frames
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

	// Start the animation
	public void startAnimation() {
		isAnimating = true;
	}

	// Stop the animation
	public void stopAnimation() {
		isAnimating = false;
	}

	// Update the animation based on elapsed time
	public void update() {
		if (isAnimating) {
			long currentTime = System.currentTimeMillis();
			float deltaTime = (currentTime - lastUpdateTime) / 1000.0f; // Convert milliseconds to seconds

			timeElapsed += deltaTime;

			if (timeElapsed >= animSpeed) {
				timeElapsed -= animSpeed;
				currentFrame = (currentFrame + 1) % frames.length;
				// Update the offsets to the current frame's offsets
				this.offsetX = frames[currentFrame][0];
				this.offsetY = frames[currentFrame][1];
			}

			lastUpdateTime = currentTime; // Update the last update time
		}
	}

	// Draw method overridden to include animation update
	@Override
	public void draw(Minecraft mc, int x, int y) {
		// Update the animation
		update();
		super.draw(mc, x, y); // Use the draw method from Texture
	}

	// Overloaded draw method for specifying width and height
	@Override
	public void draw(Minecraft mc, int x, int y, int w, int h) {
		// Update the animation
		update();
		super.draw(mc, x, y, w, h); // Use the draw method from Texture
	}
}
