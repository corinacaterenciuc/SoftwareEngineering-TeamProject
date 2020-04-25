package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.UserClaims;

@Repository
public interface RoleRepository extends SystemRepository<UserClaims, Long>
{

}
