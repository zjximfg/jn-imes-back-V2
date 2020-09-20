package cn.jianing.imes.common.utils;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ResponseEntityDownloadUtils {

    public static ResponseEntity<FileSystemResource> getResponseEntityDownload(File file) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String fileName = URLEncoder.encode(file.getName(), "UTF-8");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" +  new
                String(fileName.getBytes("ISO8859-1")));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("filename", fileName);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
}
