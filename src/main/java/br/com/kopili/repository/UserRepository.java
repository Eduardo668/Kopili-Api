package br.com.kopili.repository;

import br.com.kopili.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

    public UserEntity findByUsernameContains(String username);
    public UserEntity findByEmail(String email);

//    public List<UserEntity> findAllFriend_List(UserEntity user);


}
