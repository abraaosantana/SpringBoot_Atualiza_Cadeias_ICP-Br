package br.gov.pa.tcm.atualizacao.certdigital.infra.job;

import br.gov.pa.tcm.atualizacao.certdigital.repository.AtualizaCadeiasCertRepository;
import br.gov.pa.tcm.atualizacao.certdigital.service.impl.AtualizaCadeiasCertServiceImpl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class AtualizaCertJob implements Job {

    @Autowired
    private AtualizaCadeiasCertServiceImpl acService;
    @Autowired
    private AtualizaCadeiasCertRepository atualizaCadeiasCertRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        acService.atualizaCadeiasCertServidor(atualizaCadeiasCertRepository.findAll());
        System.out.println("Cadeias de certificados atualizadas.");

    }
}
