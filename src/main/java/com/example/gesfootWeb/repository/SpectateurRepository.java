package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Spectateur;
import com.example.gesfootWeb.entity.Spectateur;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpectateurRepository extends JpaRepository<Spectateur, Long> {
     Optional<Spectateur> findByEmail(String email);
}
