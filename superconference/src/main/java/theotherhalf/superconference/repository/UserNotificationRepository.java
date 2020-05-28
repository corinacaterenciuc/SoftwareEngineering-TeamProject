package theotherhalf.superconference.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import theotherhalf.superconference.domain.Conference;
import theotherhalf.superconference.domain.CMSUser;
import theotherhalf.superconference.domain.UserClaims;
import theotherhalf.superconference.domain.UserNotification;

import java.util.List;

public interface UserNotificationRepository extends SystemRepository<UserNotification, Long>
{
    @Query("SELECT c from UserNotification c WHERE c.user = :id")
    List<UserNotification> findByUser(@Param("id") CMSUser user);
}
