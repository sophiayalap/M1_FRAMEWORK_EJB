package fr.pantheonsorbonne.ufr27.miage.tests.utils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import fr.pantheonsorbonne.ufr27.miage.dao.ArretDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.GareDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.IncidentDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.ItineraireDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.TrainDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.TrajetDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.VoyageDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.VoyageurDAO;
import fr.pantheonsorbonne.ufr27.miage.jpa.Arret;
import fr.pantheonsorbonne.ufr27.miage.jpa.Gare;
import fr.pantheonsorbonne.ufr27.miage.jpa.Incident;
import fr.pantheonsorbonne.ufr27.miage.jpa.Itineraire;
import fr.pantheonsorbonne.ufr27.miage.jpa.Train;
import fr.pantheonsorbonne.ufr27.miage.jpa.TrainAvecResa;
import fr.pantheonsorbonne.ufr27.miage.jpa.Trajet;
import fr.pantheonsorbonne.ufr27.miage.jpa.Voyage;
import fr.pantheonsorbonne.ufr27.miage.jpa.Voyageur;

@ManagedBean
@RequestScoped
public class TestDatabase {

	@Inject
	EntityManager em;
	@Inject
	VoyageurDAO voyageurDAO;
	@Inject
	VoyageDAO voyageDAO;
	@Inject
	TrajetDAO trajetDAO;
	@Inject
	ItineraireDAO itineraireDAO;
	@Inject
	IncidentDAO incidentDAO;
	@Inject
	ArretDAO arretDAO;
	@Inject
	TrainDAO trainDAO;
	@Inject
	GareDAO gareDAO;

	public void clear() {
		em.getTransaction().begin();
		// On vide la table de jointure entre les itinéraires et les voyageurs
		for (Itineraire itineraire : itineraireDAO.getAllItineraires()) {
			itineraire.getVoyageurs().clear();
		}
		// On vide la table de jointure entre les trains et les voyageurs
		for (Train train : trainDAO.getAllTrains()) {
			if (train instanceof TrainAvecResa) {
				((TrainAvecResa) train).getVoyageurs().clear();
			}
		}
		// On vide la table de jointure entre les voyages et les voyageurs
		for (Voyage voyage : voyageDAO.getAllVoyages()) {
			voyage.getVoyageurs().clear();
		}
		// On vide toutes les tables dans le sens inverse de persistence
		for (Voyageur voyageur : voyageurDAO.getAllVoyageurs()) {
			em.remove(voyageur);
		}
		for (Voyage voyage : voyageDAO.getAllVoyages()) {
			em.remove(voyage);
		}
		for (Trajet trajet : trajetDAO.getAllTrajets()) {
			em.remove(trajet);
		}
		for (Itineraire itineraire : itineraireDAO.getAllItineraires()) {
			itineraire.setIncident(null);
			em.remove(itineraire);
		}
		for (Incident incident : incidentDAO.getAllIncidents()) {
			em.remove(incident);
		}
		for (Arret arret : arretDAO.getAllArrets()) {
			em.remove(arret);
		}
		for (Train train : trainDAO.getAllTrains()) {
			em.remove(train);
		}
		for (Gare gare : gareDAO.getAllGares()) {
			em.remove(gare);
		}
		em.getTransaction().commit();
		em.clear();
		// On remet tous les business id à 1 pour la cohérence des tests
		Train.businessIdTrainCount = 1;
		Itineraire.businessIdItineraireCount = 1;
		Incident.businessIdIncidentCount = 1;
	}

}
