package cn.xavier.hrm;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author Zheng-Wei Shui
 * @date 12/19/2021
 */
public class AliCloudOssTest {

    @Test // https://help.aliyun.com/document_detail/84781.html
    public void testUploadFile() throws FileNotFoundException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-chengdu.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tJAzuzuabfC2fHMV74t";
        String accessKeySecret = "9rKraqVFHNbUVQPh20TOoo9jhNtXQq";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        InputStream inputStream = new FileInputStream("J:\\pictures" +
                "\\高中联想手机文件\\我的图片\\天文图片\\F_f764ebf327fe40eca62ca913cf1538df.jpg");
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("hrm-xavier", "image/astronomical.jpg", inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
