
package acme.features.assistanceAgent.trackingLogs;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;
import acme.entities.trackingLogs.TrackingLog;

@Repository
public interface AssistanceAgentTrackingLogsRepository extends AbstractRepository {

	@Query("select tl from TrackingLog tl where tl.assistanceAgent.id = :Id ")
	Collection<TrackingLog> getAllTrackingLogsByAssistanceAgent(int Id);

	@Query("select t from TrackingLog t where t.claim.id = :Id order by t.lastUpdateMoment desc")
	Collection<TrackingLog> getAllTrackingLogsByClaim(int Id);

	@Query("select tl from TrackingLog tl where tl.id = :Id")
	TrackingLog getTrackingLogById(int Id);

	@Query("select c from Claim c where c.id= :Id")
	Claim findClaim(final int Id);

}
