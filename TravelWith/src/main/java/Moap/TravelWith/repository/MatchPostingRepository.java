package Moap.TravelWith.repository;


import Moap.TravelWith.dto.PostingSearchDto;
import Moap.TravelWith.entity.*;
import Moap.TravelWith.enumer.MatchRole;
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

    public void joinAsessment(Assessment assessment) {
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


    public List<MatchPosting> findMatchPostingDetail(PostingSearchDto postingSearchDto) {
        LocalDate startDate = postingSearchDto.getStartDate();
        LocalDate endDate = postingSearchDto.getEndDate();
        String query = postingSearchDto.getQuery();
        Integer money = postingSearchDto.getMoney();


        List<MatchPosting> fetch;
        if (query.isEmpty()) {
            fetch = jqf.select(qMatchPosting).from(qMatchPosting)
                    .where((qMatchPosting.endDate.loe(endDate).and(qMatchPosting.startDate.goe(startDate)))
                            .and(qMatchPosting.travelExpenses.loe(money))).fetch();

            log.info("without string");
        } else {
            fetch = jqf.select(qMatchPosting
                    ).from(qMatchPosting)
                    .where((qMatchPosting.endDate.loe(endDate).and(qMatchPosting.startDate.goe(startDate)))
                            .and(qMatchPosting.title.like("%" + query + "%"))
                            .and(qMatchPosting.travelExpenses.loe(money))).fetch();
            log.info("with string");
        }

        log.info("11111111111111111111111111");
        fetch = fetch.stream().filter(i -> findMatchPeoplesNumber(i) <= i.getNumOfPeoples()).toList();
        fetch.forEach(System.out::println);
        log.info("122222222222222222222222");
        return fetch.stream().filter(matchPosting -> matchPosting.getEndDate().isEqual(LocalDate.now()) ||
                matchPosting.getEndDate().isAfter(LocalDate.now())).toList();
    }

    public List<MatchPosting> findMatchPostingWithString(String query) {

        List<MatchPosting> fetch;
        if (query.isEmpty() ) {
            fetch = jqf
                    .select(qMatchPosting)
                    .from(qMatchPosting).fetch();

        } else {
            fetch = jqf
                    .select(qMatchPosting)
                    .from(qMatchPosting)
                    .where(qMatchPosting.title.like("%" + query + "%")).fetch();
        }

        log.info("hi");
        fetch = fetch.stream().filter(i -> {

            System.out.println(i.getNumOfPeoples());
            System.out.println(findMatchPeoplesNumber(i));
            return findMatchPeoplesNumber(i) <= i.getNumOfPeoples();}).toList();
        return fetch.stream().filter(matchPosting -> matchPosting.getEndDate().isEqual(LocalDate.now()) ||
                matchPosting.getEndDate().isAfter(LocalDate.now())).toList();
    }


    //내가 참여한 모든 종료된(성사된) matchPosting 작성
    public List<MatchPosting> findAllEndedMatchPosting(Long memberId) {
        List<MatchStatus> fetch = jqf.selectFrom(qMatchStatus).where(qMatchStatus.member.id.eq(memberId)).fetch();
        List<MatchPosting> list = fetch.stream().map(MatchStatus::getMatchPosting).toList();
        return list.stream().filter((i) -> i.getNumOfPeoples() <= this.findMatchPeoplesNumber(i)).collect(Collectors.toSet()).stream().toList();
    }

    //내가 참여한 모든 진행중인(미성사) matchPosting 작성
    public List<MatchPosting> findAllMatchPostingProgress(Long memberId) {
        List<MatchStatus> fetch = jqf.selectFrom(qMatchStatus).where(qMatchStatus.member.id.eq(memberId)).fetch();
        List<MatchPosting> list = fetch.stream().map(MatchStatus::getMatchPosting).toList();
        return list.stream().filter((i) -> i.getNumOfPeoples() > this.findMatchPeoplesNumber(i)).collect(Collectors.toSet()).stream().toList();
    }

    public List<Member> findMatchingJoiner(MatchPosting matchPosting) {
        return jqf.select(qMatchStatus.member).from(qMatchStatus)
                .innerJoin(qMatchStatus.matchPosting, qMatchPosting)
                .where(qMatchPosting.eq(matchPosting)).fetch();
    }


    public List<Member> findHost(MatchPosting matchPosting) {
        List<Member> fetch = jqf.select(qMatchStatus.member).from(qMatchStatus)
                .innerJoin(qMatchStatus.matchPosting, qMatchPosting)
                .where(qMatchPosting.eq(matchPosting).and(qMatchStatus.role.eq(MatchRole.HOST))).fetch();

        if (fetch.isEmpty()) {
            throw new RuntimeException("no host exception");
        }
        return fetch;
    }


    public List<Member> findParticipant(MatchPosting matchPosting) {
        return jqf.select(qMatchStatus.member).from(qMatchStatus)
                .innerJoin(qMatchStatus.matchPosting, qMatchPosting)
                .where(qMatchPosting.eq(matchPosting).and(qMatchStatus.role.eq(MatchRole.PARTICIPANT))).fetch();


    }

    public List<Assessment> findAsessment(Member evaluator, Member assessedMember, MatchPosting matchPosting) {

        return jqf.select(qAssessment).from(qAssessment)
                .where(qAssessment.receiver.eq(assessedMember)
                        .and(qAssessment.evaluator.eq(evaluator))
                        .and(qAssessment.matchPosting.eq(matchPosting))).fetch();
    }
}
