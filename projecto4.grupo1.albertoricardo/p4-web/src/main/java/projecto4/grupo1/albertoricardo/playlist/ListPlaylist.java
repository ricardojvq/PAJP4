package projecto4.grupo1.albertoricardo.playlist;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import projecto4.grupo1.albertoricardo.MusicEntity;
import projecto4.grupo1.albertoricardo.PlaylistEJB;
import projecto4.grupo1.albertoricardo.PlaylistEntity;
import projecto4.grupo1.albertoricardo.UserEntity;
import projecto4.grupo1.albertoricardo.user.UserLogged;

@Named
@SessionScoped
public class ListPlaylist implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private PlaylistEJB plistejb;

	@Inject
	private UserLogged userlog;

	private List<PlaylistEntity> playlists;

	private PlaylistEntity selectedPlaylist;

	public List<PlaylistEntity> ownPlaylists(UserEntity u) {
		if (playlists == null) {
			try {
				playlists = plistejb.getOwnPlaylists(u.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return playlists;
	}

	public List<PlaylistEntity> getPlaylists() {
		if (playlists == null) {
			try {
				playlists = plistejb.getOwnPlaylists(userlog.getUser().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return playlists;
	}

	public void addToSelectedPlaylist(MusicEntity music) {
		boolean duplicate = false;
		for (MusicEntity m:selectedPlaylist.getMusics()) {
			if (m.getId() == music.getId()) duplicate = true;
			FacesMessage msg = new FacesMessage("Música já existe na Playlist seleccionada.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if (!duplicate) {
			try {
				int s1 = selectedPlaylist.getMusics().size();
				selectedPlaylist.getMusics().add(music);
				int s2 = selectedPlaylist.getMusics().size();
				System.out.println("DIFERENÇA: "+(s2-s1));
				plistejb.update(selectedPlaylist);
				FacesMessage msg = new FacesMessage("Música "+music.getTitle()+" adicionada com successo à playlist seleccionada.");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} catch (Exception e) {
				FacesMessage msg = new FacesMessage("Erro: "+e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}
	
	public void removeMusicFromPlaylist(PlaylistEntity playlist, MusicEntity music) {
	}

	public void setPlaylists(List<PlaylistEntity> playlists) {
		this.playlists = playlists;
	}

	public PlaylistEntity getSelectedPlaylist() {
		return selectedPlaylist;
	}

	public void setSelectedPlaylist(PlaylistEntity selectedPlaylist) {
		this.selectedPlaylist = selectedPlaylist;
	}



}
