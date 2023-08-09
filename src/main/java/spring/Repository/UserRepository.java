package spring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import spring.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	UserEntity findByUsername(String username);
	UserEntity getByUsername(String username);

	@Modifying
    @Transactional
	@Query(value="UPDATE User u SET u.roles= ?1, u.fullname= ?2, u.address = ?3, u.phone = ?4, u.age = ?5, u.sex = ?6  WHERE u.id = ?7", nativeQuery = true)
	void updateInfo(String roles,String fullname,
			String address, String phone, int age, 
			String sex, Long id);
}
