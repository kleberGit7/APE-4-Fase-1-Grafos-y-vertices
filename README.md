|# APE 4 · Fase 1: Grafos y Vértices — Red Social Original

**Universidad Técnica de Ambato**  
**Facultad de Ingeniería en Sistemas, Electrónica e Industrial**  
**Carrera de Software · Estructura de Datos (Tercero "B")**  

---

##  Datos Informativos
* **Estudiante:** Kleber Andrés Moyota Chávez
* **Grupo:** Grupo 4
* **Docente:** Ing. José Caiza, Mg.
* **Tema:** Requisitos, modelado conceptual y verificación de invariantes de una red social mediante grafos.

---

##  Descripción del Proyecto
Este repositorio contiene el diseño conceptual y la verificación de contratos para el reto de la **Fase 1 de la APE 4**. Se modela una red social original mediante un **grafo simple, no dirigido y no ponderado** $G = (V, E)$ compuesto exactamente por **6 usuarios (vértices)** y **5 relaciones de amistad (aristas)**, verificando el cumplimiento de invariantes topológicas y reglas de negocio.

---

##  Modelo Matemático del Grafo $G = (V, E)$

### 1. Conjunto de Vértices ($V$)
$V = \{U01, U02, U03, U04, U05, U06\}$

| ID | Usuario | Grado $g(v)$ | Vecinos Adyacentes | Rol Topológico |
| :---: | :--- | :---: | :--- | :--- |
| **U01** | Mateo | **3** | `{U02, U03, U04}` | Vértice de mayor grado ($\ge 3$) |
| **U02** | Sofía | **2** | `{U01, U03}` | Parte del ciclo $C_3$ |
| **U03** | Lucas | **2** | `{U01, U02}` | Parte del ciclo $C_3$ |
| **U04** | Valeria | **2** | `{U01, U05}` | Nodo puente hacia `U05` |
| **U05** | Sebastián | **1** | `{U04}` | Vértice hoja (extremo) |
| **U06** | Camila | **0** | `∅` (Vacío) | Usuario aislado (Grado $0$) |

### 2. Conjunto de Aristas ($E$)
$E = \{e_1, e_2, e_3, e_4, e_5\}$

* **$e_1 = \{U01, U02\}$:** Amistad recíproca entre Mateo y Sofía.
* **$e_2 = \{U01, U03\}$:** Amistad recíproca entre Mateo y Lucas.
* **$e_3 = \{U02, U03\}$:** Amistad recíproca entre Sofía y Lucas *(cierra el ciclo $U01 - U02 - U03 - U01$)*.
* **$e_4 = \{U01, U04\}$:** Amistad recíproca entre Mateo y Valeria.
* **$e_5 = \{U04, U05\}$:** Amistad recíproca entre Valeria y Sebastián.

> **Teorema del Apretón de Manos:**  
> $$\sum_{v \in V} g(v) = 3 + 2 + 2 + 2 + 1 + 0 = 10 = 2|E| \quad (2 \times 5 = 10)$$

---

