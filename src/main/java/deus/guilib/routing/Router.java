package deus.guilib.routing;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages navigation between different pages in the application.
 * Allows for route registration, page navigation, and rendering of the current page.
 */
public class Router {
	private final Map<String, Page> routes = new HashMap<>();
	private Page currentPage;

	/**
	 * Registers a route with a specific page.
	 *
	 * @param path The path to associate with the page.
	 * @param page The page to be associated with the path.
	 */
	public void registerRoute(String path, Page page) {
		routes.put(path, page);
	}

	/**
	 * Navigates to a specific route. Sets the current page to the page associated
	 * with the given path.
	 *
	 * @param path The path to navigate to.
	 */
	public void navigateTo(String path) {
		Page page = routes.get(path);
		if (page != null) {
			currentPage = page;
		} else {
			System.out.println("404: Page not found");
		}
	}

	/**
	 * Renders the current page. This method should be called in a loop to continuously
	 * render the current page.
	 */
	public void renderCurrentPage() {
		if (currentPage != null) {
			currentPage.render();
			//System.out.println(currentPage.getClass().getSimpleName());
		}
	}

	/**
	 * Updates the current page with the mouse coordinates.
	 *
	 * @param mx The mouse X coordinate.
	 * @param my The mouse Y coordinate.
	 */
	public void updatePage(int mx, int my) {
		if (currentPage != null) {
			currentPage.update();
			currentPage.setMouseX(mx);
			currentPage.setMouseY(my);
		}
	}

	/**
	 * Returns the current page.
	 *
	 * @return The current page, or null if no page is currently selected.
	 */
	public Page getCurrentPage() {
		return currentPage;
	}
}
