package app.core.entities;

import lombok.*;

import javax.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
@Entity
@Builder
@ToString(exclude = "workers , ducts")

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String name;
    private String address;
    @OneToOne
    private Contact contact;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "workers_projects",joinColumns = @JoinColumn(name = "projectID"),inverseJoinColumns = @JoinColumn(name = "WorkerID"))
    private List<Worker>workers;
    @OneToMany()
    @JoinColumn(name = "projectId")
    private List<Duct>ducts;
    private LocalDate startDate;
    private LocalDate deadLine;
    private int DegreeOfDifficulty;
    private boolean done;

    public void addDuct(Duct duct){
        if(ducts == null){
            ducts=new ArrayList<>();
        }
        duct.setProjectId(this.id);
        ducts.add(duct);
    }
    public void addWorker(Worker worker){
        if(workers == null){
            workers=new ArrayList<>();
        }
        worker.addProject(this);
        workers.add(worker);
    }
}
