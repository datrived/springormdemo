package com.devanshitrivedi.springormdemo.controller;

import com.devanshitrivedi.springormdemo.entity.Review;
import com.devanshitrivedi.springormdemo.service.ProductService;
import com.devanshitrivedi.springormdemo.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping({"/api/v1/products"})
@Slf4j
public class ReviewController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{productid}/reviews")
    public Page<Review> getReviewsByProductId(@PathVariable(value = "productid") long productid, Pageable pageable){
        return reviewService.findByProductId(productid, pageable);
    }

    @GetMapping("/{productid}/reviews/{id}")
    public ResponseEntity<Review> getReviewByIdAndProductId(@PathVariable(value = "productid") long productid, @PathVariable(value = "id") long id){
        Optional<Review> review = reviewService.findByIdAndProductId(id, productid);

        if (!review.isPresent()){
            log.error("Product with id " + productid + " or Review with id " + id + " does not exist.");
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(review.get());
    }

    @PostMapping("/{productid}/reviews")
    public ResponseEntity<Review> createReview(@PathVariable long productid, @Valid @RequestBody Review review){

        return productService.findById(productid).map(product -> {
            review.setProduct(product);
            return ResponseEntity.ok(reviewService.createOrUpdate(review));
        }).orElse(ResponseEntity.notFound().build()
         );
    }

    @PutMapping("/{productid}/reviews/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable(value = "productid") long productid, @PathVariable(value = "id") long id, @Valid @RequestBody Review review){
        Optional<Review> review_updated = reviewService.findByIdAndProductId(id, productid);

        if(!review_updated.isPresent()){
            log.error("Review with id " + id + " or product id " + productid + " not found.");
            return ResponseEntity.notFound().build();
        }

        review_updated.get().setScore(review.getScore());
        review_updated.get().setBy(review.getBy());
        review_updated.get().setComment(review.getComment());

        return ResponseEntity.ok(reviewService.createOrUpdate(review_updated.get()));
    }

    @DeleteMapping("/{productid}/reviews/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(value = "productid") long productid, @PathVariable(value = "id") long id){
        Optional<Review> review = reviewService.findByIdAndProductId(id, productid);

        if(!review.isPresent()){
            log.error("Review with id " + id + " or product id " + productid + " not found.");
            return ResponseEntity.notFound().build();
        }

        reviewService.deleteById(review.get().getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productid}/reviews/score/{score}")
    public ResponseEntity<List<Review>> findByProductIdAndReviewScore(@PathVariable(value = "productid") Long productid, @PathVariable(value = "score") Integer score){
        return ResponseEntity.ok(reviewService.findByProductIdAndReviewScore(productid, score));
    }

}
