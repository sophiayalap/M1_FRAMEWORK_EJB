package fr.pantheonsorbonne.ufr27.miage.main;

import javax.xml.datatype.DatatypeConfigurationException;

public class trainsLauncher {
	public static void main(String[] args) throws DatatypeConfigurationException {
		int nbtrain = 2;

		for (int idTrain = 1; idTrain < nbtrain; idTrain++) {
			Train train = new Train(idTrain);
			Thread thread = new Thread(train);
			thread.start();
		}
	}
}
