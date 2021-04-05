package com.yeb.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName Swagger2Config.java
 * @Description swagger2的配置类
 * @createTime 2021年03月29日 21:26:00
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     */
    @Bean
    public Docket creatRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yeb.server.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());


    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     */
    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title("云易办文档")
                .description("云易办接口文档")
                .contact(new Contact("xxx", "http://localhost:8081/doc.html", "XXX@qq.com"))
                .version("1.0.0")
                .build();
    }

    /**
     * @Description:设置请求头信息
     * @Author: 冲动火龙果
     * @Date: 2021/3/29 22:27

     * @return: java.util.List<springfox.documentation.service.ApiKey>
     **/
    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }




    /**
     * @Description:设置需要登录认证的路径
     * @Author: 冲动火龙果
     * @Date: 2021/3/29 22:27

     * @return: java.util.List<springfox.documentation.spi.service.contexts.SecurityContext>
     **/
    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> list = new ArrayList<>();
        list.add(getContextByPath("/hello/.*"));
        return list;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        list.add(new SecurityReference("Authorization", authorizationScopes));
        return list;
    }






}
