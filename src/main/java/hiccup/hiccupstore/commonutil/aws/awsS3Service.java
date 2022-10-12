package hiccup.hiccupstore.commonutil.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class awsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;
    private final AmazonS3Client amazonS3Client;

    public List<UploadFile> uploadImage(String s3FullPath,List<MultipartFile> multipartFiles){
        List<UploadFile> storeFileResult = new ArrayList<>();

        multipartFiles.forEach(file -> {

            if(!file.isEmpty()){

                String storeFileName = createStoreFileName(file.getOriginalFilename());
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentLength(file.getSize());
                objectMetadata.setContentType(file.getContentType());

                try(InputStream inputStream = file.getInputStream()){
                    amazonS3.putObject(new PutObjectRequest(bucket,s3FullPath+"/"+storeFileName,inputStream,objectMetadata).
                            withCannedAcl(CannedAccessControlList.PublicRead));

                } catch (IOException exception){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,exception.getMessage());
                }

                storeFileResult.add(new UploadFile(file.getOriginalFilename(),storeFileName));
            }

        });

        return storeFileResult;
    }

    public List<User1vs1BoardDto> getStoredFileUrl(String s3FullPath,List<User1vs1BoardDto> user1vs1BoardDtoList) {

        for (User1vs1BoardDto user1vs1BoardDto : user1vs1BoardDtoList) {
                String s3StoredImageNameUrl = amazonS3Client.getUrl(bucket, s3FullPath+"/"+user1vs1BoardDto.getImagename()).toString();
                user1vs1BoardDto.setS3imageUrl(s3StoredImageNameUrl);
        }
        return user1vs1BoardDtoList;
    }

    public List<User1vs1BoardDto> getStoredProductFileUrl(String s3FullPath,List<User1vs1BoardDto> user1vs1BoardDtoList) {

        for (User1vs1BoardDto user1vs1BoardDto : user1vs1BoardDtoList) {
            if(user1vs1BoardDto.getImageType().equals("PRODUCT")){
                String s3StoredImageNameUrl = amazonS3Client.getUrl(bucket, s3FullPath+"/"+user1vs1BoardDto.getImagename()).toString();
                user1vs1BoardDto.setS3imageUrl(s3StoredImageNameUrl);
            }
        }
        return user1vs1BoardDtoList;
    }


    public NoticeDto getStoredNoticeFileUrl(String s3FullPath, NoticeDto noticeBoardSee) {
        String s3StoredImageNameUrl = amazonS3Client.getUrl(bucket, s3FullPath+"/"+noticeBoardSee.getImagename()).toString();
        noticeBoardSee.setImagepath(s3StoredImageNameUrl);
        return noticeBoardSee;
    }

    public void deleteFile(String fileName,String s3FullPath) {
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, s3FullPath+"/"+fileName));
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    private String text(){
        ConversionService conversionService = new DefaultConversionService();
        conversionService.convert(null,null);
        return null;
    }
    public String getFullPath(String s3FullPath,String filename) {
        return amazonS3Client.getUrl(bucket, s3FullPath+'/'+filename).toString();
    }

}
