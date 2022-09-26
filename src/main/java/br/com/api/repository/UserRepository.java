package br.com.api.repository;

import br.com.api.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findByUsername(String username);

//    public List<UserEntity> findAllFriend_List(UserEntity user);



}
