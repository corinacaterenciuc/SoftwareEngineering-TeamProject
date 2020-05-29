package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.CMSUser;

import java.util.Optional;

@Repository
public interface UserRepository extends SystemRepository<CMSUser, String>
{
    Optional<CMSUser> findByEmail(String username);
}
