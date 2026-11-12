package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Equipe;
import com.example.gesfootWeb.entity.Equipe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {

     List<Equipe> findAll();
   // @Query("SELECT e FROM Equipe e WHERE e.nom LIKE %:keyword%")
   // List<Equipe> searchByKeyword(@Param("keyword") String keyword);
    // Rechercher les équipes par nom ou pays en utilisant LIKE
   // @Query("SELECT e FROM Equipe e WHERE e.nom LIKE %:keyword% OR e.pays LIKE %:keyword%")
   // List<Equipe> searchByKeyword(String keyword);
    @Query("SELECT e FROM Equipe e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(e.pays) LIKE LOWER(CONCAT('%', :keyword, '%'))")
List<Equipe> searchByKeyword(String keyword);

     // Recherche par nom d'équipe
   //List<Equipe> searchByKeyword(String keyword);

    // Recherche par pays
    //List<Equipe> searchByPays(String pays);
    
    // Recherche par nom d'équipe et pays
   // List<Equipe> searchByKeywordAndPays(String keyword, String pays);
    
    boolean existsByNom(String nom);  // Vérifier si une équipe existe avec le même nom
    boolean existsByPays(String pays);  // Vérifier si une équipe existe avec le même pays
    boolean existsByClassement(int classement);  // Vérifier si une équipe existe avec le même classement

    // Méthodes pour la mise à jour, en excluant l'équipe actuelle
    boolean existsByNomAndIdNot(String nom, Long id);  // Vérifier si une équipe avec le même nom existe sauf celle avec le même id
    boolean existsByPaysAndIdNot(String pays, Long id);  // Vérifier si une équipe avec le même pays existe sauf celle avec le même id
    boolean existsByClassementAndIdNot(int classement, Long id);  // Vérifier si une équipe avec le même classement existe sauf celle avec le même id
    
   /* µ@Query("select e from Equpe e where e.pays = ?1")
 public List<Equipe> chercherEquipeParNom (String nom)*/

}
