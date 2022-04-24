package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetImageRequest {
    UUID imageId;
}
