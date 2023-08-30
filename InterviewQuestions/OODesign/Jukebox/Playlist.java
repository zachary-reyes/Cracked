package Jukebox;

import java.util.Queue;

// The Playlist manages the current and next songs to play. It is essentially a wrapper class for a queue and offers some additional methods for convenience.

public class Playlist {
	private Song song;
	private Queue<Song> queue;
	public Playlist(Song song, Queue<Song> queue) {
		super();
		this.song = song;
		this.queue = queue;
	}
	
	public Song getNextSongToPlay(){ return queue.peek(); }
	public void queueUpSong(Song s){ queue.add(s); }
}

