package com.co2nsciente.controller;

import com.co2nsciente.model.Producto;
import com.co2nsciente.service.ProductService;
import com.co2nsciente.repository.ProductoRepository;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;
import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService service;
  private final ProductoRepository productoRepository;

  public ProductController(ProductService service, ProductoRepository productoRepository) {
    this.service = service;
    this.productoRepository = productoRepository;
  }

  // --- POST: crear producto ---
  @PostMapping
  public Producto crearProducto(@Valid @RequestBody Producto producto) {
    return this.service.crearProducto(producto);
  }

  // --- GET: filtrar por nombre y precio ---
  @GetMapping
  public List<Producto> listarProductos(
      @RequestParam(required = false, defaultValue = "") String nombre,
      @RequestParam(required = false, defaultValue = "0") Double precio) {
    return this.service.listarProductos(nombre, precio);
  }

  // --- PUT: editar producto ---
  @PutMapping("/{id}")
  public Producto editarProducto(@PathVariable Long id, @Valid @RequestBody Producto producto) {
    return this.service.editarProducto(id, producto);
  }

  // --- DELETE: borrar producto ---
  @DeleteMapping("/{id}")
  public Producto borrarProducto(@PathVariable Long id) {
    return this.service.borrarProducto(id);
  }

  // --- GET: buscar por región ---
  @GetMapping("/region/{region}")
  public List<Producto> getByRegion(@PathVariable String region) {
    return service.obtenerPorRegion(region);
  }

  @GetMapping("/{id}")
  public Producto obtenerProductoPorId(@PathVariable Long id) {
    return service.obtenerProductoPorId(id);
  }

  @GetMapping("/eliminados")
  public ResponseEntity<List<Producto>> listarEliminados() {
    List<Producto> eliminados = service.obtenerProductosEliminados();

    if (eliminados.isEmpty()) {
      return ResponseEntity.noContent().build(); // 204 No Content
    }

    return ResponseEntity.ok(eliminados); // 200 OK con la lista
  }

  // Este método atrapa los errores de @Valid y los ordena
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

}
