package soict.hedspi.itss2.gyatto.moneysavior;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoneySaviorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneySaviorApplication.class, args);
	}

}
