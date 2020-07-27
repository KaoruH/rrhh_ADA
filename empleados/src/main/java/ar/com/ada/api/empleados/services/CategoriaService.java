package ar.com.ada.api.empleados.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepo;

    public void crearCategoria(Categoria categoria) {

        categoriaRepo.save(categoria);

    }

    public List<Categoria> listarCategorias() {

        return categoriaRepo.findAll();
    }

    public List<Empleado> traerEmpleadosPorCategoria(Integer categoriaId) {

        Optional<Categoria> cOptional = categoriaRepo.findById(categoriaId);
        List<Empleado> listaVacia = new ArrayList<>();

        if (cOptional.isPresent()) {

            return (cOptional.get()).getEmpleados();
        }
        return listaVacia;

    }

    public Categoria buscarCategoriaPorId(Integer categoriaId) {

        Optional<Categoria> optionalCategoria = categoriaRepo.findById(categoriaId);

        if (optionalCategoria.isPresent()) {

            return optionalCategoria.get();
        }
        return null;

    }

}