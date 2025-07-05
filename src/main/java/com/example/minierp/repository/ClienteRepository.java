package com.example.minierp.repository;

import com.example.minierp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT c FROM Cliente c WHERE c.telefone = ?1")
    Optional<Cliente> findByTelefone(String telefone);

    boolean existsByEmail(String email);
}
