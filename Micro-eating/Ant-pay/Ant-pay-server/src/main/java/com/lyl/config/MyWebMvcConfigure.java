package com.lyl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用来配置WebMvc的配置类
 * @author lyl
 * @Date 2020/9/28 14:10
 */
@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer {

    //    @Resource(name = "VerificationCodeInterceptor")
//    private VerificationCodeInterceptor verificationCodeInterceptor;

    /**
     * 添加拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new VerificationCodeInterceptor())
//                .addPathPatterns();
//    }

    /**
     * 通过file协议将/image/**下的所有请求映射到操作系统的文件系统对应的路径下
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/image/**")
//                .addResourceLocations("file:D:/temporaryFiles/test/");
//    }


    /**
     * mvc提供的跨域设置代码
     * @param registry
     */
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry
//                .addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("*")
//                .allowedHeaders("GET", "POST", "DELETE", "PUT")
//                .maxAge(3600);
//    }

    /**
     * 通过FilterRegistrationBean()的方式来添加过滤器可以设置过滤器的一系列参数
     * 添加该过滤器用来设置reqest response的编码格式
     * @return
     */
//    @Bean
//    public FilterRegistrationBean setGlobalCodeFilter(){
//        List<String> urls = new ArrayList<>();
//        urls.add("/**");
//
//        GlobalCodeFilter globalCodeFilter = new GlobalCodeFilter();
//
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(globalCodeFilter);//注册自定义过滤器
//        filterRegistrationBean.setName("globalCodeFilter");//设置过滤器的名字
//        filterRegistrationBean.setOrder(1);//设置过滤器的优先级
//        filterRegistrationBean.setUrlPatterns(urls);//设置过滤器的过滤路径
//
//        return filterRegistrationBean;
//    }

}
