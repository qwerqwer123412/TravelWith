package Moap.TravelWith.repository;


import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
//matchPosting이랑 matchStatus를 같이 관리합니다..
public class MatchPostingRepository {


    private final EntityManager em;
    private final JPAQueryFactory jqf;
    private final QMatchPosting qMatchPosting = new QMatchPosting("qm");
    private final QMatchStatus qMatchStatus = new QMatchStatus("qMatchStatus");
    private final QMember qMember = new QMember("member");
    private final QAssessment qAssessment = new QAssessment("a");


    public void joinMatchPosting(MatchPosting matchPosting) {
        em.persist(matchPosting);
    }

    public void joinMatchStatus(MatchStatus matchStatus) {
        em.persist(matchStatus);
    }

    public void joinAsessment(Assessment assessment){
        em.persist(assessment);
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

        List<MatchPosting> result = fetch.stream().filter(i -> findMatchPeoplesNumber(i) <= i.getNumOfPeoples()).toList();
        return result;
    }

    //내가 참여한 모든 종료된(성사된) matchPosting 작성
    public List<MatchPosting> findAllEndedMatchPosting(Long memberId){
        List<MatchStatus> fetch = jqf.selectFrom(qMatchStatus).where(qMatchStatus.member.id.eq(memberId)).fetch();
        List<MatchPosting> list = fetch.stream().map(MatchStatus::getMatchPosting).toList();
        return list.stream().filter((i) -> i.getNumOfPeoples() >= this.findMatchPeoplesNumber(i)).collect(Collectors.toSet()).stream().toList();
    }

    public List<Member> findEndMatchingJoiner(MatchPosting matchPosting){
        return jqf.select(qMatchStatus.member).from(qMatchStatus)
                .innerJoin(qMatchStatus.matchPosting, qMatchPosting)
                .where(qMatchPosting.eq(matchPosting)).fetch();
    }
    public List<Assessment> findAsessment(Member evaluator, Member assessedMember, MatchPosting matchPosting){

        return jqf.select(qAssessment).from(qAssessment)
                .where(qAssessment.receiver.eq(assessedMember)
                        .and(qAssessment.evaluator.eq(evaluator))
                        .and(qAssessment.matchPosting.eq(matchPosting))).fetch();
    }
}
