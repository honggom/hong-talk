package hong.gom.hongtalk.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomWordGeneratorTest {
	
	@Test
	void 랜덤문자열이_생성되는지_테스트() {
		// given, when
		String s1 = RandomWordGenerator.generateRandomWord(10);
		
		// then
		assertEquals(10, s1.length());
	}
}
