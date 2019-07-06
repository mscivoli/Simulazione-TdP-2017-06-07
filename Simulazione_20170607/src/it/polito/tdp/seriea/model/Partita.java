package it.polito.tdp.seriea.model;

public class Partita {
	
	private String squadraHome;
	private String squadraFuori;
	private String vincitrice;
	public Partita(String squadraHome, String squadraFuori, String vincitrice) {
		super();
		this.squadraHome = squadraHome;
		this.squadraFuori = squadraFuori;
		this.vincitrice = vincitrice;
	}
	public String getSquadraHome() {
		return squadraHome;
	}
	public void setSquadraHome(String squadraHome) {
		this.squadraHome = squadraHome;
	}
	public String getSquadraFuori() {
		return squadraFuori;
	}
	public void setSquadraFuori(String squadraFuori) {
		this.squadraFuori = squadraFuori;
	}
	public String getVincitrice() {
		return vincitrice;
	}
	public void setVincitrice(String vincitrice) {
		this.vincitrice = vincitrice;
	}
	
	
	

}
