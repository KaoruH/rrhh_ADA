package ar.com.ada.api.empleados.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.models.requests.InfoBasicaEmpleadoRequest;
import ar.com.ada.api.empleados.models.requests.SueldoInfoRequest;
import ar.com.ada.api.empleados.models.responses.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;
import ar.com.ada.api.empleados.services.EmpleadoService;

@RestController
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    @Autowired
    CategoriaService categoriaService;

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoBasicaEmpleadoRequest info) {

        Empleado empleado = new Empleado();
        empleado.setNombre(info.nombre);
        empleado.setEdad(info.edad);
        empleado.setSueldo(info.sueldo);
        empleado.setCategoria(categoriaService.buscarCategoriaPorId(info.categoriaId));
        empleado.setFechaAlta(LocalDate.now());
        empleado.setEstadoId(1);
        empleadoService.crearEmpleado(empleado);

        GenericResponse resp = new GenericResponse();
        resp.isOk = true;
        resp.id = empleado.getEmpleadoId();
        resp.message = "Empleado generado con exito";

        return ResponseEntity.ok(resp);

    }

    @GetMapping("/empleados")
    public ResponseEntity<?> listarEmpleado() {

        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<?> buscarEmpleadoPorId(@PathVariable Integer id) {

        Empleado empleado = empleadoService.mostrarEmpleadoPorId(id);

        if (empleado != null) {
            return ResponseEntity.ok(empleado);
        }
        // return ResponseEntity.notFound().build();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<?> actualizarSueldoEmpleado(@PathVariable Integer id, @RequestBody SueldoInfoRequest infoNueva) {

        Empleado empleadoOriginal = empleadoService.mostrarEmpleadoPorId(id);

        if (empleadoOriginal != null) {

            empleadoService.actualizarSueldoEmpleado(empleadoOriginal, infoNueva.sueldoNuevo);

            GenericResponse resp = new GenericResponse();
            
            resp.isOk = true;
            resp.id = empleadoOriginal.getEmpleadoId();
            resp.message = "Se ha actualizado con exito";

            return ResponseEntity.ok(resp);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<?> borrarEmpleado(@PathVariable Integer id) {

        Empleado empleado = empleadoService.mostrarEmpleadoPorId(id);

        if (empleado != null) {

            empleadoService.borrarEmpleado(empleado);

            GenericResponse resp = new GenericResponse();

            resp.isOk = true;
            resp.id = empleado.getEmpleadoId();
            resp.message = "Fue eliminado con exito";

            return ResponseEntity.ok(resp);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}