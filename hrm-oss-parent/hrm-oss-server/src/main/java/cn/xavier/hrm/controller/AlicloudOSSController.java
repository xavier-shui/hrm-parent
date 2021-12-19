package cn.xavier.hrm.controller;

import cn.xavier.hrm.config.AlicloudOSSProperties;
import cn.xavier.hrm.util.AjaxResult;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/aliOss")
public class AlicloudOSSController {

    @Autowired
    private AlicloudOSSProperties properties;


    @GetMapping("/sign")
    public AjaxResult ossSign(){
        // host的格式为 bucketname.endpoint
        String host = "https://" + properties.getBucketName() + "." + properties.getEndpoint();
        // 用户上传文件时指定的文件夹名字。
        String dir = "xavier";
        // 创建OSSClient实例。
        OSS ossClient = null;
        try {
            //策略过期时间
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);

            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            //签名
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            //返回签名及OSS相关参数
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", properties.getAccessKey());
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));

            return AjaxResult.me().setResultObj(respMap);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     *
     * @param objectName 填写文件名。文件名包含路径，不包含Bucket名称。例如exampledir/exampleobject.txt。
     * @return the ajax result
     * @see: https://help.aliyun.com/document_detail/32011.html
     */
    @DeleteMapping
    public AjaxResult delete(String objectName) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = properties.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = properties.getAccessKey();
        String accessKeySecret = properties.getSecretKey();
        // 填写Bucket名称，例如examplebucket。
        String bucketName = properties.getBucketName();
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 删除文件。
            ossClient.deleteObject(bucketName, objectName);
            return AjaxResult.me().setMessage("删除成功").setCode(HttpStatus.SC_OK);
        } catch (OSSException e){
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return AjaxResult.me()
                .setSuccess(false)
                .setMessage("删除失败");
    }
}