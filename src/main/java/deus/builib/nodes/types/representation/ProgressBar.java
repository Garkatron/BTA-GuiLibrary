package deus.builib.nodes.types.representation;

import deus.builib.nodes.Node;
import deus.builib.util.rendering.TextureProperties;

import java.util.Map;

public class ProgressBar extends Node {

	protected int progress = 0;  // Represents the progress from 0 to 100
	private long previousTime = System.currentTimeMillis();
	private float currentProgress = 0f;  // For smooth transition
	private double smoothingFactor = 0.1f;  // For smoother transition

	public ProgressBar() {
		super();
	}

	public ProgressBar(Map<String, String> attributes) {
		super(attributes);
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = Math.max(0, Math.min(progress, 100));
	}

	public void addProgressAmount(int a) {
		int newProgress = progress + a;
		this.progress = Math.max(0, Math.min(newProgress, 100));
	}

	@Override
	protected void updateIt() {
		super.updateIt();
		updateSmoothingFactor();

	}

	@Override
	protected void drawIt() {
		super.drawIt();
		long currentTime = System.currentTimeMillis();
		float delta = (currentTime - previousTime) / 1000f;

		currentProgress += (float) ((progress - currentProgress) * smoothingFactor);

		int filledWidth = (int) (currentProgress / 100f * getWidth());
		filledWidth = Math.min(filledWidth, getWidth());


		if (styles.containsKey("progressBarFullBackground")) {
			TextureProperties fullBgProps = tgm.getTexture((String) styles.get("progressBarFullBackground"));

			if (fullBgProps.path().equals("transparent")) return;

			//t.draw(mc, gx, gy, filledWidth, getHeight());

			drawTexture(mc, fullBgProps, gx, gy, filledWidth, height);

		}

		previousTime = currentTime;
	}

	protected void updateSmoothingFactor() {
		if (styles.containsKey("progressBarSmoothingFactor")) {
			smoothingFactor = (double) styles.get("progressBarSmoothingFactor");
		}
	}
}
