package br.gov.pa.tcm.atualizacao.certdigital.service;

import br.gov.pa.tcm.atualizacao.certdigital.model.AtualizaCadeiasCertificados;

import java.util.List;

public interface AtualizaCadeiasCertService {

    public void atualizaCadeiasCertServidor(List<AtualizaCadeiasCertificados> atualizaCadeiasCertificadosList);
}
