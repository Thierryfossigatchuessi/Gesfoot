package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Stade;
import com.example.gesfootWeb.entity.Stade;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadeRepository extends JpaRepository<Stade, Long> {
    
    public List<Stade> findByNomContaining(String nom);
}
