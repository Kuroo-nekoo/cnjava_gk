package spring.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import spring.Entity.CustomUserDetailsEntity;
import spring.Entity.UserEntity;
import spring.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if (userEntity != null) {
			CustomUserDetailsEntity customUser = new CustomUserDetailsEntity(userEntity);
            return new org.springframework.security.core.userdetails.User(customUser.getUsername()
                    , customUser.getPassword(), customUser.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }           
	}
	
	 

}
