package Moap.TravelWith.repository;

import Moap.TravelWith.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ImageRepository extends JpaRepository<Image, Long> {
//    저장하고 URL 리텅



}
