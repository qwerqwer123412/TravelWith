package Moap.TravelWith.repository;


import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.entity.MatchStatus;
import Moap.TravelWith.entity.QMatchPosting;
import Moap.TravelWith.entity.QMatchStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
//matchPosting이랑 matchStatus를 같이 관리합니다..
public class MatchPostingRepository {


    private final EntityManager em;
    private final JPAQueryFactory jqf;
    private final QMatchPosting qMatchPosting = new QMatchPosting("qm");
    private final QMatchStatus qMatchStatus = new QMatchStatus("qMatchStatus");



    public void joinMatchPosting(MatchPosting matchPosting) {
        em.persist(matchPosting);
    }

    public void joinMatchStatus(MatchStatus matchStatus) {
        em.persist(matchStatus);
    }

    public MatchPosting findMatchPostingById(Long matchPostingId) {
        return em.find(MatchPosting.class, matchPostingId);
    }

    public MatchStatus findMatchStatusById(Long matchStatusId) {
        return em.find(MatchStatus.class, matchStatusId);
    }

    public Integer findMatchPeoplesNumber(MatchPosting matchPosting) {

        return jqf.select(qMatchStatus).from(qMatchStatus)
                .innerJoin(qMatchStatus.matchPosting, qMatchPosting)
                .where(qMatchPosting.eq(matchPosting)).fetch().size();

    }


}
