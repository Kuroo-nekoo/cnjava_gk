package spring.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import spring.Entity.UserEntity;
import spring.Repository.UserRepository;

@Repository
@Service
public class UserServiceImp implements UserService{

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		UserEntity userexist = userRepository.findByUsername(userEntity.getUsername());
		if (userexist == null) {
			userEntity.setFullname(userEntity.getFullname());
			userEntity.setUsername(userEntity.getUsername());
			userEntity.setRoles(userEntity.getRoles());
			userEntity.setAddress(userEntity.getAddress());
			userEntity.setPhone(userEntity.getPhone());
			userEntity.setAge(userEntity.getAge());
			userEntity.setActive(true);
			userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
			userRepository.save(userEntity);
		}
	}

	@Override
	public UserEntity getByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.getByUsername(username);
	}

	@Override
	public void deleteUserById(Long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

	@Override
	public void deleteUser(UserEntity userEntity) {
		// TODO Auto-generated method stub
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserEntity> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public UserEntity getById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getById(id);
	}

	@Override
	public void updateInfo(UserEntity userEntity) {
		// TODO Auto-generated method stub
		userRepository.updateInfo(userEntity.getRoles(), userEntity.getFullname() , userEntity.getAddress(),
			userEntity.getPhone(), userEntity.getAge(), userEntity.getSex(), userEntity.getId());
	}
	
	 
}