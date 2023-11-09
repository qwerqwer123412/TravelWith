package Moap.TravelWith.repository;


import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.MatchPosting;
import Moap.TravelWith.entity.MatchStatus;
import Moap.TravelWith.entity.QMatchPosting;
import Moap.TravelWith.entity.QMatchStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
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


    public List<MatchPosting> findMatchPosting(PostingSearchDto postingSearchDto) {
        LocalDate startDate = postingSearchDto.getStartDate();
        LocalDate endDate = postingSearchDto.getEndDate();
        String query = postingSearchDto.getQuery();
        Integer money = postingSearchDto.getMoney();
        List<MatchPosting> fetch = jqf.selectFrom(qMatchPosting)
                .where(qMatchPosting.endDate.between(startDate, endDate)
                        .or(qMatchPosting.startDate.between(startDate, endDate))
                        .and(qMatchPosting.title.like("%" + query + "%"))
                        .and(qMatchPosting.travelExpenses.lt(money))
                        .and(qMatchPosting.endDate.gt(LocalDate.now())))
                .fetch();

        log.info(String.valueOf(fetch.size()));
        List<MatchPosting> result = fetch.stream().filter(i -> findMatchPeoplesNumber(i) <= i.getNumOfPeoples()).toList();
        log.info(String.valueOf(result.size()));
        return result;
    }


}
