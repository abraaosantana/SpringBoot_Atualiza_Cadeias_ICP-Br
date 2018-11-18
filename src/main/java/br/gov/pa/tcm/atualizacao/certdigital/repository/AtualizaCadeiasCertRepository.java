package br.gov.pa.tcm.atualizacao.certdigital.repository;

import br.gov.pa.tcm.atualizacao.certdigital.model.AtualizaCadeiasCertificados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtualizaCadeiasCertRepository extends JpaRepository<AtualizaCadeiasCertificados, Long> {
}
