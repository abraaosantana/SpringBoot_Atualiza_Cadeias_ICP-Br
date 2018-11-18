package br.gov.pa.tcm.atualizacao.certdigital.controller;

import br.gov.pa.tcm.atualizacao.certdigital.repository.AtualizaCadeiasCertRepository;
import br.gov.pa.tcm.atualizacao.certdigital.service.impl.AtualizaCadeiasCertServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AtualizacaoCadeiasCertController {

    @Autowired
    private AtualizaCadeiasCertServiceImpl acService;
    @Autowired
    private AtualizaCadeiasCertRepository atualizaCadeiasCertRepository;

    @GetMapping("/atualiza-crt")
    public ModelAndView atualizaCadeiasCertCRT() {
        ModelAndView modelAndView = new ModelAndView("atualiza_crt");
        acService.atualizaCadeiasCertServidor(atualizaCadeiasCertRepository.findAll());
        return modelAndView;
    }

}
