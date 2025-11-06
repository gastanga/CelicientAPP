package com.celicienta.celicienta.Service;
import com.celicienta.celicienta.Entity.Producto;
import com.celicienta.celicienta.Entity.Usuario;
import com.celicienta.celicienta.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepo productoRepo;

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private VendedorProfileRepo vendedorProfileRepo;

    public Producto publicarProducto (Long usuarioId, Producto producto){
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean esVendedor = vendedorProfileRepo.findByUsuarioId(usuarioId).isPresent();

        if (!esVendedor) {
            throw new RuntimeException("El usuario no tiene perfil de vendedor");
        }

        producto.setVendedor(usuario);
        producto.setActivo(true);
        return productoRepo.save(producto);
    }

    public List<Producto> listarTodos() {
        return productoRepo.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminarProducto(Long id) {
        if (!productoRepo.existsById(id)) {
            throw new RuntimeException("Producto no existe");
        }
        productoRepo.deleteById(id);
    }

    public Producto actualizarProducto(Long productoId, Producto productoActualizado) {
        Producto productoExistente = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());
        productoExistente.setActivo(productoActualizado.isActivo());
        productoExistente.setVendedor(productoExistente.getVendedor());
        // productoExistente.setReceta(productoActualizado.getReceta());
        return productoRepo.save(productoExistente);
    }

    }
