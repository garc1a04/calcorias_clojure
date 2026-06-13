# calcorias_clojure — API REST

API REST desenvolvida em **Clojure** para gerenciamento de calorias, permitindo o registro de usuários, alimentos consumidos e exercícios realizados, além do cálculo do balanço energético diário.

---

## Sobre o Projeto

Este projeto é a camada de backend (API) do sistema de contagem de calorias. Ele expõe endpoints HTTP que recebem requisições da CLI (`cli_calcorias_clojure`) e integra-se a duas APIs externas para obter dados nutricionais e de gasto calórico:

- **USDA FoodData Central API** — busca o valor calórico dos alimentos por nome.
- **API Ninjas Calories Burned API** — calcula as calorias queimadas em uma atividade física com base no peso do usuário e na duração.

O estado da aplicação é mantido em memória utilizando um `atom` do Clojure, seguindo o paradigma funcional — sem banco de dados externo.

---

## Tecnologias Utilizadas

| Tecnologia | Finalidade |
|---|---|
| Clojure 1.10 | Linguagem principal |
| Compojure | Roteamento HTTP |
| Ring | Servidor web e middlewares |
| ring-json | Parsing de corpo JSON |
| Cheshire | Serialização/deserialização JSON |
| clj-http | Requisições HTTP para APIs externas |

---

## Estrutura do Projeto

```
calcorias_clojure/
├── project.clj
└── src/
    ├── calcorias/
    │   ├── handler.clj          # Definição das rotas (entrada da aplicação)
    │   ├── controller.clj       # Validação e delegação para a camada de serviço
    │   ├── service.clj          # Orquestra os casos de uso
    │   ├── database/
    │   │   └── db.clj           # "Banco de dados" em memória (atom)
    │   └── use_case/
    │       ├── add_user.clj     # Cadastra um usuário
    │       ├── add_food.clj     # Registra alimento consumido
    │       ├── add_exercise.clj # Registra exercício realizado
    │       ├── find_user.clj    # Busca o usuário cadastrado
    │       ├── find_calories.clj# Lista calorias por período
    │       └── get_calories_saldo.clj # Calcula o balanço energético
    ├── food/
    │   └── core.clj             # Integração com a USDA FoodData Central API
    ├── exercise/
    │   └── core.clj             # Integração com a API Ninjas (calorias queimadas)
    └── shared/
        ├── validations.clj      # Validações de entrada (campos obrigatórios e tipos)
        ├── json.clj             # Helpers para respostas JSON
        ├── error/
        │   └── app_error.clj   # Tratamento de erros padronizado
        └── wrapper/
            └── wrap_filter.clj  # Middleware de filtro de requisições
```

---

## Pré-requisitos

- [Java JDK 8+](https://adoptium.net/)
- [Leiningen](https://leiningen.org/) (`lein`)

---

## Como Executar

Clone o repositório e, na pasta `calcorias_clojure`, execute:

```bash
lein ring server
```

O servidor iniciará na porta **3000** por padrão.

---

## Endpoints

### `POST /api/user`

Cadastra um novo usuário. O usuário é armazenado em memória e é necessário para o registro de exercícios.

**Body (JSON):**
```json
{
  "name": "João",
  "weight": "80",
  "age": "25",
  "sex": "M"
}
```

**Campos obrigatórios:** `name`, `weight`, `age`, `sex` (todos strings).

**Resposta de sucesso (201):**
```json
{
  "message": "User successfully created.",
  "data": { "name": "João", "weight": "80", "age": "25", "sex": "M" }
}
```

---

### `POST /api/food`

Registra um alimento consumido. Consulta a API do USDA para obter as informações nutricionais com base no nome informado.

**Body (JSON):**
```json
{
  "name": "apple",
  "grams": 150
}
```

**Campos obrigatórios:** `name` (string), `grams` (inteiro).

**Resposta de sucesso (200):**
```json
{
  "message": "sucess",
  "calories": {
    "type": "intake",
    "name": "APPLE",
    "kcal": 78.0,
    "grams": 150,
    "date": "2025-06-13"
  }
}
```

**Caso haja múltiplos resultados** para o nome buscado, a API retorna a lista para que o usuário escolha o mais específico:
```json
{
  "message": "Many results",
  "foods": [{ "name": "APPLE, RAW" }, { "name": "APPLE JUICE" }]
}
```

---

### `POST /api/user/burned`

Registra um exercício realizado pelo usuário. Requer que um usuário já tenha sido cadastrado (`POST /api/user`). Consulta a API Ninjas para calcular as calorias queimadas.

**Body (JSON):**
```json
{
  "name": "running",
  "minutes": 30
}
```

**Campos obrigatórios:** `name` (string), `minutes` (inteiro).

**Resposta de sucesso (201):**
```json
{
  "message": "sucess",
  "exercise": {
    "type": "burned",
    "name": "Running",
    "minutes": 30,
    "kcal": 320.5,
    "date": "2025-06-13"
  }
}
```

---

### `GET /api/calories`

Retorna a lista de todos os registros (alimentos e exercícios) armazenados.

**Query params (opcionais):**
- `period=day` — filtra pelo dia atual
- `period=month` — filtra pelo mês atual (padrão)

**Resposta (200):**
```json
{
  "data": [
    { "type": "intake", "name": "APPLE", "kcal": 78.0, "grams": 150, "date": "2025-06-13" },
    { "type": "burned", "name": "Running", "kcal": 320.5, "minutes": 30, "date": "2025-06-13" }
  ]
}
```

---

### `GET /api/calories/saldo`

Calcula o **balanço energético**: soma as calorias consumidas (intake) e subtrai as calorias queimadas (burned).

**Resposta (200):**
```json
{
  "data": {
    "energy_balance": -242.5
  }
}
```

Um valor negativo indica déficit calórico (mais calorias queimadas do que consumidas).

---

### `GET /api/user`

Retorna os dados do usuário atualmente cadastrado em memória.

**Resposta (200):**
```json
{
  "data": { "name": "João", "weight": "80", "age": "25", "sex": "M" }
}
```

---

## Modelo de Dados (em Memória)

A aplicação utiliza um `atom` Clojure como banco de dados em memória com a seguinte estrutura:

```clojure
{:user {}        ;; Dados do usuário (nome, peso, idade, sexo)
 :calories []}   ;; Lista de registros de alimentos e exercícios
```

> **Atenção:** os dados são perdidos ao reiniciar o servidor, pois não há persistência em disco.

---

## Validações

Todos os endpoints com `POST` passam por validação antes de processar os dados:

- **400 Bad Request** — campos obrigatórios ausentes no body.
- **422 Unprocessable Entity** — tipos de dados incorretos (ex.: `grams` enviado como string em vez de inteiro).
- **404 Not Found** — usuário não encontrado ao tentar registrar exercício.

---

## APIs Externas Utilizadas

| API | Documentação |
|---|---|
| USDA FoodData Central | https://fdc.nal.usda.gov/api-guide |
| API Ninjas Calories Burned | https://api-ninjas.com/api/caloriesburned |