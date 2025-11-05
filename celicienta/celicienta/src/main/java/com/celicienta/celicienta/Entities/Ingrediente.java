package com.celicienta.celicienta.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table (name = "ingredientes")
public class Ingrediente {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String unidadMedida;
    private double costoUnitario;

}
