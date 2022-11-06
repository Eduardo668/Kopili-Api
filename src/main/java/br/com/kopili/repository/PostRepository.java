package br.com.kopili.repository;

import br.com.kopili.models.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

//    public List<PostEntity> findAllPost (Long post_id);

}
