package hiccup.hiccupstore.commonutil.file;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;
    private Integer boardid;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
