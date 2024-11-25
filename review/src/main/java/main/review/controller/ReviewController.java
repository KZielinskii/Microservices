package main.review.controller;

import main.review.domain.Review;
import main.review.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    ReviewRepository reviewRepository;

    @Autowired
    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @PostMapping("/addReview")
    public ResponseEntity<String> addReview(@RequestBody Review review){;
        System.out.println(review);
        reviewRepository.save(review);
        return ResponseEntity.ok("Review added");
    }
    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getReviews(String gameName){
        List<Review> reviews = reviewRepository.findByGameName(gameName);
        return ResponseEntity.ok(reviews);
    }
}
