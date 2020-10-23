package br.com.zumbolovsky.fateapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.zumbolovsky.fateapp.domain.Personagens;
import br.com.zumbolovsky.fateapp.domain.PersonagensRepository;

@Service
public class PersonagensService {

  @Autowired private PersonagensRepository personagensRepository;

  @Transactional
  public void insert(final Personagens personagens) {
    personagensRepository.save(personagens);
  }
}
