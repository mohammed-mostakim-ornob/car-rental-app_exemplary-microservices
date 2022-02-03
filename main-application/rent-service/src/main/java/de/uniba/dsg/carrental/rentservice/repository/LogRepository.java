package de.uniba.dsg.carrental.rentservice.repository;

import de.uniba.dsg.carrental.rentservice.model.data.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, String> {
}
