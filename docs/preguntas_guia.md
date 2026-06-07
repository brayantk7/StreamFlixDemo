# Resolución de Preguntas Guías - StreamFlix AI

**1. [cite_start]¿Cómo puede el uso de IA mejorar la personalización de las recomendaciones para cada usuario de la plataforma?** [cite: 74]
La IA transita de un modelo estático (basado en popularidad general) a uno dinámico predictivo. Utilizando algoritmos de Machine Learning, el sistema procesa el historial de visualización, metadatos y comportamientos implícitos para identificar correlaciones no evidentes. Esto permite generar un vector de preferencias único por usuario, entregando recomendaciones que se ajustan probabilísticamente a sus gustos individuales en lugar de tendencias globales.

**2. [cite_start]¿Qué diferencias existen entre el filtrado colaborativo y el análisis de contenido en términos de efectividad para este tipo de plataforma?** [cite: 75]
* **Filtrado Colaborativo:** Basa sus predicciones en la similitud entre usuarios (User-to-User) o entre ítems consumidos por grupos (Item-to-Item). Es altamente efectivo para descubrir nuevos intereses (Serendipity), pero sufre del problema de "Cold Start" (arranque en frío) con usuarios o películas nuevas.
* **Análisis de Contenido:** Recomienda basándose estrictamente en los atributos de las películas (género, director, actores) que el usuario ya ha calificado positivamente. Es efectivo para usuarios con gustos de nicho, pero tiende a sobre-especializar las recomendaciones (Filter Bubble).

**3. [cite_start]¿Cómo puede implementarse un algoritmo de árbol de decisiones para identificar qué características son más relevantes para las recomendaciones?** [cite: 76]
Se implementa estructurando los nodos internos como atributos del usuario (ej. `edad`, `generoFavorito`). La relevancia de cada característica se determina durante el entrenamiento (fase de inducción) calculando la ganancia de información (Information Gain) o el índice Gini. El atributo que mejor divide y clasifica el conjunto de datos se posiciona como el nodo raíz, optimizando la ruta hacia las hojas (recomendaciones finales).

**4. [cite_start]¿Cómo se puede utilizar la recursividad en la programación para mejorar el sistema de recomendaciones y optimizar el rendimiento del sistema?** [cite: 77]
La recursividad permite recorrer y evaluar estructuras de datos no lineales, como los árboles de decisión, sin necesidad de anidar múltiples bloques `if/else` condicionales. El método se llama a sí mismo evaluando el nodo actual y avanzando al nodo hijo correspondiente hasta alcanzar un caso base (el nodo hoja con la recomendación). Esto hace que el código sea escalable, mantenible y capaz de procesar árboles de profundidad variable (N-niveles) con una complejidad de tiempo óptima $O(h)$, donde $h$ es la altura del árbol.

**5. [cite_start]¿Qué métricas se deben considerar para evaluar el éxito del sistema de recomendación y cómo se pueden comparar con los resultados actuales?** [cite: 78]
* **Métricas Implícitas:** Tasa de finalización (Completion Rate) y Tiempo de visualización (Watch Time).
* **Métricas Explícitas:** Calificaciones directas post-visualización (Feedback binario o en escala de 1 a 5).
* **Comparación (A/B Testing):** Se divide a la base de usuarios en dos grupos. El Grupo A utiliza el sistema actual (rankings generales) [cite: 20] y el Grupo B el nuevo modelo de IA. Se compara la retención de usuarios, la interacción con las recomendaciones (Click-Through Rate) y la reducción en la tasa de abandono (Churn Rate) en un periodo establecido.