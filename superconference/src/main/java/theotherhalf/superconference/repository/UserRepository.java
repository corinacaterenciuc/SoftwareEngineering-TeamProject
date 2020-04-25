package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.User;

@Repository
public interface UserRepository extends SystemRepository<User, Long>
{
}
