package br.com.kopili;

import br.com.kopili.utility.AlgorithmUtil;
import br.com.kopili.utility.AuthorizeAlwaysRoutesUtil;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaBackendApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private Environment environment;

	@Bean
	public AlgorithmUtil algorithmUtil(){
		return new AlgorithmUtil(environment);
	}

	@Bean
	public AuthorizeAlwaysRoutesUtil authorizeAlwaysRoutesUtil(){
		return new AuthorizeAlwaysRoutesUtil();
	}

}
