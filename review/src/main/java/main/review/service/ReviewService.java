package main.review.service;

import main.review.domain.Review;
import main.review.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository=reviewRepository;
    }

    public Review save(Review review){
        return reviewRepository.save(review);
    }
    public List<Review> findByGameName(String gameName){
        return reviewRepository.findByGameName(gameName);
    }

    public Page<Review> findByGameNamePaged(String gameName, int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return reviewRepository.findByGameName(gameName,pageable);
    }
}
