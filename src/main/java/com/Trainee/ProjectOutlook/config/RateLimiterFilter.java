package com.Trainee.ProjectOutlook.config;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    // Карта для хранения бакетов по IP-адресу клиента
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    // Метод для создания нового бакета с лимитом на 10 запросов в минуту
    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {

        // Получаем IP-адрес клиента
        String clientIp = request.getRemoteAddr();

        // Создаём или извлекаем бакет для данного IP-адреса
        Bucket bucket = cache.computeIfAbsent(clientIp, k -> createNewBucket());

        // Проверяем, можно ли потребить токен (разрешение запроса)
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response); // Разрешаем запрос
        } else {
            response.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
            response.getWriter().write("Too many requests - Rate limit exceeded");
        }
    }
}
