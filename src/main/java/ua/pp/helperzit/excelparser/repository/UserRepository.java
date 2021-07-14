package ua.pp.helperzit.excelparser.repository;

import org.springframework.data.repository.CrudRepository;
import ua.pp.helperzit.excelparser.service.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

}
