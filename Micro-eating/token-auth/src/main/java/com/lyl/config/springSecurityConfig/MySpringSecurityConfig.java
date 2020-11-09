package com.lyl.config.springSecurityConfig;

import com.lyl.config.springSecurityConfig.sms.SmsSecurityConfig;
import com.lyl.enums.CommonEnum;
import com.lyl.properties.LoginParametersConstant;
import com.lyl.utils.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author lyl
 * @Date 2020/9/28 14:52
 */
@Configuration
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    TokenUtil tokenUtil;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    SmsSecurityConfig smsSecurityConfig;

    @Resource
    LoginParametersConstant loginParametersConstant;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MyDaoAuthenticationProvider createMyDaoAuthenticationProvider(){
        MyDaoAuthenticationProvider myDaoAuthenticationProvider = new MyDaoAuthenticationProvider();
        myDaoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        myDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return myDaoAuthenticationProvider;
    }

    /**
     * 通过重写 authenticationManager 方法来提供一个自己的 AuthenticationManager，
     * 实际上就是 ProviderManager，在创建 ProviderManager 时，加入自己的 myAuthenticationProvider
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        ProviderManager manager = new ProviderManager(Arrays.asList(createMyDaoAuthenticationProvider()));
        return manager;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/hello");
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler(){
        return new GeneralAuthenticationFailureHandler.Builder()
                .setCode(CommonEnum.CLIENTERROR.getCode())
                .setIsok(true)
                .setData(null)
                .setMsg(null)
                .build();

    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new GeneralAuthenticationSuccessHandler.Builder()
                .setCode(CommonEnum.SUCCESS.getCode())
                .setIsok(true)
                .setMsg("认证成功")
                .setTokenUtil(tokenUtil)
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginProcessingUrl(loginParametersConstant.getLoginProcessingUrl())
                .usernameParameter(loginParametersConstant.getUsernameParameter())
                .passwordParameter(loginParametersConstant.getPasswordParameter())
                .successHandler(getAuthenticationSuccessHandler())
                .failureHandler(getAuthenticationFailureHandler())
                .and()
                .apply(smsSecurityConfig)
                .and()
                .authorizeRequests()
//                .antMatchers("/article/admin/**").hasAuthority("root")
                .antMatchers("/vc.jpg","/smsCode","/hello","/refreshToken").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    //    private TokenUtil getTokenUtil(){
//        return new TokenUtil.Builder()
//                .setSecret(TokenConstant.secret)
//                .setHeader(TokenConstant.header)
//                .setExpiration(TokenConstant.expiration)
//                .build();
//    }
}
