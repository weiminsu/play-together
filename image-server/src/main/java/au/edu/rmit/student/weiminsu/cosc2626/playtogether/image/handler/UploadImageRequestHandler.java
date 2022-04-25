package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.handler;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.ImageMetadata;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.UploadImageRequest;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.UploadImageResponse;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.ImageRepository;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.S3Repository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.common.collect.ImmutableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class UploadImageRequestHandler implements RequestHandler<UploadImageRequest, UploadImageResponse> {

    private static final List<String> ALLOWED_IMAGE_TYPES = ImmutableList.of("jpeg", "jpg", "png");
    Logger logger = LogManager.getLogger();

    @Override
    public UploadImageResponse handleRequest(UploadImageRequest input, Context context) {
        UploadImageResponse uploadImageResponse = new UploadImageResponse();
        if (!ALLOWED_IMAGE_TYPES.contains(input.getImageType())) {
            logger.info("Unsupported media type: " + input.getImageType());
            throw new UnsupportedOperationException("Only jpeg, jpg or png images are supported.");
        }
        ImageRepository imageRepository = new ImageRepository();
        S3Repository s3Repository = new S3Repository();

        UUID imageId = UUID.randomUUID();
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setImageId(imageId);
        imageMetadata.setBucketName(ImageRepository.IMAGE_BUCKET_NAME);
        imageMetadata.setImageType(input.getImageType());
        imageRepository.saveImageMetadata(imageMetadata);

        URL presignedUrl = s3Repository.getUploadUrl(imageMetadata.getBucketName(), imageMetadata.getImageId().toString()+"."+imageMetadata.getImageType());
        uploadImageResponse.setImageId(imageId);
        uploadImageResponse.setUploadUrl(presignedUrl);
        return uploadImageResponse;
    }
}
