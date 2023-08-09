package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.Entity.BillEntity;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long>{ }
