package kh.edu.paragoniu.mrs.service;

import kh.edu.paragoniu.mrs.entity.Motorbike;
import kh.edu.paragoniu.mrs.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorbikeService {

    private final MotorbikeRepository motorbikeRepository;

    public MotorbikeService(MotorbikeRepository motorbikeRepository) {
        this.motorbikeRepository = motorbikeRepository;
    }

    // ---- CRUD: Create ----
    public Motorbike addMotorbike(Motorbike motorbike) {
        return motorbikeRepository.save(motorbike);
    }

    // ---- CRUD: Read ----
    public List<Motorbike> getAllMotorbikes() {
        return motorbikeRepository.findAll();
    }

    public List<Motorbike> getAvailableMotorbikes() {
        return motorbikeRepository.findByStatus(Motorbike.Status.AVAILABLE);
    }

    public Optional<Motorbike> getMotorbikeById(Integer id) {
        return motorbikeRepository.findById(id);
    }

    // ---- CRUD: Update ----
    public Motorbike updateMotorbike(Motorbike motorbike) {
        return motorbikeRepository.save(motorbike);
    }

    public void updateStatus(Integer id, Motorbike.Status status) {
        motorbikeRepository.findById(id).ifPresent(m -> {
            m.setStatus(status);
            motorbikeRepository.save(m);
        });
    }

    // ---- CRUD: Delete ----
    public void deleteMotorbike(Integer id) {
        motorbikeRepository.deleteById(id);
    }

    public long countAvailable() {
        return motorbikeRepository.findByStatus(Motorbike.Status.AVAILABLE).size();
    }

    public long countAll() {
        return motorbikeRepository.count();
    }
}