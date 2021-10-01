package SongLib.App;

public class Song {
	private String songName;
	private String artistName;
	private String year;
	private String album;
	
	public Song(String songName, String artistName, String year, String album) {
		this.songName = songName;
		this.artistName = artistName;
		this.year = year;
		this.album = album;
	}
	
	public void setName(String name) {
		this.songName = name;
	}
	
	public String getSongName() {
		return songName;
	}
	
	public void setArtistName(String name) {
		this.artistName = name;
	}
	
	public String getArtistName() {
		return artistName;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setAlbumName(String album) {
		this.album = album;
	}
	
	public String getAlbumName() {
		return album;
	}
	
	public String fullName() {
		return  songName + "\t" + artistName + "\t" + year + "\t" + album;
	}
	
	public String toString() {
		return songName + " | " + artistName;
	}
	
}