## 📊 Representación Visual del Grafo
graph RedSocialGrupo4 {
    layout=neato;
    bgcolor="#FFFFFF";
    pad="0.4";
    
    node [fontname="Helvetica, Arial, sans-serif"];
    edge [color="#334155", penwidth=3.2];

    // Título superior
    titulo [shape=plaintext, label=<
        <B><FONT POINT-SIZE="17" COLOR="#0F172A">Red Social Original G = (V, E) · Grupo 4 (6 Vértices, 5 Aristas)</FONT></B>
    >, pos="5.0,6.3!"];

    // 6 Nodos principales (Círculos)
    node [shape=circle, style="filled", fontcolor="white", penwidth=3.5, fixedsize=true, width=1.35];
    
    U02 [label=<<B><FONT POINT-SIZE="12">Sofía</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U02)</FONT>>, pos="1.5,5.1!", fillcolor="#0F766E", color="#14B8A6"];
    U03 [label=<<B><FONT POINT-SIZE="12">Lucas</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U03)</FONT>>, pos="1.5,2.1!", fillcolor="#0F766E", color="#14B8A6"];
    U01 [label=<<B><FONT POINT-SIZE="12">Mateo</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U01)</FONT>>, pos="4.0,3.6!", fillcolor="#1E3A8A", color="#3B82F6"];
    U04 [label=<<B><FONT POINT-SIZE="12">Valeria</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U04)</FONT>>, pos="6.5,3.6!", fillcolor="#0F766E", color="#14B8A6"];
    U05 [label=<<B><FONT POINT-SIZE="12">Sebastián</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U05)</FONT>>, pos="8.6,5.0!", fillcolor="#334155", color="#64748B"];
    U06 [label=<<B><FONT POINT-SIZE="12">Camila</FONT></B><BR/><FONT POINT-SIZE="10" COLOR="#E2E8F0">(U06)</FONT>>, pos="8.6,1.9!", fillcolor="#991B1B", color="#EF4444"];

    // Placas de Grado debajo de cada nodo
    node [shape=box, style="rounded,filled", fixedsize=false, width=0, height=0, margin="0.08,0.04", penwidth=0];
    
    deg_U02 [label=<<B><FONT POINT-SIZE="9" COLOR="#334155">Grado: 2</FONT></B>>, fillcolor="#F1F5F9", pos="1.5,4.15!"];
    deg_U03 [label=<<B><FONT POINT-SIZE="9" COLOR="#334155">Grado: 2</FONT></B>>, fillcolor="#F1F5F9", pos="1.5,1.15!"];
    deg_U01 [label=<<B><FONT POINT-SIZE="9" COLOR="#1E40AF">Grado: 3 (Máx ≥ 3)</FONT></B>>, fillcolor="#DBEAFE", pos="4.0,2.65!"];
    deg_U04 [label=<<B><FONT POINT-SIZE="9" COLOR="#334155">Grado: 2</FONT></B>>, fillcolor="#F1F5F9", pos="6.5,2.65!"];
    deg_U05 [label=<<B><FONT POINT-SIZE="9" COLOR="#334155">Grado: 1</FONT></B>>, fillcolor="#F1F5F9", pos="8.6,4.05!"];
    deg_U06 [label=<<B><FONT POINT-SIZE="9" COLOR="#991B1B">Grado: 0 (Aislado)</FONT></B>>, fillcolor="#FEE2E2", pos="8.6,0.95!"];

    // Etiquetas de las 5 Aristas (e1 - e5) en cajitas con borde
    node [shape=box, style="rounded,filled", fillcolor="#F8FAFC", color="#CBD5E1", penwidth=1.2, margin="0.06,0.03"];
    
    lbl_e1 [label=<<B><FONT POINT-SIZE="10" COLOR="#0F172A">e1</FONT></B>>, pos="2.85,4.65!"];
    lbl_e2 [label=<<B><FONT POINT-SIZE="10" COLOR="#0F172A">e2</FONT></B>>, pos="2.85,2.55!"];
    lbl_e3 [label=<<B><FONT POINT-SIZE="10" COLOR="#0F172A">e3</FONT></B>>, pos="1.10,3.60!"];
    lbl_e4 [label=<<B><FONT POINT-SIZE="10" COLOR="#0F172A">e4</FONT></B>>, pos="5.25,3.92!"];
    lbl_e5 [label=<<B><FONT POINT-SIZE="10" COLOR="#0F172A">e5</FONT></B>>, pos="7.45,4.55!"];

    // Etiqueta central del Ciclo C3
    ciclo [shape=box, style="rounded,filled,dashed", fillcolor="#EFF6FF", color="#93C5FD", penwidth=1.5, margin="0.1,0.06",
           label=<<B><FONT POINT-SIZE="9.5" COLOR="#1D4ED8">Ciclo C3<BR/>(U01-U02-U03-U01)</FONT></B>>, pos="2.45,3.60!"];

    // Pie de página
    pie [shape=plaintext, style="", label=<
        <I><FONT POINT-SIZE="11" COLOR="#475569">Propiedades: Grafo Simple, No Dirigido y No Ponderado | Suma de Grados = 10 (2 × 5 aristas)</FONT></I>
    >, pos="5.0,0.25!"];

    // 5 Aristas conectando los vértices
    U01 -- U02;
    U01 -- U03;
    U02 -- U03;
    U01 -- U04;
    U04 -- U05;
}

    style U06 fill:#991B1B,stroke:#EF4444,stroke-width:3px,color:#FFFFFF
