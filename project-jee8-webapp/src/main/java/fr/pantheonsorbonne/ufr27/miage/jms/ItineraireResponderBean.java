package fr.pantheonsorbonne.ufr27.miage.jms;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.xml.bind.JAXBException;

@ApplicationScoped
@ManagedBean
public class ItineraireResponderBean implements MessageListener{

	@Inject
	EntityManager em;

	@Inject
	MessageGateway messageGateway;

	@PostConstruct
	private void init() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						System.out.println("Thread launch");
						Message msg = messageGateway.ackInfogare();
						onMessage(msg);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	@Override
	public void onMessage(Message incomingRequest) {
		try {
			messageGateway.ackReplyItineraire(incomingRequest);
		} catch (JMSException | JAXBException e) {
			e.printStackTrace();
		}
	}

}
