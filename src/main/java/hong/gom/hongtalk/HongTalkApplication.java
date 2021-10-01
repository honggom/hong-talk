package hong.gom.hongtalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Auditing 활성화 
public class HongTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HongTalkApplication.class, args);
	}

}