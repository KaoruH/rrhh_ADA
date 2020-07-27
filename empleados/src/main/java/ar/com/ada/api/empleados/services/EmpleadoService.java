package ar.com.ada.api.empleados.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repositories.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepo;

    public void crearEmpleado(Empleado empleado){

        empleadoRepo.save(empleado);

    }

    public List<Empleado> listarEmpleados(){

        return empleadoRepo.findAll();

    }

    public Empleado mostrarEmpleadoPorId(Integer empleadoId){

        Optional<Empleado> optionalEmpleado = empleadoRepo.findById(empleadoId);

        if (optionalEmpleado.isPresent()){
            
            return optionalEmpleado.get();
            
        } 
        
        return null;

    }

    public void actualizarSueldoEmpleado(Empleado empleadoOriginal, BigDecimal sueldo){

        empleadoOriginal.setSueldo(sueldo);

        empleadoRepo.save(empleadoOriginal);

    }

    public void actualizarEstado(Empleado empleado,int estadoId){

        empleado.setEstadoId(estadoId);

        empleadoRepo.save(empleado);
    }

    public void borrarEmpleado(Empleado empleado){

        this.actualizarEstado(empleado, 0);

    }

    
}