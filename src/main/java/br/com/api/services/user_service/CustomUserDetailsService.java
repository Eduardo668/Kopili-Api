package br.com.api.services.user_service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.api.models.UserEntity;
import br.com.api.repository.UserRepository;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         UserEntity user_data = userRepository.findByUsername(username);
         
         if (user_data == null){
            throw new UsernameNotFoundException("Este usuario não existe!!");
         }

         return User(
            user_data.getUsername(), user_data.getPassword(), true, true, true
         );

    }
    
}
