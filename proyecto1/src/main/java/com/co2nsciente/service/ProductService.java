package com.co2nsciente.service;

import com.co2nsciente.exception.ResourceNotFoundException; // Asume que creaste esta clase
import com.co2nsciente.model.Producto;
import com.co2nsciente.repository.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

  private final ProductoRepository productoRepository;
  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

  public ProductService(ProductoRepository repository) {
    this.productoRepository = repository;
  }


  //  Obtener producto ACTIVO por ID (Corrige el error 500/404)
  public Producto obtenerProductoPorId(Long id) {
    // Usa findByIdAndEliminadoFalse()
    return productoRepository.findByIdAndEliminadoFalse(id)
        .orElseThrow(() -> {
          logger.warn("Producto ACTIVO con ID {} no encontrado", id);
          return new ResourceNotFoundException("Producto con ID " + id + " no encontrado o fue eliminado.");
        });
  }

  //  Obtener lista de productos ACTIVO
  public List<Producto> listarProductos(String nombre, Double precio) {
    logger.info("Iniciando búsqueda de productos activos...");
    List<Producto> resultado;

    if (!nombre.isEmpty() && precio > 0) {
      // Usa findByNombreContainingAndPrecioLessThanEqualAndEliminadoFalse
      resultado = this.productoRepository.findByNombreContainingAndPrecioLessThanEqualAndEliminadoFalse(nombre, precio);
    } else if (!nombre.isEmpty()) {
      // Usa findByNombreContainingAndEliminadoFalse
      resultado = this.productoRepository.findByNombreContainingAndEliminadoFalse(nombre);
    } else if (precio > 0) {
      // Usa findByPrecioLessThanEqualAndEliminadoFalse
      resultado = this.productoRepository.findByPrecioLessThanEqualAndEliminadoFalse(precio);
    } else {
      // Usa findByEliminadoFalse para traer todos los activos
      resultado = this.productoRepository.findByEliminadoFalse();
    }

    logger.info("Cantidad de productos encontrados: {}", resultado.size());
    return resultado;
  }

  //  Nuevo: Obtener la lista de productos ELIMINADOS
  public List<Producto> obtenerProductosEliminados() {
    logger.info("Solicitando lista de productos eliminados (eliminado=true).");
    return productoRepository.findByEliminadoTrue();
  }


  // -- Métodos de ESCRITURA (POST, PUT, DELETE) --

  public Producto crearProducto(Producto producto) {
    // ... (Tu código para crear) ...
    return productoRepository.save(producto);
  }

  public Producto editarProducto(Long id, Producto dataProducto) {
    logger.info("Intentando editar producto con ID {}", id);

    // Usamos findByIdAndEliminadoFalse para NO permitir editar productos eliminados
    Producto producto = this.productoRepository.findByIdAndEliminadoFalse(id)
        .orElseThrow(() -> {
          logger.error("Error: producto ACTIVO con ID {} no encontrado", id);
          return new ResourceNotFoundException("Producto no encontrado o fue eliminado");
        });
    if (dataProducto.getNombre() != null) {
      producto.setNombre(dataProducto.getNombre());
    }
    if (dataProducto.getPrecio() != null) {
      producto.setPrecio(dataProducto.getPrecio());
    }
    if (dataProducto.getDescripcion() != null) {
      producto.setDescripcion(dataProducto.getDescripcion());
    }
    if (dataProducto.getCategoria() != null) {
      producto.setCategoria(dataProducto.getCategoria());
    }
    if (dataProducto.getRegion() != null) {
      producto.setRegion(dataProducto.getRegion());
    }


    Producto actualizado = this.productoRepository.save(producto);
    logger.info("Producto ID {} editado correctamente", actualizado.getId());
    return actualizado;
  }


  public Producto borrarProducto(Long id) {
    logger.info("Intentando borrar (soft delete) producto con ID {}", id);

    // Usamos findByIdAndEliminadoFalse para asegurar que solo marcamos productos activos
    Producto producto = this.productoRepository.findByIdAndEliminadoFalse(id)
        .orElseThrow(() -> {
          logger.warn("No se encontró el producto ACTIVO con ID {}", id);
          return new ResourceNotFoundException("Producto no encontrado o ya fue eliminado");
        });

    producto.setEliminado(true);
    Producto actualizado = this.productoRepository.save(producto);
    logger.info("Producto con ID {} marcado como eliminado", actualizado.getId());
    return actualizado;
  }

  //Obtener lista de productos ACTIVO por Región
  public List<Producto> obtenerPorRegion(String region) {
    logger.info("Buscando productos activos en la región: {}", region);
    // Usa el método filtrado del Repository: findByRegionAndEliminadoFalse
    return productoRepository.findByRegionAndEliminadoFalse(region);
  }



}