package com.lpp.life.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.lpp.life.community.exception.CustomizeErrorCode;
import com.lpp.life.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

//@Service
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;

    @Value("${ucloud.ufile.private-key}")
    private String privateKey;

    @Value(("${ucloud.ufile.bucket-name}"))
    private String backName;

    @Value(("${ucloud.ufile.region}"))
    private String region;

    @Value(("${ucloud.ufile.suffix}"))
    private String suffix;





    public String upload(InputStream fileStream,String mimeType,String fileName){
        File file = new File("your file path");

        String generateFilname="";
        String[] fileSpliter = fileName.split("\\.");
        if(fileSpliter.length>1){
            generateFilname = UUID.randomUUID().toString() + "." + fileSpliter[fileSpliter.length - 1];
        }else{
            return null;
        }

        try {
            // 对象相关API的授权器
            ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(
                    "Your PublicKey", "Your PrivateKey");

            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, suffix);
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFilname)
                    .toBucket("community")
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
                    if(response !=null && response.getRetCode() == 0){
                        String url = UfileClient.object(OBJECT_AUTHORIZER, config).
                                getDownloadUrlFromPrivateBucket(generateFilname, backName, 24 * 60 * 60).createUrl();
                        return url;
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAILED);
                    }
        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAILED);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAILED);
        }

//        return "name";
    }

}
