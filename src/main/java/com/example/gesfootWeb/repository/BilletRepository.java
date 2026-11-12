package com.example.gesfootWeb.repository;

//import com.example.gesfootConsole.entity.Billet;
import com.example.gesfootWeb.entity.Billet;
import com.example.gesfootWeb.entity.Spectateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BilletRepository extends JpaRepository<Billet, Long> {

    public void save(Spectateur spectateur);
}
