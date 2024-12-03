package deus.guilib.nodes.stylesystem;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YAMLProcessor {

	public static Map<String, Object> read(String path) {
		Yaml file = new Yaml();
		Map<String, Object> data;
		try (FileInputStream istream = new FileInputStream (path)) {
			data = file.load(istream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return data;
	}

	public static Map<String, Object> read(FileInputStream fInputStream) {
		Yaml file = new Yaml();
		Map<String, Object> data = file.load(fInputStream);
		return data;
	}
	public static Map<String, Object> read(InputStream fInputStream) {
		Yaml file = new Yaml();
		Map<String, Object> data = file.load(fInputStream);
		return data;
	}

}
