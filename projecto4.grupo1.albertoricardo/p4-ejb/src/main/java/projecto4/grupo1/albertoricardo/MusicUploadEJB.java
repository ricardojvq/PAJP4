package projecto4.grupo1.albertoricardo;

import java.util.Date;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import projecto4.grupo1.albertoricardo.MusicEntity;




@Stateless
public class MusicUploadEJB implements MusicUploadEJBLocal {
	
    @PersistenceContext(name="Playlist")
    private EntityManager em;

 
//    The method merge creates or updates an entity,  
//    we cannot remove not-attached entities 
//    - we have to find them first. 
//    This is the "Seek And Destroy" pattern 
    
  
	@Override
	public void uploadMusicDB(String title, String artist, String album, Date dateReleased, String path) {
		System.out.println("Entrou no EJB");
		MusicEntity me = new MusicEntity();
		me.setTitle(title);
		me.setArtist(artist);
		me.setAlbum(album);
		me.setDateRecord(dateReleased);
		me.setPathFile(path);
		
		em.persist(me);
		System.out.println("Fez persist!");
	}
}