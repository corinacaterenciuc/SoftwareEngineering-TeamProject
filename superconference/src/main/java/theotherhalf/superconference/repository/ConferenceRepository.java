package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.Conference;

@Repository
public interface ConferenceRepository extends SystemRepository<Conference, Long>
{
}
