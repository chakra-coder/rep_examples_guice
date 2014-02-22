package pl.kwi.daos;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.kwi.db.guice.test.DbUnitUtil;
import pl.kwi.entities.UserEntity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class UserDaoTest {
	
	
	private UserDao dao;
	private PersistService ps;
	
	
	@Before
	public void setUp() throws Exception {
		Injector injector = Guice.createInjector(new JpaPersistModule("jpaUnitTest"));
		ps = injector.getInstance(PersistService.class);
		dao = injector.getInstance(UserDao.class);		
		ps.start();
	}
	
	@After
	public void tearDown() {
		ps.stop();
	}
	

	@Test
	public void create() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		UserEntity user = new UserEntity();
		user.setName("User");		
		dao.create(user);
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		assertNotNull(user.getId());

	}
	
	@Test
	public void read() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		UserEntity user = dao.read(1L);
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		assertEquals("User1", user.getName());
		
	}
	
	@Test
	public void update() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		UserEntity user = dao.read(1L);
		user.setName("User4");
		dao.update(user);
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		user = dao.read(1L);
		
		assertEquals("User4", user.getName());
		
	}
	
	@Test
	public void delete() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		UserEntity user = dao.read(1L);
		dao.delete(user);
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		user = dao.read(1L);
		
		assertNull(user);
		
	}
	
	@Test
	public void deleteById() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		dao.deleteById(1L);
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		UserEntity user = dao.read(1L);
		
		assertNull(user);
		
	}
	
	@Test
	public void findAll() {
		
		DbUnitUtil.executeDataFile("/dbunit/userDaoTest.xml", dao.getEntityManagerProvider().get());
		
		dao.getEntityManagerProvider().get().getTransaction().begin();
		
		List<UserEntity> users = dao.findAll();
		
		dao.getEntityManagerProvider().get().getTransaction().commit();
		
		assertEquals(3, users.size());
		
	}

}
