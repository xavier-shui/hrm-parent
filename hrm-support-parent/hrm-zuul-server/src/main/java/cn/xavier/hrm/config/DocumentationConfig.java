package cn.xavier.hrm.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("系统管理", "/hrm/system/v2/api-docs", "2.0"));
        resources.add(swaggerResource("授权管理", "/hrm/auth/v2/api-docs", "2.0"));
        resources.add(swaggerResource("oss管理", "/hrm/oss/v2/api-docs", "2.0"));
        resources.add(swaggerResource("课程管理", "/hrm/course/v2/api-docs", "2.0"));
        resources.add(swaggerResource("搜索管理", "/hrm/search/v2/api-docs", "2.0"));
        resources.add(swaggerResource("用户管理", "/hrm/user/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}