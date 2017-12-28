package com.nea.stock.dbservice.repository;

import com.nea.stock.dbservice.model.Quote;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories(basePackages = "com.nea.stock.dbservice.repository")
//Annotation to enable JPA repositories.
// Will scan the package of the annotated configuration class for Spring Data repositories by default.
@SpringBootApplication
//Indicates a configuration class that declares one or more @Bean methods.
public interface QuotesRespository extends JpaRepository<Quote, Integer> {
    public List<Quote> findByUserName(String username);
}
