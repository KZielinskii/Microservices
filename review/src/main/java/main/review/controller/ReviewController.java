package main.review.controller;

import main.review.domain.Review;
import main.review.repo.ReviewRepository;
import main.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;

    }

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody Review review){;
        System.out.println(review);
        reviewService.save(review);
        return ResponseEntity.ok("Review added");
    }
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviews(@RequestParam(name = "gameName")String gameName){
        List<Review> reviews = reviewService.findByGameName(gameName);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/review-page")
    public ResponseEntity<Page<Review>> getReviewsPerPage(@RequestParam("gameName")String gameName,
                                                          @RequestParam(name = "page", defaultValue = "0")int page,
                                                          @RequestParam(name = "size", defaultValue = "10") int size,
                                                          @RequestParam(name = "sortBy", defaultValue = "id")String sortBy){

        Page<Review> pages = reviewService.findByGameNamePaged(gameName, page,size, sortBy);
        return ResponseEntity.ok(pages);
    }
    @GetMapping("/average-rating")
    public ResponseEntity<Double> getAverageRating(@RequestParam("gameName") String gameName) {
        double averageRating = reviewService.getAverageRating(gameName);
        return ResponseEntity.ok(averageRating);
    }
}
