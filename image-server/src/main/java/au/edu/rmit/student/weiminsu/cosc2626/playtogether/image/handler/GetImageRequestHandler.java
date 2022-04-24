package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.handler;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.GetImageRequest;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.GetImageResponse;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.ImageMetadata;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.ImageRepository;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository.S3Repository;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class GetImageRequestHandler implements RequestHandler<GetImageRequest, GetImageResponse> {

    Logger logger = LogManager.getLogger();

    @Override
    public GetImageResponse handleRequest(GetImageRequest input, Context context) {
        logger.info("handling get image request");
        ImageRepository imageRepository = new ImageRepository();
        S3Repository s3Repository = new S3Repository();

        ImageMetadata imageMetadata = imageRepository.getImageMetadata(input.getImageId());
        URL presignedUrl = imageMetadata == null ? null :
                s3Repository.getDownloadUrl(imageMetadata.getBucketName(), imageMetadata.getImageId().toString());
        GetImageResponse getImageResponse = new GetImageResponse();
        getImageResponse.setImageId(input.getImageId());
        getImageResponse.setImageUrl(presignedUrl);
        return getImageResponse;
    }
}
