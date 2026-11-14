import java.util.ArrayList;
import java.util.List;

/**
 * APE 4 - Fase 1: Reto de Grafos y Vertices (Grupo 4)
 * Estudiante: Kleber Andres Moyota Chavez
 * 
 * Representacion y verificacion de contratos conceptuales de la Red Social
 * original de 6 usuarios (U01-U06) y 5 amistades (e1-e5).
 */
public class RetoRedSocialGrupo4 {

    // TDA Conceptual: Usuario (Vertice)
    static class Usuario {
        final String id;
        final String nombre;

        Usuario(String id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return id + " (" + nombre + ")";
        }
    }

    // TDA Conceptual: Amistad (Arista no dirigida)
    static class Amistad {
        final String codigo;
        final String u;
        final String v;

        Amistad(String codigo, String u, String v) {
            this.codigo = codigo;
            this.u = u;
            this.v = v;
        }

        boolean conecta(String a, String b) {
            return (u.equals(a) && v.equals(b)) || (u.equals(b) && v.equals(a));
        }

        boolean incideEn(String id) {
            return u.equals(id) || v.equals(id);
        }

        String obtenerVecino(String id) {
            if (u.equals(id)) return v;
            if (v.equals(id)) return u;
            return null;
        }
    }

    private final List<Usuario> vertices = new ArrayList<>();
    private final List<Amistad> aristas = new ArrayList<>();

    // Regla R6 y R7: ID unico y nombre no vacio
    public String registrarUsuario(String id, String nombre) {
        if (id == null || id.trim().isEmpty() || nombre == null || nombre.trim().isEmpty()) {
            return "RECHAZADO (R7): El ID y el nombre no pueden estar vacios.";
        }
        if (buscarUsuario(id) != null) {
            return "RECHAZADO (R6): El identificador '" + id + "' ya existe.";
        }
        vertices.add(new Usuario(id, nombre));
        return "ACEPTADO: Usuario " + id + " (" + nombre + ") registrado.";
    }

    // Reglas R1, R2, R3 y R4: Extremos validos, sin lazos, reciproco y sin duplicados
    public String proponerAmistad(String codigo, String idA, String idB) {
        if (buscarUsuario(idA) == null || buscarUsuario(idB) == null) {
            return "RECHAZADO (R1 - Usuario inexistente): Uno o ambos IDs (" + idA + ", " + idB + ") no pertenecen a V.";
        }
        if (idA.equals(idB)) {
            return "RECHAZADO (R2 - Lazo prohibido): El usuario " + idA + " no puede ser amigo de si mismo.";
        }
        if (existeRelacionInterna(idA, idB)) {
            return "RECHAZADO (R4 - Arista duplicada): La amistad entre " + idA + " y " + idB + " ya existe.";
        }
        aristas.add(new Amistad(codigo, idA, idB));
        return "ACEPTADO (" + codigo + "): Amistad mutua registrada entre " + idA + " y " + idB + ".";
    }

    private Usuario buscarUsuario(String id) {
        for (Usuario u : vertices) {
            if (u.id.equals(id)) return u;
        }
        return null;
    }

    private boolean existeRelacionInterna(String idA, String idB) {
        for (Amistad e : aristas) {
            if (e.conecta(idA, idB)) return true;
        }
        return false;
    }

    // Contrato: existeRelacion(a, b)
    public String existeRelacion(String idA, String idB) {
        if (buscarUsuario(idA) == null || buscarUsuario(idB) == null) {
            return "ERROR DE VALIDACION (R1): Identificador inexistente en existeRelacion(" + idA + ", " + idB + ")";
        }
        return String.valueOf(existeRelacionInterna(idA, idB));
    }

    // Contrato: vecinos(id)
    public String vecinos(String id) {
        if (buscarUsuario(id) == null) {
            return "ERROR DE VALIDACION (R1): Identificador '" + id + "' inexistente.";
        }
        List<String> listaVecinos = new ArrayList<>();
        for (Amistad e : aristas) {
            String vecino = e.obtenerVecino(id);
            if (vecino != null) {
                Usuario uVecino = buscarUsuario(vecino);
                listaVecinos.add(uVecino.id + " (" + uVecino.nombre + ")");
            }
        }
        return listaVecinos.isEmpty() ? "Conjunto vacio (Usuario aislado)" : listaVecinos.toString();
    }

    // Contrato: grado(id)
    public int grado(String id) {
        if (buscarUsuario(id) == null) {
            throw new IllegalArgumentException("Identificador inexistente: " + id);
        }
        int contador = 0;
        for (Amistad e : aristas) {
            if (e.incideEn(id)) contador++;
        }
        return contador;
    }

    public static void main(String[] args) {
        RetoRedSocialGrupo4 red = new RetoRedSocialGrupo4();

        System.out.println("=== 1. REGISTRO DE LOS 6 USUARIOS (CONJUNTO V) ===");
        System.out.println(red.registrarUsuario("U01", "Mateo"));
        System.out.println(red.registrarUsuario("U02", "Sofia"));
        System.out.println(red.registrarUsuario("U03", "Lucas"));
        System.out.println(red.registrarUsuario("U04", "Valeria"));
        System.out.println(red.registrarUsuario("U05", "Sebastian"));
        System.out.println(red.registrarUsuario("U06", "Camila")); // Usuario aislado

        System.out.println("\n=== 2. REGISTRO DE LAS 5 AMISTADES (CONJUNTO E) ===");
        System.out.println(red.proponerAmistad("e1", "U01", "U02")); // Mateo - Sofia
        System.out.println(red.proponerAmistad("e2", "U01", "U03")); // Mateo - Lucas
        System.out.println(red.proponerAmistad("e3", "U02", "U03")); // Sofia - Lucas (Cierra ciclo U01-U02-U03)
        System.out.println(red.proponerAmistad("e4", "U01", "U04")); // Mateo - Valeria (Mateo alcanza grado 3)
        System.out.println(red.proponerAmistad("e5", "U04", "U05")); // Valeria - Sebastian

        System.out.println("\n=== 3. VERIFICACION DE GRADOS Y LEMA DEL APRETON DE MANOS ===");
        int sumaGrados = 0;
        for (Usuario u : red.vertices) {
            int g = red.grado(u.id);
            sumaGrados += g;
            System.out.println("Usuario " + u + " -> Grado: " + g + " | Vecinos: " + red.vecinos(u.id));
        }
        System.out.println("Suma total de grados = " + sumaGrados + " | 2 * numero de aristas (2 * 5) = " + (2 * red.aristas.size()));

        System.out.println("\n=== 4. CONSULTAS DE LA PREGUNTA GUIA 5 ===");
        System.out.println("existeRelacion(U01, U04) [Amigos]    -> " + red.existeRelacion("U01", "U04"));
        System.out.println("existeRelacion(U02, U05) [No amigos] -> " + red.existeRelacion("U02", "U05"));
        System.out.println("vecinos(U01)                         -> " + red.vecinos("U01"));
        System.out.println("grado(U01)                           -> " + red.grado("U01"));

        System.out.println("\n=== 5. PRUEBAS DE RECHAZO DE LA PREGUNTA GUIA 6 ===");
        System.out.println(red.proponerAmistad("e_err1", "U01", "U01")); // Caso Lazo
        System.out.println(red.proponerAmistad("e_err2", "U03", "U01")); // Caso Duplicado inverso
        System.out.println(red.proponerAmistad("e_err3", "U06", "U99")); // Caso Usuario inexistente
    }
}