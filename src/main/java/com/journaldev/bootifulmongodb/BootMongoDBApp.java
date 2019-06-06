package com.journaldev.bootifulmongodb;

import com.mashape.unirest.http.Unirest;
import com.mongodb.MongoClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.net.ssl.SSLContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class BootMongoDBApp {

	@Value("${spring.data.mongodb.database}")
	private String databasename;


	public static void main(String[] args) {
		SpringApplication.run(BootMongoDBApp.class, args);
	}


	@Bean
	public SimpleMongoDbFactory mongoDbFactory() throws Exception {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		return new SimpleMongoDbFactory(mongoClient, databasename);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}

	@Bean
	public Unirest setUnirest() throws Exception {
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.build();
		Unirest.setHttpClient(httpclient);
		return null;
	}

}

/*
	@Component
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public class MyCORSFilter implements Filter {


		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "*");
			response.setHeader("Access-Control-Allow-Headers", "*");
			response.setHeader("Access-Control-Request-Headers", "*");
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Type", "application/json; charset=UTF-8");
			response.setHeader("Vary", "Accept-Encoding");

			chain.doFilter(req, res);
		}

		@Override
		public void init(FilterConfig filterConfig) {
		}

		@Override
		public void destroy() {
		}

	}
}*/
