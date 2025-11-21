package ar.edu.unnoba.poo2025.torneos.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    @OneToMany(mappedBy = "admin")
    private Set <Tournament> tournaments = new HashSet <>();

    public Admin() {}

    public Admin(String email, String password) {
        super(email, password);
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

}
