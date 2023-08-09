package spring.Service;

import java.util.List;

import spring.Entity.UserEntity;

public interface UserService {

	UserEntity findByUsername(String username);
	UserEntity getByUsername(String username);
	void saveUser(UserEntity userEntity);
	void deleteUserById(Long id);
	void deleteUser(UserEntity userEntity);
	List<UserEntity> findAll();
	UserEntity getById(Long id);
	void updateInfo(UserEntity userEntity);
}
