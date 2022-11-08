package br.com.kopili.config;

import br.com.kopili.utility.AlgorithmUtil;
import br.com.kopili.utility.AuthorizeAlwaysRoutesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansAgrupation {

    @Autowired
    private Environment environment;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AlgorithmUtil algorithmUtil(){
        return new AlgorithmUtil(environment);
    }

    @Bean
    public AuthorizeAlwaysRoutesUtil authorizeAlwaysRoutesUtil(){
        return new AuthorizeAlwaysRoutesUtil();
    }


}
