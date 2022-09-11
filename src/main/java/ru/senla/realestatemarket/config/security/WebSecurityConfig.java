package ru.senla.realestatemarket.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.senla.realestatemarket.config.security.filter.JwtTokenFilter;
import ru.senla.realestatemarket.service.user.IUserService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity(debug = true)
@Configuration
@ComponentScan(basePackages = "ru.senla.realestatemarket")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;
    private final PasswordEncoder passwordEncoder;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationEntryPoint authenticationEntryPoint;


    public WebSecurityConfig(
            IUserService userService,
            JwtTokenFilter jwtTokenFilter,
            PasswordEncoder passwordEncoder,
            @Qualifier("customAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler,
            @Qualifier("customAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint
    ) {
        this.userDetailsService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
        this.passwordEncoder = passwordEncoder;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http


                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()


                .authorizeRequests()


                // AUTHORIZATION

                .antMatchers(POST, "/api/auth").permitAll()


                // SWAGGER

                .antMatchers(GET,"/v2/api-docs/**").permitAll()
                .antMatchers(GET,"/v3/api-docs/**").permitAll()
                .antMatchers(GET, "/swagger-ui/**").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/favicon**").permitAll()
                .antMatchers("/swagger**").permitAll()
                .antMatchers("/springfox**").permitAll()


                // USERS

                .antMatchers(POST, "/api/users").permitAll()



                // ADDRESS

                .antMatchers(GET, "/api/addresses").permitAll()
                .antMatchers(GET, "/api/addresses/{id}").permitAll()
                .antMatchers(GET, "/api/regions/{regionId}/" +
                        "cities/{cityId}/streets/{streetId}/house-numbers").permitAll()



                // PROPERTIES

                // HOUSING PROPERTIES

                // APARTMENT PROPERTIES

                .antMatchers("/api/properties/housing/apartments/current").authenticated()
                .antMatchers("/api/properties/housing/apartments/current/{id}").authenticated()


                // FAMILY HOUSE PROPERTIES

                .antMatchers("/api/properties/housing/family-houses/current").authenticated()
                .antMatchers("/api/properties/housing/family-houses/current/{id}").authenticated()


                // LAND PROPERTIES

                .antMatchers("/api/properties/housing/lands/current").authenticated()
                .antMatchers("/api/properties/housing/lands/current/{id}").authenticated()



                // ANNOUNCEMENTS

                // HOUSING ANNOUNCEMENTS

                // APARTMENT ANNOUNCEMENTS

                .antMatchers("/api/announcements/housing/apartments/current").authenticated()
                .antMatchers("/api/announcements/housing/apartments/current/{id}").authenticated()

                .antMatchers(GET, "/api/announcements/housing/apartments/open").permitAll()
                .antMatchers(GET, "/api/announcements/housing/apartments/open/{id}").permitAll()
                
                .antMatchers(GET, "/api/announcements/housing/apartments/closed/owner/{userIdOfOwner}")
                    .permitAll()


                // FAMILY HOUSE ANNOUNCEMENTS

                .antMatchers("/api/announcements/housing/family-houses/current").authenticated()
                .antMatchers("/api/announcements/housing/family-houses/current/{id}").authenticated()

                .antMatchers(GET, "/api/announcements/housing/family-houses/open").permitAll()
                .antMatchers(GET, "/api/announcements/housing/family-houses/open/{id}").permitAll()

                .antMatchers(GET, "/api/announcements/housing/family-houses/closed/owner/{userIdOfOwner}")
                    .permitAll()


                // LAND ANNOUNCEMENTS

                .antMatchers("/api/announcements/housing/lands/current").authenticated()
                .antMatchers("/api/announcements/housing/lands/current/{id}").authenticated()

                .antMatchers(GET, "/api/announcements/housing/lands/open").permitAll()
                .antMatchers(GET, "/api/announcements/housing/lands/open/{id}").permitAll()

                .antMatchers(GET, "/api/announcements/housing/lands/closed/owner/{userIdOfOwner}")
                    .permitAll()
                
                
                .anyRequest().hasAnyRole("ADMIN");

        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

}
