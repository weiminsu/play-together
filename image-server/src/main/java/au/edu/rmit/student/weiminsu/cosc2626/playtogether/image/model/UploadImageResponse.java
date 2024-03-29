package au.edu.rmit.student.weiminsu.cosc2626.playtogether.image.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageResponse {
    UUID imageId;
    URL uploadUrl;
    String errorMessage;
}
