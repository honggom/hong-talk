package hong.gom.hongtalk.util;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SHA256Test {
	
	@Autowired
	SHA256 sha256;

	@Test
	void SHA256_암호화가_되는지_테스트() throws Exception{
		String plainText = "hong";
		String encryptedText = sha256.encrypt(plainText);
		
		System.out.println(plainText);
		System.out.println(encryptedText);
		
		assertNotEquals(plainText, encryptedText);
	}

}
