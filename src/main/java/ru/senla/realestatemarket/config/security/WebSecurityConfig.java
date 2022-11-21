package ru.senla.realestatemarket.config.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ru.senla.realestatemarket.config.security.filter.JwtTokenFilter;
import ru.senla.realestatemarket.service.user.IUserService;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * @author Alexander Slotin (@alexsnitol)
 */

@EnableWebSecurity(debug = false)
@Configuration
@ComponentScan(basePackages = "ru.senla.realestatemarket")
public class WebSecurityConfig {

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


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        http = http.cors().and().csrf().disable();

        http


                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()


                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()


                .authenticationManager(authenticationManager)


                .authorizeRequests()


                // AUTHORIZATION

                .antMatchers(POST, "/api/auth").permitAll()

                // STATIC RESOURCES

                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()

                // CHART

                .antMatchers("/canvas/**").permitAll()

                // SWAGGER

                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/favicon**").permitAll()
                .antMatchers("/swagger**").permitAll()
                .antMatchers("/springfox**").permitAll()


                // USERS

                .antMatchers("/api/users/current/**").authenticated()
                .antMatchers(POST, "/api/users").hasAnyRole("ANONYMOUS", "ADMIN")


                // REVIEWS

                .antMatchers(GET, "/api/reviews/customers/**").authenticated()
                .antMatchers(GET, "/api/reviews/sellers/current/**").authenticated()
                .antMatchers(GET, "/api/reviews/sellers/{sellerId}").permitAll()
                .antMatchers(POST, "/api/reviews/sellers/**").authenticated()

                
                // MESSAGES

                .antMatchers("/api/messages/**").authenticated()
                
                
                // BALANCE OPERATIONS

                .antMatchers(GET, "/api/users/current/balance-operations/**").authenticated()


                // ADDRESS

                .antMatchers(GET, "/api/addresses/**").permitAll()
                .antMatchers(GET, "/api/regions/{regionId}/" +
                        "cities/{cityId}/streets/{streetId}/house-numbers").permitAll()
                
                .antMatchers(GET, "/api/cities/**").permitAll()
                .antMatchers(GET, "/api/regions/**").permitAll()
                .antMatchers(GET, "/api/streets").permitAll()


                // HOUSES

                .antMatchers(GET, "/api/houses/**").permitAll()


                // PROPERTIES

                // HOUSING PROPERTIES

                // APARTMENT PROPERTIES

                .antMatchers("/api/properties/housing/apartments/owners/current/**").authenticated()


                // FAMILY HOUSE PROPERTIES

                .antMatchers("/api/properties/housing/family-houses/owners/current/**").authenticated()


                // LAND PROPERTIES

                .antMatchers("/api/properties/lands/owners/current/**").authenticated()



                // ANNOUNCEMENTS

                .antMatchers(GET, "/api/announcements/housing/apartments/open/**").permitAll()


                // HOUSING ANNOUNCEMENTS

                .antMatchers(GET, "/api/announcements/housing/apartments/housing/open/**").permitAll()


                // APARTMENT ANNOUNCEMENTS

                .antMatchers(GET, "/api/announcements/housing/apartments/open/**").permitAll()

                .antMatchers("/api/announcements/housing/apartments/owners/current/**").authenticated()
                
                .antMatchers(GET, "/api/announcements/housing/apartments/closed/owners/{userIdOfOwner}")
                    .permitAll()

                .antMatchers(GET, "/api/announcements/housing/apartments" +
                        "/timetables/rent/tenants/current/**").authenticated()
                .antMatchers(GET, "/api/announcements/housing/apartments/{apartmentAnnouncementId}" +
                        "/timetables/rent/tenants/current/**").authenticated()
                .antMatchers(POST, "/api/announcements/housing/apartments" +
                        "/open/{apartmentAnnouncementId}" +
                        "/timetables/rent/tenants/current/**").authenticated()
                

                // FAMILY HOUSE ANNOUNCEMENTS

                .antMatchers(GET, "/api/announcements/housing/family-houses/open/**").permitAll()

                .antMatchers("/api/announcements/housing/family-houses/owners/current/**").authenticated()

                .antMatchers(GET, "/api/announcements/housing/family-houses/closed/owners/{userIdOfOwner}")
                    .permitAll()

                .antMatchers(GET, "/api/announcements/housing/family-houses" +
                        "/timetables/rent/tenants/current/**").authenticated()
                .antMatchers(GET, "/api/announcements/housing/family-houses/{familyHouseAnnouncementId}" +
                        "/timetables/rent/tenants/current/**").authenticated()
                .antMatchers(POST, "/api/announcements/housing/family-houses" +
                        "/open/{familyHouseAnnouncementId}" +
                        "/timetables/rent/tenants/current/**").authenticated()


                // LAND ANNOUNCEMENTS

                .antMatchers(GET, "/api/announcements/lands/open/**").permitAll()

                .antMatchers("/api/announcements/lands/owners/current/**").authenticated()

                .antMatchers(GET, "/api/announcements/lands/closed/owners/{userIdOfOwner}")
                    .permitAll()


                // DICTIONARY
                
                .antMatchers(GET, "/api/announcement-top-prices/**").permitAll()
                .antMatchers(GET, "/api/renovation-types/**").permitAll()
                .antMatchers(GET, "/api/house-materials/**").permitAll()

                // ENUMS

                .antMatchers(GET, "/api/enums/**").permitAll()



                
                .anyRequest().hasAnyRole("ADMIN").and()


                .formLogin().disable();

        http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();

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
