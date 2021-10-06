package hong.gom.hongtalk.util;

import java.util.Random;

public class RandomWordGenerator {
	
	private final static int RANDOM_WORD_LENGTH = 10;

	public static String generateRandomWord() {

		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < RANDOM_WORD_LENGTH; i++) {
			if (random.nextBoolean()) {
				sb.append((char) ((int) (random.nextInt(26)) + 97));
			} else {
				sb.append((random.nextInt(10)));
			}
		}
		return sb.toString();
	}
}
