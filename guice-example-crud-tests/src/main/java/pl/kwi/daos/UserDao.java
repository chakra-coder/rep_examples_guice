package pl.kwi.daos;

import pl.kwi.db.guice.AbstractDao;
import pl.kwi.entities.UserEntity;

public class UserDao extends AbstractDao<UserEntity> {
	
	
	public UserDao(){
		setClazz(UserEntity.class);		
	}
	
}
