package app.core.repositories;

import app.core.entities.Employee;
import app.core.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByCompanyId(int companyId);

    Optional<Employee> findByName(String name);

    Optional<Employee> findByUserCredentials_UserNameAndUserCredentials_Password(String userName, String password);

    List<Employee> findByRoleAndCompanyId(Role role, int companyId);

    Optional<Employee> findByTZ(String TZ);

    Optional<Employee> findByPhone(String phone);

    List<Employee> findByAddress_City(String city);

    boolean existsByEmail(String email);
}