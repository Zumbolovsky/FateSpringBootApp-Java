package br.com.zumbolovsky.fateapp.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonagensRepository extends JpaRepository<Personagens, Integer> {
}
