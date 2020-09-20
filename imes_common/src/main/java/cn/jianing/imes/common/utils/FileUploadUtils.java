package cn.jianing.imes.common.utils;

import cn.jianing.imes.common.entity.FileUploadResponse;
import com.aliyun.oss.OSS;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    /**
     * 工具方法，向阿里云oss服务器中上传图片文件，
     * 保存的路径为 images/yyyy/MM/dd/newFileName.extension
     * @param file 上传的文件
     * @param ossClient 阿里云oss对象
     * @param bucketName bucketName
     * @param newFileName 要保存的新的文件名
     * @return ant design upload 的组件中的file对象
     */
    public static FileUploadResponse imageUpload(MultipartFile file, OSS ossClient, String bucketName, String urlPrefix, String newFileName) {

        FileUploadResponse fileUploadResponse = new FileUploadResponse();

        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            fileUploadResponse.setStatus(FileUploadResponse.STATUS_ERROR);
            return fileUploadResponse;
        }

        String filePath = getImageFilePath(file.getOriginalFilename(), newFileName);

        try {
            ossClient.putObject(bucketName, filePath, file.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            // 上传失败
            fileUploadResponse.setStatus(FileUploadResponse.STATUS_ERROR);
            return fileUploadResponse;
        }

        String fileFullName = urlPrefix + filePath;
        fileUploadResponse.setStatus(FileUploadResponse.STATUS_DONE);
        fileUploadResponse.setUid(fileFullName);
        fileUploadResponse.setName(fileFullName);
        return fileUploadResponse;

    }

    private static String getImageFilePath(String fileName, String newFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy") + "/" + dateTime.toString("MM") + "/" +
                dateTime.toString("dd") + "/" + newFileName +
                "." + StringUtils.getFilenameExtension(fileName);
    }
}
