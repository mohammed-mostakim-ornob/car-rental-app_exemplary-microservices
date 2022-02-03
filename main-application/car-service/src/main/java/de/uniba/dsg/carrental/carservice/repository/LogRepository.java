package de.uniba.dsg.carrental.carservice.repository;

import de.uniba.dsg.carrental.carservice.model.data.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, String> {
}
