package com.samha.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {
    public static byte[] zipReports(List<byte[]> reports) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipStream = new ZipOutputStream(outputStream)) {
            int reportIndex = 1;
            for (byte[] report : reports) {
                String entryName = "report" + reportIndex + ".txt";
                zipStream.putNextEntry(new ZipEntry(entryName));
                zipStream.write(report);
                zipStream.closeEntry();
                reportIndex++;
            }
        }
        OutputStream outputStream1 = new FileOutputStream("teste.zip");
        outputStream1.write(outputStream.toByteArray());
        return outputStream.toByteArray();
    }

    public static byte[] createZipFile(List<Map<String, Object>> results) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ZipOutputStream zos = new ZipOutputStream(baos)) {

            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> doc = results.get(i);
                byte[] fileBytes = (byte[]) doc.get("bytes");
                String fileName = doc.get("nome") + ".pdf";

                ZipEntry zipEntry = new ZipEntry(fileName);
                zos.putNextEntry(zipEntry);
                zos.write(fileBytes);
                zos.closeEntry();
            }

            zos.finish();
            zos.close();

            byte[] zipBytes = baos.toByteArray();

            System.out.println("Arquivos zipados salvos com sucesso.");

            return zipBytes;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao compactar e salvar os arquivos: " + e.getMessage());
        }
    }
}