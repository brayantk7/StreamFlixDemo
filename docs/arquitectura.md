# StreamFlix AI System Architecture

## 1. Proposed Solution Architecture (Diagram)

The following diagram illustrates the data flow from the user input to the generation of the final recommendation, integrating both requested AI models:

```mermaid
graph TD
    A[User Data] --> B(Viewing History)
    A --> C(Preferences / Genres)
    
    B --> D{Engine: Collaborative Filtering}
    C --> E{Engine: Decision Tree}
    
    D -->|User Similarity| F[Movie Candidates]
    E -->|Rule Recursion| F
    
    F --> G[Ranking and Cross-Validation]
    G --> H((Personalized Recommendation))
    
    style A fill:#f9f,stroke:#333,stroke-width:2px
    style H fill:#bbf,stroke:#333,stroke-width:4px