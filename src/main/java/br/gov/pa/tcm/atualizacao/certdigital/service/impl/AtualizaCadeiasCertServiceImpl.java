package br.gov.pa.tcm.atualizacao.certdigital.service.impl;

import br.gov.pa.tcm.atualizacao.certdigital.model.AtualizaCadeiasCertificados;
import br.gov.pa.tcm.atualizacao.certdigital.service.AtualizaCadeiasCertService;
import br.gov.pa.tcm.atualizacao.certdigital.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AtualizaCadeiasCertServiceImpl implements AtualizaCadeiasCertService {

    String passwd = null;

    @Override
    public void atualizaCadeiasCertServidor(List<AtualizaCadeiasCertificados> atualizaCadeiasCertificadosList){

        atualizaCadeiasCertificadosList.forEach(atualizacao ->{
            FileUtil.isPathExist(atualizacao.getPathServidor());
            File arquivoZip = FileUtil.DownloadUrlFile(atualizacao.getUrlRepositorio(), atualizacao.getPathServidor());
            try {

                if (atualizacao.getPasswdFile() == null) {
                    passwd = "";
                } else {
                    passwd = atualizacao.getPasswdFile();
                }

                FileUtil.unzipFileCrt(arquivoZip, passwd, atualizacao.getPathServidor());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (arquivoZip.isFile()){
                    arquivoZip.delete();
                }
            }
        });
    }
}
