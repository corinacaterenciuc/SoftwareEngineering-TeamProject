package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.CMSUser;

@Repository
public interface UserRepository extends SystemRepository<CMSUser, String>
{
}
