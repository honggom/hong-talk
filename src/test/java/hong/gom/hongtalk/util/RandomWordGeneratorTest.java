package hong.gom.hongtalk.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomWordGeneratorTest {
	
	@Test
	void 문자열의_길이를_받고_해당_길이로_랜덤_문자열을_반환한다() {
		// given, when
		String s1 = RandomWordGenerator.generateRandomWord(10);
		
		// then
		assertEquals(10, s1.length());
	}
}
