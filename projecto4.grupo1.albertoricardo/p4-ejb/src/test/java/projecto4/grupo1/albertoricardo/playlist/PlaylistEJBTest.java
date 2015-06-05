package projecto4.grupo1.albertoricardo.playlist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import projecto4.grupo1.albertoricardo.PlaylistCRUD;
import projecto4.grupo1.albertoricardo.PlaylistEJB;
import projecto4.grupo1.albertoricardo.PlaylistEntity;
import projecto4.grupo1.albertoricardo.UserEntity;

@RunWith(MockitoJUnitRunner.class)
public class PlaylistEJBTest {
	
	@Mock
	EntityManager em;
	@Mock
	PlaylistCRUD plcrud;
	@Mock
	Query mockedQuery;
	
	
	@InjectMocks
	PlaylistEJB plejb;

	@Test
	public void getPlaylistsTest() {
		Mockito.when((em).createQuery("SELECT p FROM PlaylistEntity p WHERE p.userOwner.id = :id")).thenReturn(mockedQuery);
		Mockito.when(mockedQuery.getResultList()).thenReturn(new ArrayList<PlaylistEntity>());
		List<PlaylistEntity> p = plejb.getPlaylists();
		int size = p.size();
		assertEquals(0, size);
	}

}