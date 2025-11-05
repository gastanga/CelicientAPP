package com.celicienta.celicienta.Entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "administradores")
public class Admin extends Usuario {
    private String tipoAdmin;
}
