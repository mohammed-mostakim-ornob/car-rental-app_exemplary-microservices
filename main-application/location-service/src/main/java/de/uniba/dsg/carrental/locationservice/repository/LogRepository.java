package de.uniba.dsg.carrental.locationservice.repository;

import de.uniba.dsg.carrental.locationservice.model.data.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, String> {
}
