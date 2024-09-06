package deus.guilib.element.elements;

import deus.guilib.element.Element;
import deus.guilib.element.interfaces.IElement;
import deus.guilib.resource.Texture;
import org.lwjgl.opengl.GL11;

import java.util.Objects;

public class ProgressBar extends Element {

	protected int progress = 0;  // Represents the progress from 0 to 100
	protected Texture fullTexture;
	private long previousTime = System.currentTimeMillis();
	private float currentProgress = 0f;  // For smooth transition
	private float smoothingFactor = 0.1f;  // For smoother transition

	public IElement setFullTexture(Texture fullTexture) {
		this.fullTexture = fullTexture;
		return this;
	}

	public int getProgress() {
		return progress;
	}

	@Override
	protected void drawIt() {
		if (mc == null || gui == null) {
			System.out.println("Error on drawIt, [Minecraft dependency] or [Gui dependency] are [null].");
			return;
		}

		// Time delta for smooth progress transition
		long currentTime = System.currentTimeMillis();
		float delta = (currentTime - previousTime) / 1000f;

		// Adjust current progress towards the target progress smoothly
		currentProgress += (progress - currentProgress) * smoothingFactor;

		// Set up OpenGL configurations for rendering
		GL11.glColor4f(1f, 1f, 1f, 1f);  // Reset color

		if (!Objects.equals(config.getTheme(), "NONE")) {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(
				themeManager.getProperties(config.getTheme()).get(getClass().getSimpleName())
			));
		} else {
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(texture.getPath()));
		}

		// Render the background of the progress bar
		GL11.glDisable(GL11.GL_BLEND);
		gui.drawTexturedModalRect(x, y, texture.getOffsetX(), texture.getOffsetY(), texture.getWidth(), texture.getHeight());

		// Render the filled portion based on progress (horizontal bar)
		int filledWidth = (int) (currentProgress / 100f * texture.getWidth());  // Calculate filled width
		filledWidth = Math.min(filledWidth, texture.getWidth());  // Ensure it doesn't exceed max width

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture(fullTexture.getPath()));

		gui.drawTexturedModalRect(x, y, fullTexture.getOffsetX(), fullTexture.getOffsetY(), filledWidth, fullTexture.getHeight());

		// Update the previous time for smooth animation
		previousTime = currentTime;
	}

	public void setProgress(int progress) {
		this.progress = Math.max(0, Math.min(progress, 100));  // Clamp the progress between 0 and 100
	}
}
