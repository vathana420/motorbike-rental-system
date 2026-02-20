package kh.edu.paragoniu.mrs.repository;

import kh.edu.paragoniu.mrs.entity.Motorbike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorbikeRepository extends JpaRepository<Motorbike, Integer> {
    List<Motorbike> findByStatus(Motorbike.Status status);
    boolean existsByPlateNumber(String plateNumber);
}