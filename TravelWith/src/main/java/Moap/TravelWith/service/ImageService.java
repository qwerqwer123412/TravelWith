package Moap.TravelWith.service;

import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.entity.Member;
import Moap.TravelWith.repository.ImageRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional(readOnly = true)
public class ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    @Transactional
    public String changeProfileImg(MultipartFile file, Member member) throws IOException {
        String fileName = "profile/" + member.getId();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

        String encodedReturnURL = URLEncoder.encode("https://" + bucket + ".s3.amazonaws.com/" + fileName, StandardCharsets.UTF_8.toString());
        member.changeProfileImg(encodedReturnURL);
        return encodedReturnURL;

    }

    @Transactional
    public void saveMessageImg(MultipartFile file, MatchPosting matchPosting) throws IOException {
        try {
            saveImgLogic(file, matchPosting);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void saveImgLogic(MultipartFile file, MatchPosting matchPosting) throws IOException {
        String fileName = "msg/" + matchPosting.getId() + "/" + UUID.randomUUID();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        String encodedReturnURL = "https://" + bucket + ".s3.amazonaws.com/" + fileName;
        matchPosting.setImgURL(encodedReturnURL);

    }

}
