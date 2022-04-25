package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.repository;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model.ImageMetadata;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class ImageRepository {

    public static final String IMAGE_BUCKET_NAME = "play-together-image-store";

    private static final String DYNAMODB_IMAGE_METADATA_TABLE_NAME = "image_metadata";
    private static final AmazonDynamoDB dbClient = AmazonDynamoDBClientBuilder.defaultClient();
    private static final DynamoDB dynamoDB = new DynamoDB(dbClient);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    Logger logger = LogManager.getLogger();

    public ImageMetadata getImageMetadata(UUID imageId) {
        logger.debug("Retrieving image {} from {}",
                imageId, DYNAMODB_IMAGE_METADATA_TABLE_NAME);
        ImageMetadata imageMetadata = null;

        Table table = dynamoDB.getTable(DYNAMODB_IMAGE_METADATA_TABLE_NAME);

        try {
            Item item = table.getItem(ImageMetadata.IMAGE_ID, imageId.toString(), null, null);
            if (item != null) {
                logger.debug(item.toJSONPretty());
                imageMetadata = objectMapper.readValue(item.toJSON(), ImageMetadata.class);
            } else {
                logger.warn("No image metadata found for image {}", imageId);
            }
        } catch (Exception e) {
            logger.error("Failed to get image metadata with image id {}", imageId, e);
        }
        return imageMetadata;
    }

    public void saveImageMetadata(ImageMetadata imageMetadata) {
        Table table = dynamoDB.getTable(DYNAMODB_IMAGE_METADATA_TABLE_NAME);
        try {
            Item item = new Item().withPrimaryKey(ImageMetadata.IMAGE_ID, imageMetadata.getImageId().toString())
                    .withString(ImageMetadata.BUCKET_NAME, imageMetadata.getBucketName())
                            .withString(ImageMetadata.IMAGE_TYPE, imageMetadata.getImageType());
            table.putItem(item);
        }
        catch (Exception e) {
            logger.error("Create items failed.", e);
        }
    }


}
