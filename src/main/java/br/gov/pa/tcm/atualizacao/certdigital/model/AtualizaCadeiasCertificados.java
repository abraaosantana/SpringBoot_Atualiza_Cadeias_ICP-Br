package br.gov.pa.tcm.atualizacao.certdigital.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class AtualizaCadeiasCertificados implements Serializable {

    @Id
    @SequenceGenerator(name = "ATUALIZA_CADEIAS_CERTIFICADOS_SEQ_GENERATOR", sequenceName = "ATUALIZA_CADEIAS_CERTIFICADOS_SEQ", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATUALIZA_CADEIAS_CERTIFICADOS_SEQ_GENERATOR")
    private Long id;

    private String pathServidor;

    private String urlRepositorio;

    private String passwdFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getPathServidor() {
        return pathServidor;
    }

    public void setPathServidor(String pathServidor) {
        this.pathServidor = pathServidor;
    }

    public String getUrlRepositorio() {
        return urlRepositorio;
    }

    public void setUrlRepositorio(String urlRepositorio) {
        this.urlRepositorio = urlRepositorio;
    }

    public String getPasswdFile() {
        return passwdFile;
    }

    public void setPasswdFile(String passwdFile) {
        this.passwdFile = passwdFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtualizaCadeiasCertificados that = (AtualizaCadeiasCertificados) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pathServidor, that.pathServidor) &&
                Objects.equals(urlRepositorio, that.urlRepositorio) &&
                Objects.equals(passwdFile, that.passwdFile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, pathServidor, urlRepositorio, passwdFile);
    }

    @Override
    public String toString() {
        return "AtualizaCadeiasCertificados{" +
                "id=" + id +
                ", pathServidor='" + pathServidor + '\'' +
                ", urlRepositorio='" + urlRepositorio + '\'' +
                ", passwdFile='" + passwdFile + '\'' +
                '}';
    }
}
