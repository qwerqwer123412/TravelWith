package Moap.TravelWith.repository;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // 발신자를 기준으로 쪽지를 찾는 메소드
    List<Message> findBySender(Member sender);

    // 수신자를 기준으로 쪽지를 찾는 메소드
    List<Message> findByReceiver(Member receiver);
}
