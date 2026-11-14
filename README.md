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
## 📊 Representación Visual del Grafo

```mermaid
%%{init: {'theme': 'base', 'themeVariables': { 'edgeLabelBackground': '#F8FAFC', 'lineColor': '#334155', 'fontFamily': 'Segoe UI, sans-serif'}}}%%
flowchart LR
    %% Columna 1: Sofía y Lucas (Vertical)
    subgraph Col1[" "]
        direction TB
        U02(("<b>Sofía</b><br/>(U02)"))
        G02(["Grado: 2"])
        U03(("<b>Lucas</b><br/>(U03)"))
        G03(["Grado: 2"])
        
        U02 ~~~ G02
        U02 ---|"<b>e3</b>"| U03
        U03 ~~~ G03
    end

    %% Etiqueta del Ciclo C3 en medio del triángulo
    C3(["Ciclo C3<br/>(U01-U02-U03-U01)"])

    %% Columna 2: Mateo (Centro)
    subgraph Col2[" "]
        direction TB
        U01(("<b>Mateo</b><br/>(U01)"))
        G01(["Grado: 3 (Máx ≥ 3)"])
        U01 ~~~ G01
    end

    %% Columna 3: Valeria (Centro-Derecha)
    subgraph Col3[" "]
        direction TB
        U04(("<b>Valeria</b><br/>(U04)"))
        G04(["Grado: 2"])
        U04 ~~~ G04
    end

    %% Columna 4: Sebastián (Arriba) y Camila (Abajo Aislada)
    subgraph Col4[" "]
        direction TB
        U05(("<b>Sebastián</b><br/>(U05)"))
        G05(["Grado: 1"])
        U06(("<b>Camila</b><br/>(U06)"))
        G06(["Grado: 0 (Aislado)"])
        
        U05 ~~~ G05
        G05 ~~~ U06
        U06 ~~~ G06
    end

    %% Aristas principales
    U02 ---|"<b>e1</b>"| U01
    U03 ---|"<b>e2</b>"| U01
    U02 ~~~ C3
    U03 ~~~ C3
    C3 ~~~ U01
    U01 ---|"<b>e4</b>"| U04
    U04 ---|"<b>e5</b>"| U05
    U04 ~~~ U06

    %% Ocultar bordes de las columnas guía
    style Col1 fill:none,stroke:none
    style Col2 fill:none,stroke:none
    style Col3 fill:none,stroke:none
    style Col4 fill:none,stroke:none

    %% Estilos de los 6 Vértices (Círculos idénticos a la imagen)
    style U01 fill:#1E3A8A,stroke:#3B82F6,stroke-width:4px,color:#FFFFFF
    style U02 fill:#0F766E,stroke:#14B8A6,stroke-width:4px,color:#FFFFFF
    style U03 fill:#0F766E,stroke:#14B8A6,stroke-width:4px,color:#FFFFFF
    style U04 fill:#0F766E,stroke:#14B8A6,stroke-width:4px,color:#FFFFFF
    style U05 fill:#334155,stroke:#64748B,stroke-width:4px,color:#FFFFFF
    style U06 fill:#991B1B,stroke:#EF4444,stroke-width:4px,color:#FFFFFF

    %% Estilos de las placas de Grado y del Ciclo C3
    style G01 fill:#DBEAFE,stroke:none,color:#1E40AF,font-size:11px
    style G02 fill:#F1F5F9,stroke:none,color:#334155,font-size:11px
    style G03 fill:#F1F5F9,stroke:none,color:#334155,font-size:11px
    style G04 fill:#F1F5F9,stroke:none,color:#334155,font-size:11px
    style G05 fill:#F1F5F9,stroke:none,color:#334155,font-size:11px
    style G06 fill:#FEE2E2,stroke:none,color:#991B1B,font-size:11px
    style C3 fill:#EFF6FF,stroke:#93C5FD,stroke-width:2px,stroke-dasharray: 4 4,color:#1D4ED8,font-size:11px
```

    style U06 fill:#991B1B,stroke:#EF4444,stroke-width:3px,color:#FFFFFF
