package Moap.TravelWith.repository;

import Moap.TravelWith.entity.QAssessment;
import Moap.TravelWith.entity.QMatchPosting;
import Moap.TravelWith.entity.QMatchStatus;
import Moap.TravelWith.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory jqf;
    private final QMatchPosting qMatchPosting = new QMatchPosting("qm");
    private final QMatchStatus qMatchStatus = new QMatchStatus("qMatchStatus");
    private final QMember qMember = new QMember("member");
    private final QAssessment qAssessment = new QAssessment("a");

    }
