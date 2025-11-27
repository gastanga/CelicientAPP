package com.celicienta.celicienta.Service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CestaService {
    private Map<Long, List<Long>> cestaPorUsuario = new HashMap<>();

    public void agregarProducto(Long idUsuario, Long idProducto) {
        cestaPorUsuario.computeIfAbsent(idUsuario, k -> new ArrayList<>()).add(idProducto);
    }

    public List<Long> obtenerCesta(Long idUsuario) {
        return cestaPorUsuario.getOrDefault(idUsuario, new ArrayList<>());
    }

    public void eliminarProducto(Long idUsuario, Long idProducto) {
        cestaPorUsuario.computeIfAbsent(idUsuario, k -> new ArrayList<>()).remove(idProducto);
    }

    public void vaciarCesta(Long idUsuario) {
        cestaPorUsuario.remove(idUsuario);
    }

}
