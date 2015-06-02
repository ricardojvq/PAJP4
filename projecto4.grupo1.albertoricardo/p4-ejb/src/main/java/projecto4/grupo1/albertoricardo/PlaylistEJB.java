<<<<<<< HEAD
package projecto4.grupo1.albertoricardo;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Local
@Stateless
public class PlaylistEJB {

	@PersistenceContext(name="Playlist")
	private EntityManager em;

	private PlaylistCRUD pl_crud= new PlaylistCRUD();


	public void addPlaylist(String name, Date insertDate, UserEntity userlogged) {
		PlaylistEntity pl = new PlaylistEntity();
		pl.setName(name);
		pl.setInsertDate(insertDate);
		pl.setUserOwner(userlogged);
		em.persist(pl);;
	}    


	@SuppressWarnings("unchecked")
	public List<PlaylistEntity> getPlaylists() { 
		List<PlaylistEntity> pe = new ArrayList<>();
		try {
			Query q = em.createQuery("SELECT p FROM PlaylistEntity p");
			pe = (ArrayList<PlaylistEntity>) q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}

	@SuppressWarnings("unchecked")
	public List<PlaylistEntity> getOwnPlaylists(int id) { 
		List<PlaylistEntity> pe = new ArrayList<>();
		try {
			Query q = em.createQuery("SELECT p FROM PlaylistEntity p WHERE p.userOwner.id = :id")
					.setParameter("id", id);
			pe = (ArrayList<PlaylistEntity>) q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}


	public boolean findName(String name){
		boolean found=false;
		try {
			Query q = em.createQuery("select u from PlaylistEntity u where u.name like :e")
					.setParameter("e", name);
			if(q.getSingleResult()!=null)
				found=true;
		} catch (NoResultException nre) {
			found = false;
		}
		return found;
	}

	public void updateName(int id, String name){
		
		pl_crud.find(id).setName(name);
		
	}
	
	public void update(PlaylistEntity playlist) {

		pl_crud.update(playlist);
	}

}
=======
package projecto4.grupo1.albertoricardo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import projecto4.grupo1.albertoricardo.PlaylistEntity;




@Stateless
public class PlaylistEJB implements PlaylistEJBLocal {

	@PersistenceContext(name="Playlist")
	private EntityManager em;


	@Override
	public PlaylistEntity create(PlaylistEntity playlist) {
		return em.merge(playlist);
	}
	@Override
	public PlaylistEntity update(PlaylistEntity playlist) {
		return em.merge(playlist);
	}
	@Override
	public void remove(PlaylistEntity playlist) {
		em.remove(em.merge(playlist));

	}
	@Override
	public PlaylistEntity find(Object id) {
		return em.find(PlaylistEntity.class, id);
	}

	public void addPlaylist(String name, Date insertDate, UserEntity userlogged) {
		PlaylistEntity pl = new PlaylistEntity();
		pl.setName(name);
		pl.setInsertDate(insertDate);
		pl.setUserOwner(userlogged);
		em.persist(pl);
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlaylistEntity> getPlaylists() { 
		List<PlaylistEntity> pe = new ArrayList<>();
		try {
			Query q = em.createQuery("SELECT p FROM PlaylistEntity p");
			pe = (ArrayList<PlaylistEntity>) q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PlaylistEntity> getOwnPlaylists(int id) { 
		List<PlaylistEntity> pe = new ArrayList<>();
		try {
			Query q = em.createQuery("SELECT p FROM PlaylistEntity p WHERE p.userOwner.id = :id")
					.setParameter("id", id);
			pe = (ArrayList<PlaylistEntity>) q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pe;
	}
	@Override
	public boolean findName(String name){
		boolean found=false;
		try {
			Query q = em.createQuery("select u from PlaylistEntity u where u.name like :e")
					.setParameter("e", name);
			if(q.getSingleResult()!=null)
			  found=true;
		} catch (NoResultException nre) {
			found = false;
		}
		return found;
	}


}
>>>>>>> branch 'master' of https://github.com/ricardojvq/PAJP4.git
