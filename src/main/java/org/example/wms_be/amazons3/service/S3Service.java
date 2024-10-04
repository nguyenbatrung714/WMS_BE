package org.example.wms_be.amazons3.service;

import java.io.File;

public interface S3Service {

    String uploadFileToS3(File file);

    String generatePresignedUrl(String imagePath);

}
