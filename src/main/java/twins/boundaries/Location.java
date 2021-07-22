package twins.boundaries;

public class Location {

	private double lng;
	private double lat;
	
	public Location(double lng,double lat) {
		this.lng = lng;
		this.lat = lat;
	}
	
	public Location() {}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	
}
