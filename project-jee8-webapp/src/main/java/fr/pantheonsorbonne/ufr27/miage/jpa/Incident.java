package fr.pantheonsorbonne.ufr27.miage.jpa;

import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "Incident.getAllIncidents", query = "SELECT i FROM Incident i"),
		@NamedQuery(name = "Incident.getNbIncidents", query = "SELECT COUNT(i) FROM Incident i"),
		@NamedQuery(name = "Incident.getIncidentById", query = "SELECT i FROM Incident i WHERE i.id = :id"),
		@NamedQuery(name = "Incident.getIncidentByBusinessId", query = "SELECT i FROM Incident i WHERE i.businessId = :id")

})
public class Incident {

	public static int businessIdIncidentCount = 1;

	public Incident() {
	}

	public Incident(int typeIncident) {
		this.businessId = "I" + businessIdIncidentCount++;
		this.typeIncident = typeIncident;
		this.etat = CodeEtatIncident.EN_COURS.getCode();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int id;

	String businessId;

	int typeIncident;
	int etat;

	LocalDateTime heureDebut;
	LocalDateTime heureTheoriqueDeFin;

	public int getId() {
		return id;
	}

	public int getTypeIncident() {
		return typeIncident;
	}

	public void setTypeIncident(int typeIncident) {
		this.typeIncident = typeIncident;
	}

	public LocalDateTime getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(LocalDateTime heureDebut) {
		this.heureDebut = heureDebut;
	}

	public LocalDateTime getHeureTheoriqueDeFin() {
		return heureTheoriqueDeFin;
	}

	public void setHeureTheoriqueDeFin(LocalDateTime heureTheoriqueDeFin) {
		this.heureTheoriqueDeFin = heureTheoriqueDeFin;
	}

	public void initHeureTheoriqueDeFin(LocalTime ldtDuree) {
		this.heureTheoriqueDeFin = this.heureDebut.plusSeconds(ldtDuree.toSecondOfDay());
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public enum CodeEtatIncident {

		EN_COURS(1), RESOLU(0);

		private int code;

		private CodeEtatIncident(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}
	}

	public enum CodeTypeIncident {

		ANIMAL_SUR_VOIE(1), ARBRE_SUR_VOIE(2), MALAISE_PASSAGER(3), FEUILLE_MOUILLIE_SUR_VOIE(4), PANNE_ELECTRIQUE(5),
		PERSONNE_SUR_VOIE(6);

		private int code;

		private CodeTypeIncident(int code) {
			this.code = code;
		}

		public LocalTime getTempEstimation() {
			switch (code) {
			case 1:
				return LocalTime.of(0, 15, 0, 0);

			case 2:
				return LocalTime.of(3, 0, 0, 0);

			case 3:
				return LocalTime.of(1, 15, 0, 0);

			case 4:
				return LocalTime.of(1, 30, 0, 0);

			case 5:
				return LocalTime.of(2, 15, 0, 0);

			case 6:
				return LocalTime.of(0, 30, 0, 0);

			default:
				return LocalTime.of(1, 0, 0, 0);
			}

		}

		public int getCode() {
			return code;
		}

		public static CodeTypeIncident valueOf(int codeTypeIncident) {
			CodeTypeIncident result = null;
			for (CodeTypeIncident c : CodeTypeIncident.values()) {
				if (c.getCode() == codeTypeIncident) {
					result = c;
					break;
				}
			}
			return result;
		}
	}
}
