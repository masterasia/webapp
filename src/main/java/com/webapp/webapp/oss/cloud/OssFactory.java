package com.webapp.webapp.oss.cloud;


import com.webapp.webapp.sys.service.SysConfigService;
import com.webapp.common.utils.ConfigConstant;
import com.webapp.common.utils.Constant;
import com.webapp.common.utils.SpringContextUtils;

/**
 * 文件上传Factory
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2017-03-26 10:18
 */
public final class OssFactory {
    private static SysConfigService sysConfigService;

    static {
        OssFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static BaseCloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if(config.getType() == Constant.CloudService.QINIU.getValue()){
            return new QiniuCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
            return new QcloudCloudStorageService(config);
        }

        return null;
    }

}
