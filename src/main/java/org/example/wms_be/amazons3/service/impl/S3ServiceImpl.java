package org.example.wms_be.amazons3.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.amazons3.service.S3Service;
import org.example.wms_be.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public String uploadFileToS3(File file) {
        try {
            return uploadFileToS3Bucket(bucketName, file);
        } catch (AmazonServiceException e) {
            throw new FileStorageException("Error uploading file to S3");
        }
    }

    @Override
    public String generatePresignedUrl(String s3Path) {
        // Set expiration time
        Date expiration = Date.from(Instant.now().plus(Duration.ofDays(1)));

        // Generate the presigned URL request
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, s3Path)
                        .withMethod(com.amazonaws.HttpMethod.GET)
                        .withExpiration(expiration)
                        .withResponseHeaders(new ResponseHeaderOverrides().withContentType(
                                "application/octet-stream"));

        // Generate the presigned URL
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toExternalForm();
    }

    private String uploadFileToS3Bucket(final String bucketName, final File file) {
        // File name
        String fileName = Instant.now().toEpochMilli() + "_" + file.getName().replace(" ", "_");

        // Upload to S3
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
//        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putObjectRequest);

        return fileName;
    }


}
