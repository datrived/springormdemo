package com.devanshitrivedi.springormdemo.repository;

import com.devanshitrivedi.springormdemo.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;


@Repository
@Transactional(readOnly = true)
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable);
    Optional<Review> findByIdAndProductId(Long id, Long productId);

    //Find all reviews with specific Product ID and Review Score
    @Query(value = "SELECT * FROM review WHERE product_id = :id and score = :score", nativeQuery = true)
    List<Review> findByProductIdAndReviewScore(@Param("id") Long id, @Param("score") Integer score);

}
