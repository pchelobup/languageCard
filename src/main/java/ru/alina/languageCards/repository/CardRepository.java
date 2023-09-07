package ru.alina.languageCards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.alina.languageCards.model.Card;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT c FROM card c WHERE c.user.id=:userId")
    List<Card> getAll(@Param("userId") long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM card c WHERE c.id=:id AND c.user.id=:userId")
    int delete(@Param("id") Long id, @Param("userId") Long userId);
}
