package Moap.TravelWith.repository;

import Moap.TravelWith.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    public Member findMemberById(Long memberId);

    Optional<Member> findByEmail(String email);

}
