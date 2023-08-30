package Jukebox;

import java.util.Set;

import javax.sound.midi.Soundbank;

/* 7.3 Jukebox: Design a musical jukebox using OO principles
 * 
 * Hints: 198
 * -> Scope the problem first and make a list of your assumptions. It's often okay to make reasonable assumptions, but you need to make them explicit.
 */
public class Jukebox {
    private CDPlayer cdPlayer;
    private User user;
    private Set<CD> cdCollection;
    private SongSelector ts;

    public Jukebox(CDPlayer cdPlayer, User user, Set<CD> cdCollection, SongSelector ts) {
        super();
        this.cdPlayer = cdPlayer;
        this.user = user;
        this.cdCollection = cdCollection;
        this.ts = ts;
    }

    public Song getCurrentSong() {return ts.getCurrentSong();}

    public void setUser(User u) {this.user = u;}
}
