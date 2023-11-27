package Moap.TravelWith.repository;

import Moap.TravelWith.entity.LoginCheck;
import Moap.TravelWith.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LoginCheckRepository extends JpaRepository<LoginCheck, Long> {

    public Optional<LoginCheck> findLoginCheckByEmail(String email);
    @Transactional
    public void deleteLoginCheckByEmail(String email);

}
