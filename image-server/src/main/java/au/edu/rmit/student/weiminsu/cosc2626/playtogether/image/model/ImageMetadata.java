package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageMetadata {
    public static final String IMAGE_ID = "imageId";
    public static final String BUCKET_NAME = "bucketName";
    public static final String OBJECT_KEY = "objectKey";

    UUID imageId;
    String bucketName;
}
