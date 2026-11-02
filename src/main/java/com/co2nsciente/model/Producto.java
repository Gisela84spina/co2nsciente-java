package com.co2nsciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List; // Este es para que entienda qué es una lista (bolsa de cosas)

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter

public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;//siempre arriba d todo y con estas anotaciones

  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 3, max = 30, message = "El nombre debe tener entre 3 y 30 caracteres")
  @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]*$", message = "El nombre no puede contener caracteres especiales")
  @Pattern(regexp = ".*[a-zA-ZáéíóúÁÉÍÓÚñÑ].*", message = "El nombre debe contener al menos algunas letras")
  private String nombre;

  private String region = "General";

  private boolean eliminado = false;

  @NotBlank(message = "La URL de la imagen es obligatoria")
  @JsonProperty("imagenUrl") // Esto le dice a Java: "Aunque en la DB diga imagen_url, a React mandale imagenUrl"
  @Column(name = "imagen_url")
  private String imagenUrl;

  @NotNull(message = "El precio es obligatorio")
  @Min(value = 1, message = "El precio debe ser mayor a 0")
  private Double precio;

  private String descripcion;

  @ManyToMany
  private List<Categoria> categorias;



  // metodo constructor con parametros
  public Producto(String nombre, Double precio, String descripcion, List<Categoria> categorias, String imagenUrl) {
    this.nombre = nombre;
    this.precio = precio;
    this.descripcion = descripcion;
    this.categorias = categorias;
    this.imagenUrl = imagenUrl;

  }

//constructor vacio obligatorio
  public Producto() {
  }
}