package SpringBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import SpringBoot.config.handler.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        htt.
////                 указываем страницу с формой логина
//                .loginPage("/login")
////                указываем логику обработки при логине
             //   .successHandler(loginSuccessHandler)
////                 указываем action с формы логина
//                .loginProcessingUrl("/login")
////                 Указываем параметры логина и пароля с формы логина
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
////                 даем доступ к форме логина всем
           //     .permitAll();
//
//        http.logout()
//                // разрешаем делать логаут всем
//                .permitAll()
//                // указываем URL логаута
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                // указываем URL при удачном логауте
//                .logoutSuccessUrl("/login?logout")
//                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
//                .and().csrf().disable();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/hello").hasAnyRole("USER","ADMIN")
                .antMatchers("/login").permitAll()
                .and().formLogin()
                .successHandler(loginSuccessHandler);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
