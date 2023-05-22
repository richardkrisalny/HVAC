package app.core.services;
import app.core.entities.Employee;
import app.core.entities.Project;
import app.core.entities.UserType;
import app.core.exeptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyService extends ServiceGlobal {
    public Project addProject(Project project,int companyId) throws ServiceException {
        if (projectRepository.existsByNameAndCompanyId(project.getName(), companyId))
            throw new ServiceException("the company all ready have project with this name---"+project.getName());
        userCredentialsRepository.save(project.getClient().getUserCredentials());
        addressRepository.save(project.getClient().getAddress());
        addressRepository.save(project.getAddress());
        clientRepository.save(project.getClient());
        companyRepository.findById(companyId).orElseThrow(()->new ServiceException("the company not found")).addProject(project);
        return projectRepository.save(project);
    }
    public Project getProject(int projectId ,int companyId) throws ServiceException {
        return projectRepository.findByIdAndCompanyId(projectId,companyId).orElseThrow(()->new ServiceException("the project not found"));
    }
    public Project getProjectByName(String projectName ,int companyId) throws ServiceException {
        return projectRepository.findByNameAndCompanyId(projectName,companyId).orElseThrow(()->new ServiceException("the project not found"));
    }
    public List<Project> getAllProjects(int companyId)  {
        return projectRepository.findByCompanyId(companyId);
    }
    public Project updateProject(Project project,int projectId ,int companyId) throws ServiceException {
        if (!projectRepository.existsByIdAndCompanyId(projectId,companyId))
            throw new ServiceException("the project "+project.getName()+" not found");
        project.getClient().getUserCredentials().setUserType(UserType.CUSTOMER);
        userCredentialsRepository.save(project.getClient().getUserCredentials());
        addressRepository.save(project.getClient().getAddress());
        addressRepository.save(project.getAddress());
        clientRepository.save(project.getClient());
        project.setId(projectId);
        return projectRepository.save(project);
    }
    public void deleteProject(int projectId,int companyId) throws ServiceException {
        if(!projectRepository.existsByIdAndCompanyId(projectId,companyId))
            throw new ServiceException("the company not found");
        projectRepository.deleteById(projectId);
    }
    public Employee addEmployee(Employee employee,int companyId) throws ServiceException {
        if(employeeRepository.existsByTZAndCompanyId(employee.getTZ(), companyId))
            throw new ServiceException("the employee already exists");
        employee.getUserCredentials().setUserType(UserType.EMPLOYEE);
        userCredentialsRepository.save(employee.getUserCredentials());
        addressRepository.save(employee.getAddress());
        companyRepository.findById(companyId).orElseThrow(()->new ServiceException("the company not found")).addEmployee(employee);
        return employeeRepository.save(employee);
        }
        public Employee getEmployee(int employeeId,int companyId) throws ServiceException {
        return employeeRepository.findByIdAndCompanyId(employeeId,companyId).orElseThrow(()->new ServiceException("the employee not found"));
        }
        public Employee getEmployee(String employeeTZ,int companyId) throws ServiceException {
        return employeeRepository.findByTZAndCompanyId(employeeTZ,companyId).orElseThrow(()->new ServiceException("the employee not found"));
        }
        public List<Employee> getAllEmployees(int companyId)  {
        return employeeRepository.findByCompanyId(companyId);
        }
        public List<Employee> getAllEmployees(int projectId,int companyId)  {
        return employeeRepository.findByProjects_IdAndCompanyId(projectId, companyId);
        }

}
