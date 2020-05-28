package theotherhalf.superconference.repository;

import org.springframework.stereotype.Repository;
import theotherhalf.superconference.domain.DBFile;

import java.util.Optional;

@Repository
public interface FileRepository extends SystemRepository<DBFile, String>
{
}
