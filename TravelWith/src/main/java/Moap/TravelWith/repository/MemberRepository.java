package Moap.TravelWith.repository;

import Moap.TravelWith.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findMemberById(Long memberId);
}
