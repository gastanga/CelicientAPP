package com.celicienta.celicienta.Service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CestaService {
    private final Map<Long, List<Long>> cestas = new ConcurrentHashMap<>();


    public List<Long> obtenerCesta(Long idUsuario) {
        return cestas.getOrDefault(idUsuario, new ArrayList<>());
    }

    public void agregarProducto(Long idUsuario, Long idProducto) {
        cestas.computeIfAbsent(idUsuario, k -> new ArrayList<>()).add(idProducto);
    }

    public void eliminarProducto(Long idUsuario, Long idProducto) {
        cestas.computeIfAbsent(idUsuario, k -> new ArrayList<>()).remove(idProducto);
    }

    public void vaciarCesta(Long idUsuario) {
        cestas.remove(idUsuario);
    }

}
