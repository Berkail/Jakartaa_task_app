package Utilities;

public class JsonUtil {
	public static String escapeJsonString(String input) {
		if (input == null) {
			return "";
		}
		return input.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}
