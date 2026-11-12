package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Joueur;
import com.example.gesfootWeb.entity.Joueur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JoueurRepository extends JpaRepository<Joueur, Long> {
   // public List<Joueur> findAll() {
    //return entityManager.createQuery("SELECT j FROM Joueur j JOIN FETCH j.equipe", Joueur.class).getResultList();
    // Ajout de la méthode pour vérifier si un joueur existe par numéro
    boolean existsByNumero(int numero); // La méthode générée automatiquement par Spring Data JPA
/*@Query("SELECT j FROM Joueur j WHERE j.numero = :numero AND j.id != :id")
    Optional<Joueur> findByNumeroAndIdNot(@Param("numero") String numero, @Param("id") long id);
*/
    // Ajouter une méthode pour trouver un joueur par son numéro, en excluant un ID donné
   // Optional<Joueur> findByNumeroAndIdNot(String numero, Long id);
    
    
    @Query("SELECT j FROM Joueur j WHERE j.nom LIKE :chaine OR j.prenom LIKE :chaine")
    List<Joueur> findByNomOrPrenomStartingWith(@Param("chaine") String chaine);

}
