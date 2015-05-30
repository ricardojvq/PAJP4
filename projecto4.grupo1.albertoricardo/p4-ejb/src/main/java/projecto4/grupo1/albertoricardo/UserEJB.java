package projecto4.grupo1.albertoricardo;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projecto4.grupo1.albertoricardo.security.PasswordEncryptor;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
public class UserEJB implements UserEJBLocal {
	@PersistenceContext(name="Playlist")
	EntityManager em;

	/**
	 * Default constructor. 
	 */
	public UserEJB() {

	}
	
	@Override
	public boolean verifyLogin(String email, String password) {
		boolean verified;
		Query q = em.createQuery("select u from UserEntity u where u.email like :e")
		.setParameter("e", email);
		try {
			UserEntity usr = (UserEntity) q.getSingleResult();
			PasswordEncryptor pe = new PasswordEncryptor();
			if (pe.check(password, usr.getPassword())) verified = true;
			else verified = false;
		} catch (NoResultException nre) {
			nre.printStackTrace();
			verified = false;
		}

		return verified;
	}
	
	@Override
	public void registerUser(String username, String password, String name) {
		PasswordEncryptor pe = new PasswordEncryptor();
		String ePassword = pe.encrypt(password);
		UserEntity u = new UserEntity(username, ePassword, name);
		em.persist(u);
	}
	
	@Override
	public boolean changeUser(int id, String newName, String newPassword) {
		boolean success = false;
		try {
			Query q = em.createQuery("UPDATE UserEntity u SET u.name = :n, u.password = :p WHERE id = :id")
					.setParameter("n", newName)
					.setParameter("p", newPassword)
					.setParameter("id", id);
			int up = q.executeUpdate();
			if (up == 1) success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
		
	}
	
	@Override
	public boolean deleteUser(int id) {
		boolean success = false;
		try {
			Query q = em.createQuery("DELETE FROM UserEntity u WHERE u.id = :id")
					.setParameter("id", id);
			int up = q.executeUpdate();
			if (up == 1) success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

	@Override
	public int getUserID(String username) {
		int id = -1;
		Query q = em.createQuery("select u from UserEntity u where u.email like :e");
		q.setParameter("e", username);
		UserEntity u = (UserEntity) q.getSingleResult();
		id = u.getId();
		return id;
	}
	
	@Override
	public String getName(String username) {
		Query q = em.createQuery("select u from UserEntity u where u.email like :e");
		q.setParameter("e", username);
		UserEntity u = (UserEntity) q.getSingleResult();
		String name = u.getName();
		return name;
	}

	@Override
	public UserEntity getUserEntity(String username) {
		Query q = em.createQuery("select u from UserEntity u where u.email like :e");
		q.setParameter("e", username);
		return (UserEntity) q.getSingleResult();
		
	}

}
