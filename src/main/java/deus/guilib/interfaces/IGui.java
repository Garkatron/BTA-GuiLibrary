package deus.guilib.interfaces;

public interface IGui {

	/**
	 * Updates the GUI based on the current resolution and mouse coordinates.
	 * Emits a resize signal if the dimensions change.
	 */
	void update();

}
