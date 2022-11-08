package br.com.kopili.filters;

import br.com.kopili.utility.AlgorithmUtil;
import br.com.kopili.utility.AuthorizeAlwaysRoutesUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


// Captura as requisições e verifica se elas possuem o token de acesso
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final AlgorithmUtil algorithmUtil;
    private final AuthorizeAlwaysRoutesUtil authorizedRoutes;





    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {



            if (authorizedRoutes.verifyRoute(request.getServletPath())){
                filterChain.doFilter(request, response);
            }else {


                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                    try {

                        String pure_jwt_token = authorizationHeader.substring("Bearer ".length());

                        JWTVerifier verifier = JWT.require(algorithmUtil.defineAlgorithm()).build();

                        DecodedJWT decodedJWT = verifier.verify(pure_jwt_token);
                        String username = decodedJWT.getSubject();
                        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        stream(roles).forEach(role -> {
                            authorities.add(new SimpleGrantedAuthority(role));
                        });

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(username, null, authorities);


                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
                        filterChain.doFilter(request, response);


                    } catch (Exception e) {
                        response.setHeader("error", e.getMessage());

                        // FORBIDDEN STATUS
                        response.setStatus(403);

                        Map<String, String> error_message = new HashMap<>();
                        error_message.put("error", e.toString());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error_message);


                    }


                } else {
                    log.error("Header not Found");
                    filterChain.doFilter(request, response);


                }
            }



    }

}
