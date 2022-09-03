package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.resolver.MyHandlerExceptionResolver;
import hello.exception.resolver.UserHandlerExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        // dispatcherType에 error까지 넣어주면 클라이언트 요청 뿐만 아니라 오류페이지 요청에서도 필터가 호출됨. 기본값은 REQUEST
        // 만약 오류 페이지 전용 필터를 만들고 싶음 DispatcherType.ERROR만 지정하면 됨
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        return filterFilterRegistrationBean;
    }

    // 기존 설정을 유지하면서 추가
    // configurationHandlerExceptionResolvers(..)를 사용하면 스프링이 기본으로 등록하는 exceptionHandler가 제거되기 때문에
    // extendHandlerExceptionResolvers 이걸로 사용해야 함
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }
}
