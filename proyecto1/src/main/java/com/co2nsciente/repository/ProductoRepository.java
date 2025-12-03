package com.co2nsciente.repository;

import com.co2nsciente.model.Producto;
import java.util.List;
import java.util.Optional; // Importación necesaria para findById
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

  //  Obtener un producto ACTIVO por ID
  Optional<Producto> findByIdAndEliminadoFalse(Long id);

  //  Obtener lista de productos ACTIVO
  List<Producto> findByEliminadoFalse();

  //  Búsqueda de ACTIVO por Región
  List<Producto> findByRegionAndEliminadoFalse(String region);

  //  Búsqueda de ACTIVO por Nombre (Search/Containing)
  // Elige uno de los dos si ambos hacen lo mismo, aquí dejo los dos con el filtro:
  List<Producto> searchByNombreAndEliminadoFalse(String nombre);
  List<Producto> findByNombreContainingAndEliminadoFalse(String nombre);

  //  Búsqueda de ACTIVO por Precio
  List<Producto> findByPrecioLessThanEqualAndEliminadoFalse(Double precio);

  //  Búsqueda combinada de ACTIVO
  List<Producto> findByNombreContainingAndPrecioLessThanEqualAndEliminadoFalse(String nombre, Double precio);

  // Nuevo: Obtener lista de productos ELIMINADOS
  List<Producto> findByEliminadoTrue();
}