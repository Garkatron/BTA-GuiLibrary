package deus.builib.guimanagement.routing;

import deus.builib.gssl.Signal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages navigation between different pages in the application.
 * Allows for route registration, page navigation, and rendering of the current page.
 */
public class Router {
	private final Map<String, Page> routes = new HashMap<>();
	int index = 0;
	private Page previousPage;
	private Page currentPage;
	public Signal<Page> onChange = new Signal<>();

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
	 * Registers a route with a specific index and path.
	 *
	 * @param i    The index to associate with the route.
	 * @param path The path to associate with the page.
	 * @param page The page to be associated with the path.
	 */
	public void registerRoute(int i, String path, Page page) {
		routes.put(i + "º" + path, page);
	}

	/**
	 * Registers a route with a specific index and page.
	 *
	 * @param i    The index to associate with the route.
	 * @param page The page to be associated with the index.
	 */
	public void registerRoute(int i, Page page) {
		routes.put(String.valueOf(i), page);
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
			previousPage = currentPage;
			currentPage = page;
			onChange.emit(currentPage);
		} else {
			System.out.println("404: Page not found: " + path);
		}
	}

	/**
	 * Navigates back to the previous page, if available.
	 */
	public void backPreviousPage() {
		if (previousPage != null) {
			currentPage = previousPage;
			onChange.emit(currentPage);
		} else {
			System.out.println("404: Page not found");
		}
	}

	/**
	 * Navigates to the previous route based on the index.
	 * If the index is out of bounds, it wraps around to the last route.
	 */
	public void back() {
		index--;

		if (index < 0) {
			index = routes.size() - 1;
		}
		// System.out.println("Current index: " + index);

		String[] keys = routes.keySet().toArray(new String[0]);
		System.out.println("Available keys: " + Arrays.toString(keys));

		String key = Arrays.stream(keys)
			.filter(s -> s.startsWith(index + "º"))
			.findFirst()
			.orElse(null);

		if (key != null && routes.containsKey(key)) {
			currentPage = routes.get(key);
			onChange.emit(currentPage);
		} else {
			System.out.println("404: Page not found: " + key);
		}
	}

	/**
	 * Navigates to the next route based on the index.
	 * If the index exceeds the number of routes, it wraps around to the first route.
	 */
	public void next() {
		index++;
		if (index >= routes.size()) {
			index = 0;
		}

		System.out.println("Current index: " + index);

		String[] keys = routes.keySet().toArray(new String[0]);
		System.out.println("Available keys: " + Arrays.toString(keys));

		String key = Arrays.stream(keys)
			.filter(s -> s.startsWith(index + "º"))
			.findFirst()
			.orElse(null);

		if (key != null && routes.containsKey(key)) {
			currentPage = routes.get(key);
			onChange.emit(currentPage);
		} else {
			System.out.println("404: Page not found: " + key);
		}
	}

	/**
	 * Renders the current page. This method should be called in a loop to continuously
	 * render the current page.
	 */
	public void renderCurrentPage() {
		if (currentPage != null) {
			currentPage.render();
			// System.out.println(currentPage.getClass().getSimpleName());
		}
	}

	/**
	 * Updates the current page.

	 */
	public void updatePage() {
		if (currentPage != null) {
			currentPage.update();
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
