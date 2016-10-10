package com.leetron;

public class Earthquake {
	
	private String _id;
	private String event_date;
	private String event_time;
	private String latitude;
	private String longitude;
	private String depth;
	private String mag;
	private String magtype;
	private String nst;
	private String gap;
	private String dmin;
	private String rms;
	private String net;
	private String id;
	private String updated_date;
	private String updated_time;
	private String place;
	private String type;
	private String horizontalerror;
	private String deptherror;
	private String magerror;
	private String magnst;
	private String status;
	private String locationsource;
	private String magsource;
	public Earthquake(String _id, String event_date, String event_time, String latitude, String longitude, String depth,
			String mag, String magtype, String nst, String gap, String dmin, String rms, String net, String id,
			String updated_date, String updated_time, String place, String type, String horizontalerror,
			String deptherror, String magerror, String magnst, String status, String locationsource, String magsource) {
		super();
		this._id = _id;
		this.event_date = event_date;
		this.event_time = event_time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.depth = depth;
		this.mag = mag;
		this.magtype = magtype;
		this.nst = nst;
		this.gap = gap;
		this.dmin = dmin;
		this.rms = rms;
		this.net = net;
		this.id = id;
		this.updated_date = updated_date;
		this.updated_time = updated_time;
		this.place = place;
		this.type = type;
		this.horizontalerror = horizontalerror;
		this.deptherror = deptherror;
		this.magerror = magerror;
		this.magnst = magnst;
		this.status = status;
		this.locationsource = locationsource;
		this.magsource = magsource;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getMag() {
		return mag;
	}
	public void setMag(String mag) {
		this.mag = mag;
	}
	public String getMagtype() {
		return magtype;
	}
	public void setMagtype(String magtype) {
		this.magtype = magtype;
	}
	public String getNst() {
		return nst;
	}
	public void setNst(String nst) {
		this.nst = nst;
	}
	public String getGap() {
		return gap;
	}
	public void setGap(String gap) {
		this.gap = gap;
	}
	public String getDmin() {
		return dmin;
	}
	public void setDmin(String dmin) {
		this.dmin = dmin;
	}
	public String getRms() {
		return rms;
	}
	public void setRms(String rms) {
		this.rms = rms;
	}
	public String getNet() {
		return net;
	}
	public void setNet(String net) {
		this.net = net;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public String getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHorizontalerror() {
		return horizontalerror;
	}
	public void setHorizontalerror(String horizontalerror) {
		this.horizontalerror = horizontalerror;
	}
	public String getDeptherror() {
		return deptherror;
	}
	public void setDeptherror(String deptherror) {
		this.deptherror = deptherror;
	}
	public String getMagerror() {
		return magerror;
	}
	public void setMagerror(String magerror) {
		this.magerror = magerror;
	}
	public String getMagnst() {
		return magnst;
	}
	public void setMagnst(String magnst) {
		this.magnst = magnst;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLocationsource() {
		return locationsource;
	}
	public void setLocationsource(String locationsource) {
		this.locationsource = locationsource;
	}
	public String getMagsource() {
		return magsource;
	}
	public void setMagsource(String magsource) {
		this.magsource = magsource;
	}
	

}
