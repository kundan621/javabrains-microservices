package io.javabrains.moviecatalogservice.resources;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;
    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){


       //Movie movie= restTemplate.getForObject("http://localhost:8082/movies/hop", Movie.class);
        //get all rated movie iD's

        UserRating ratings=restTemplate.getForObject("http://RATINGS-DATA-SERVICE/ratingsData/users/"+userId, UserRating.class);
             return ratings.getUserRating().stream().map(rating ->{
                 //for each movie ID, call movie info service and get details
                 Movie movie=restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/"+rating.getMovieId(), Movie.class);
                 //put them all together
                 return new CatalogItem(movie.getName(),"desc",rating.getRating());

    })
    .collect(Collectors.toList());



    }


    @RequestMapping("/hope11")
    public List<String> getReply(){
        return Collections.singletonList("Hello hola hhuuu");
    }

}
