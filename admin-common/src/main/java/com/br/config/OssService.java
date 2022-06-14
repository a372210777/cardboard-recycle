package com.br.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.ObjectMetadata;
import com.br.dto.FileInfo;
import com.br.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Service
@ConditionalOnBean(OssConfig.class)
public class OssService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OssService.class);

    @Autowired
    private OssConfig ossConfig;
    @Autowired
    protected MessageSource     messageSource;

    public FileInfo writeImage(FileInfo fileInfo) throws IOException {
        InputStream is = fileInfo.getInputStream();

        try {
            String bucket = ossConfig.getDefimgbucket();
            String name = StringUtils.generateToken(4);
            String objKey = fileInfo.getRootDir() + "/" + name + getExt(fileInfo.getOriginName());

            OSSClient client = new OSSClient(messageSource.getMessage("oss.internalEndpoint", null, ossConfig.getEndpoint(), null),
                    ossConfig.getKey(), ossConfig.getSecret());
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(fileInfo.getFileSize());
            meta.setContentType(fileInfo.getContentType());

            String uri = "";
            String opType = null;
            if (ossConfig.getImagedomain() != null && ossConfig.getImagedomain().length() > 0) {
                uri = ossConfig.getProtocol() + ossConfig.getImagedomain() + "/" + objKey;
            } else {
                uri = ossConfig.getProtocol() + bucket + "." + ossConfig.getEndpoint() + "/" + objKey;
            }
            opType = "inline";
            meta.setContentDisposition(opType + "; filename=" + URLEncoder.encode(fileInfo.getOriginName(), "UTF-8"));

            // 上传到aliyun-oss
            client.putObject(bucket, objKey, is, meta);

            fileInfo.setKey(name);
            fileInfo.setUri(uri);
            return fileInfo;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw e;
        }

    }

    private  String getExt(String filename)
    {
        if(filename != null && filename.lastIndexOf(".") != -1)
            return filename.substring(filename.lastIndexOf("."));
        else
            return "";
    }

}
