package Moap.TravelWith.repository;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.MyIntroduce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyIntroduceRepository extends JpaRepository<MyIntroduce, Long> {
    Optional<MyIntroduce> findByMember(Member member);
}
