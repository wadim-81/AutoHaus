package org.workingproject45efs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.workingproject45efs.entity.Car;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query("select c from Car c join fetch c.manager")
    List<Car> findAllWithManager();

    List<Car> findByBrand(String brand);
    List<Car> findByModel(String model);
    List<Car> findByManagerId(Integer managerId);
    List<Car> findByManager_ManagerEmail(String email);
}
