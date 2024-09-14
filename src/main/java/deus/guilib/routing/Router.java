package deus.guilib.routing;

import deus.guilib.gssl.Signal;
import deus.guilib.util.math.Tuple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

	public void registerRoute(int i, String path, Page page) {
		routes.put(i + "º" + path, page);
	}

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

	public void backPreviousPage() {
		if (previousPage != null) {
			currentPage = previousPage;
			onChange.emit(currentPage);
		} else {
			System.out.println("404: Page not found");
		}
	}

	public void back() {
		index--;

		if (index < 0) {
			index = routes.size()-1;
		}
		// Verificar el valor actual de index
		System.out.println("Current index: " + index);

		// Obtener las claves del mapa
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

	public void next() {
		index++;
		if (index >= routes.size()) {
			index = 0;
		}

		// Verificar el valor actual de index
		System.out.println("Current index: " + index);

		// Obtener las claves del mapa
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
