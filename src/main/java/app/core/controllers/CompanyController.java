package app.core.controllers;
import app.core.entities.Project;

import app.core.exeptions.ServiceException;
import app.core.jwt.User;
import app.core.repositories.CompanyRepository;
import app.core.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;


    @PostMapping(value = "/addProject", headers = {HttpHeaders.AUTHORIZATION})
    public Project addProject(@RequestBody Project project, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        try {
            return companyService.addProject(project, companyRepository.findByUserCredentials_UserName(user.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the company not found")).getId());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/getProject", headers = {HttpHeaders.AUTHORIZATION})
    public Project getProject(int projectId, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        try {
            return companyService.getProject(projectId, companyRepository.findByUserCredentials_UserName(user.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the company not found")).getId());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/getProjectByName", headers = {HttpHeaders.AUTHORIZATION})
    public Project getProjectByName(String projectName, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        try {
            return companyService.getProjectByName(projectName, companyRepository.findByUserCredentials_UserName(user.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the company not found")).getId());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping(value = "/updateProject", headers = {HttpHeaders.AUTHORIZATION})
    public Project updateProject(@RequestBody Project project, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        try {
            return companyService.updateProject(project, companyRepository.findByUserCredentials_UserName(user.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the company not found")).getId());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteProject", headers = {HttpHeaders.AUTHORIZATION})
    public void deleteProject(@RequestBody int projectId, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");
        try {
            companyService.deleteProject(projectId, companyRepository.findByUserCredentials_UserName(user.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "the company not found")).getId());
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}



