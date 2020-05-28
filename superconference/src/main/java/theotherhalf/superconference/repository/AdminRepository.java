package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;

@Repository
public interface AdminRepository extends SystemRepository<CMSUser, Long>
{

}
