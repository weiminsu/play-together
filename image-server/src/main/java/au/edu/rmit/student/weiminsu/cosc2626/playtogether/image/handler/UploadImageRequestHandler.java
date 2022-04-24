package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.handler;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.ImageMetadata;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.UploadImageResponse;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.ImageRepository;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.S3Repository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class UploadImageRequestHandler implements RequestHandler<Map<Object, Object>, UploadImageResponse> {

    @Override
    public UploadImageResponse handleRequest(Map<Object, Object> input, Context context) {
        ImageRepository imageRepository = new ImageRepository();
        S3Repository s3Repository = new S3Repository();

        UUID imageId = UUID.randomUUID();
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setImageId(imageId);
        imageMetadata.setBucketName(ImageRepository.IMAGE_BUCKET_NAME);
        imageRepository.saveImageMetadata(imageMetadata);

        URL presignedUrl = s3Repository.getUploadUrl(imageMetadata.getBucketName(), imageMetadata.getImageId().toString());
        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        uploadImageResponse.setImageId(imageId);
        uploadImageResponse.setUploadUrl(presignedUrl);
        return uploadImageResponse;
    }
}
