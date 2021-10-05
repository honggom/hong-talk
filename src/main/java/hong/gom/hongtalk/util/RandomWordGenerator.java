package hong.gom.hongtalk.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomWordGenerator {

	public static String generateRandomWord(int wordLength) {

		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < wordLength; i++) {
			if (random.nextBoolean()) {
				sb.append((char) ((int) (random.nextInt(26)) + 97));
			} else {
				sb.append((random.nextInt(10)));
			}
		}
		return sb.toString();
	}
}
