import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SystemOutUtils {

	private static final String LINE = "\n";

	/**
	 * 打印
	 *
	 * @param text
	 */
	public static void println(String text) {
		System.out.println(text);
	}

	/**
	 * 验证
	 *
	 * @param output
	 */
	public static ByteArrayOutputStream convert(ByteArrayOutputStream output) {
		ByteArrayOutputStream newOutput = new ByteArrayOutputStream();
		for (String item : output.toString().split(LINE)) {
			byte[] bytes = item.getBytes();
			try {
				newOutput.write(bytes);
				newOutput.write(LINE.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newOutput;
	}

}