package com.nea.stock.dbservice.resource;

import com.nea.stock.dbservice.model.Quote;
import com.nea.stock.dbservice.model.Quotes;
import com.nea.stock.dbservice.repository.QuotesRespository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//Spring MVC RESTful web services ile gelen bu annotaion "@Controller+@ResponseBody"nin yerini almaktadır.
// @RequestMapping ile birlikte kullanılır.
@RequestMapping("/rest/db")
//To configure the mapping of web requests  to specific handler classes/methods.
//@PathVariable ve @RequestBody, bu annotaion ile kullanılır.
public class DBServiceResource {

    private QuotesRespository quotesRespository;

    public DBServiceResource(QuotesRespository quotesRespository) {
        this.quotesRespository = quotesRespository;
    }

    //listeleme
    @GetMapping("/{username}")
    //@GetMapping is used to handle GET type of request method
    //@PathVariable: To handle dynamic URIs where one or more of the URI value works as a parameter
    public List<String> getQuotes(@PathVariable("username") final String username) {
        return getQuotesByUsername(username);
    }

    @PostMapping("/add")
    //@RequestBody: Spring'll bind the incoming HTTP request body(for the URL mentioned in @RequestMapping for that method) to that parameter
    // Spring REST API ve Angular client bunu kullanır.
    public List<String> add(@RequestBody final Quotes quotes){
        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quote -> quotesRespository.save(quote))
        ;
        return getQuotesByUsername(quotes.getUserName());
    }

    @PostMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username){
        List<Quote> quotesList = quotesRespository.findByUserName(username);
        quotesRespository.delete(quotesList);
        return getQuotesByUsername(username);
    }




    private List<String> getQuotesByUsername(@PathVariable("username") String username){
        return quotesRespository.findByUserName(username) //username'e göre repositoryden sorgulama yapılır
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
        //sonrasında REST endpoint gönderilir
    }


}
