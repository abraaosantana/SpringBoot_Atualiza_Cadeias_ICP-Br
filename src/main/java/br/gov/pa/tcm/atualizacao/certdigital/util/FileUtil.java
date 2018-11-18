package br.gov.pa.tcm.atualizacao.certdigital.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static void isPathExist(String path) {
        File filePath = new File(path);
        if (!filePath.exists() || !filePath.isDirectory()) {
            filePath.mkdirs();
        }
    }

    public static File DownloadUrlFile(String stringUrl, String pathLocal) {
        try {

            //Encapsula a URL num objeto java.net.URL
            URL url = new URL(stringUrl);
            //Utilizando URL.getPath() o arquivo local terá o mesmo nome descrito na URL Lembrando que ira retornar a estrutura
            //completa de diretorios e devendo ser tratada caso nao deseje preservar esta estrutura no seu disco local.
            String nomeArquivoLocal = url.getPath().substring(url.getPath().lastIndexOf("/") + 1, url.getPath().length());

            //Cria streams de leitura (este metodo ja faz a conexao)...
            InputStream is = url.openStream();
            //... e de escrita.
            FileOutputStream fos = new FileOutputStream(pathLocal + File.separator + nomeArquivoLocal);
            //Le e grava byte a byte. Voce pode (e deve) usar buffers para
            //melhor performance (BufferedReader).
            int umByte = 0;
            while ((umByte = is.read()) != -1){
                fos.write(umByte);
            }
            //Nao se esqueca de sempre fechar as streams apos seu uso!
            is.close();
            fos.close();
            //apos criar o arquivo fisico, retorna referencia para o mesmo
            return new File(pathLocal + File.separator + nomeArquivoLocal);
        } catch (Exception e) {
            //Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!
            //Aqui so vamos mostrar o stack no stderr.
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
    public static void unzipFileCrt(File arquivoeContasZIP, String passwd, String outputDir) throws IOException {

        List<String> extensoesInvalidas = new ArrayList<String>();
        extensoesInvalidas.add("htm");
        extensoesInvalidas.add("md5");
        extensoesInvalidas.add("pdf");
        extensoesInvalidas.add("zip");
        extensoesInvalidas.add("ZIP");
        extensoesInvalidas.add("\\");

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(arquivoeContasZIP);
        } catch (ZipException e) {
            e.printStackTrace();
        }

        try {

            // Check to see if the zip file is password protected
            if (zipFile.isEncrypted()) {
                // if yes, then set the password for the zip file
                zipFile.setPassword(passwd);
            }

            // Get the list of file headers from the zip file
            List fileHeaderList = zipFile.getFileHeaders();

            // Loop through the file headers
            for (int i = 0; i < fileHeaderList.size(); i++) {
                FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
                String fileName = fileHeader.getFileName();

                if (!(FilenameUtils.isExtension(fileName, extensoesInvalidas))) {
                    // Extract the file to the specified destination
                    zipFile.extractFile(fileHeader, outputDir);
                }
            }

        } catch (Exception e) {
            //System.out.println("ih , deu erro :" + e.getMessage());
        }

    }

}
