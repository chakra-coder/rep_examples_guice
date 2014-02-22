package pl.kwi.services;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

import pl.kwi.daos.UserDao;
import pl.kwi.entities.UserEntity;

public class UserService {

	@Inject
	private UserDao userDao;
	
	/**
	 * Method creates user in database.
	 * 
	 * @param user object <code>UserEntity</code> with entity of
	 * user which should be created in database
	 * @return object <code>Long</code> with id of created user
	 */
	@Transactional
	public Long createUser(UserEntity user){
		
		userDao.create(user);
		return user.getId();
		
	}
	
	/**
	 * Method gets user with specified id from database.
	 * 
	 * @param id object <code>Long</code> with id of user which
	 * should be get from database.
	 * @return object <code>UserEntity</code> with user from database
	 * with specified id
	 */
	@Transactional
	public UserEntity readUser(Long id){
		
		return userDao.read(id);
		
	}
	
	/**
	 * Method updates user in database.
	 * 
	 * @param user object <code>UserEntity</code> with entity of
	 * user which should be updated in database
	 */
	@Transactional
	public void updateUser(UserEntity user){
		
		userDao.update(user);
		
	}
	
	/**
	 * Method deletes user from database.
	 * 
	 * @param user object <code>UserEntity</code> with entity of
	 * user which should be deleted in database
	 */
	@Transactional
	public void deleteUser(UserEntity user){
		
		userDao.deleteById(user.getId());
		
	}
	
	/**
	 * Method gets list of all users from database.
	 * 
	 * @return list of all users from database
	 */
	@Transactional
	public List<UserEntity> getUserList(){
		
		return userDao.findAll();
		
	}

}
