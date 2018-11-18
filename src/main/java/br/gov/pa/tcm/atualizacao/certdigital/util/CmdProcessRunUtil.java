package br.gov.pa.tcm.atualizacao.certdigital.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CmdProcessRunUtil {

    public static String runProcessKeytool(String keytoolHome, String aliasName, String path_to_keystore_file, String path_to_certificate_file, String keyPass) {
        Runtime rt = Runtime.getRuntime();

        String path_to_file_certificate = path_to_certificate_file + File.separatorChar + aliasName;
        keyPass = "changeit";

        //File logFile = arqSqlLoader.getArquivoLog();
        String extension = ""; //UtilitarioGeral.getFileExtension(arqSqlLoader.getArquivoRececebido());
        String cmd;
        String soName = System.getProperty("os.name").substring(0, 3) ;
        System.out.println(System.getProperty("os.name").substring(0, 3));
        String arquivoName = "";
        if ((extension == null) || (extension.equals(""))) {
            if (soName.equals("Win")){

                cmd = "keytool -exportcert -alias " + aliasName
                        + " -keypass " + keyPass
                        + " -keystore " + path_to_keystore_file
                        + " -rfc -file " + path_to_file_certificate + ".crt"
                        + " -storepass " + keyPass;
            }else{
                cmd = "keytool -exportcert -alias " + aliasName
                        + " -keypass " + keyPass
                        + " -keystore " + path_to_keystore_file
                        + " -rfc -file " + path_to_file_certificate + ".crt"
                        + " -storepass " + keyPass;
            }
        }
        else {
            if (soName.equals("Win")){
                cmd = "keytool -exportcert -alias " + aliasName
                        + " -keypass " + keyPass
                        + " -keystore " + path_to_keystore_file
                        + " -rfc -file " + path_to_file_certificate + ".crt"
                        + " -storepass " + keyPass;
            }else{
                cmd = "keytool -exportcert -alias " + aliasName
                        + " -keypass " + keyPass
                        + " -keystore " + path_to_keystore_file
                        + " -rfc -file " + path_to_file_certificate + ".crt"
                        + " -storepass " + keyPass;
            }

            System.out.println(cmd);

        }

        System.out.println(cmd);

        FileWriter file;
        Process proc = null;
        BufferedWriter bf = null;
        try {
            file = new FileWriter(path_to_certificate_file + File.separator + "ExceptionInKeytool.txt");
            bf = new BufferedWriter(file);
            bf.write("KEYTOOL COMMAND:\n" +cmd +"---------------------------------\n\n\n");

            String[] env = {"ORACLE_HOME=/opt/oracle/app/oracle/product/11.2.0/client"};
            if (soName.equals("Win")){
                proc = rt.exec(cmd);
            }else{
                proc = rt.exec(cmd,env);
            }

            String exitVal = String.valueOf(proc.waitFor());
            System.out.println(exitVal);
        } catch (IOException | InterruptedException e) {
            System.out.println("ERROU comando cmd" );
            e.getMessage();

        }
        finally {
            if (proc != null) {
                proc.destroy();
            }
        }
        return arquivoName;
    }

}
