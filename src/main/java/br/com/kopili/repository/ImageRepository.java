package br.com.kopili.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kopili.models.ImageEntity;

@Deprecated
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    
}
