package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Rencontre;
import com.example.gesfootWeb.entity.Rencontre;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RencontreRepository extends JpaRepository<Rencontre, Long> {

    public List<Rencontre> findByEquipe1_NomContaining(String equipe1);

    @Query("SELECT r FROM Rencontre r WHERE DATE(r.dateEtHeure) = ?1")
    List<Rencontre> findByDate(LocalDate date);
List<Rencontre> findByEquipe2Id(Long equipe2Id);
}
