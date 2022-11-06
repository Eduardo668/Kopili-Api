 package br.com.kopili.security;

 import br.com.kopili.filters.CustomAuthenticationFilter;
 import br.com.kopili.filters.CustomAuthorizationFilter;
 import br.com.kopili.utility.AlgorithmUtil;
 import br.com.kopili.utility.AuthorizeAlwaysRoutesUtil;
 import lombok.RequiredArgsConstructor;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.http.HttpMethod;
 import org.springframework.security.authentication.AuthenticationManager;
 import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 import org.springframework.security.core.userdetails.UserDetailsService;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

 import static org.springframework.http.HttpMethod.*;
 import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AlgorithmUtil algorithmUtil;
    private final AuthorizeAlwaysRoutesUtil alwaysRoutesUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), algorithmUtil);
        customAuthenticationFilter.setFilterProcessesUrl("/kopili/login");


        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests()
                .antMatchers(
                        HttpMethod.POST,
                        "/kopili/login",
                        "/user/create",
                        "/user/create/admin"
                )
                .permitAll();

        http.authorizeRequests()
                .antMatchers(DELETE, "/user/delete/**")
                .permitAll();

        http.authorizeRequests().
                antMatchers(GET, "/user/token/refresh")
                .permitAll();

        http.authorizeRequests()
                .antMatchers(GET, "/user/read")
                .hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests()
                .antMatchers(
                        GET,
                        "/user/findUserImage/**",
                        "/user/findUserPostImage/**",
                        "/user/findAUser/**",
                        "/user/readUserFollowed/**",
                        "/page/feed/**"
                        )
                .hasAnyAuthority("ROLE_USER");

        http.authorizeRequests().antMatchers(
                POST,
                "/makePost/**",
                "/followSomeone/**"
        ).hasAnyAuthority("ROLE_USER");




        http.authorizeRequests().anyRequest().authenticated();


        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(algorithmUtil, alwaysRoutesUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
