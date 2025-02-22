# Requisitos - Sistema de inscrições em Eventos

Vamos criar um sistema para inscrever nossos alunos em eventos usando *Spring Boot*

## Requisitos Funcionais

1. **Inscrição**:
    - O usuário pode se inscrever no evento usando nome e e-mail.
2. **Geração de Link de Indicação**:
    - O usuário pode gerar um link de indicação (um por inscrito).
3. **Ranking de Indicações**:
    - O usuário pode ver o ranking de indicações.
4. **Visualização de Indicações**:
    - O usuário pode ver a quantidade de inscritos que ingressaram com seu link.

## User Stories

### US00 - CRUD de Evento

Este User Story é necessário para subsidiar os User Stories e Requisitos Funcionais existentes

Algumas funcionalidades para gerenciarmos eventos

- Criação de um novo evento
- Listagem de todos os eventos disponíveis
- Recuperação dos detalhes de um determinado evento pelo ID
- Recuperação dos detalhes de um determinado evento pelo seu Pretty Name

```bash
Endpoint: POST /events
Descrição: Cria um novo evento
Requisição
{
		"name":"CodeCraft Summit 2025",
		"location":"Online",
		"price":0.0,
		"startDate":"2025-03-16",
		"endDate":"2025-03-18",
		"startTime":"19:00:00"
		"endTime":"21:00:00"
}

Resposta 
{
	  "id": 1,
		"name":"CodeCraft Summit 2025",
		"prettyName":"codecraft-summit-2025",
		"location":"Online",
		"price":0.0,
		"startDate":"2025-03-16",
		"endDate":"2025-03-18",
		"startTime":"19:00:00"
		"endTime":"21:00:00"
}

```

```bash
Endpoint: GET /events
Descrição: Lista todos os eventos

Resposta:
Resposta 

[{
	  "id": 1,
		"name":"CodeCraft Summit 2025",
		"prettyName":"codecraft-summit-2025",
		"location":"Online",
		"price":0.0,
		"startDate":"2025-03-16",
		"endDate":"2025-03-18",
		"startTime":"19:00:00"
		"endTime":"21:00:00"
},
...
]

```

```bash
Endpoint: GET /events/PRETTY_NAME
Descrição: Recupera um evento pelo seu Pretty Name

Exemplo: 
http://localhost:8080/events/codecraft-summit-2025

Resposta:
{
	  "id": 1,
		"name":"CodeCraft Summit 2025",
		"prettyName":"codecraft-summit-2025",
		"location":"Online",
		"price":0.0,
		"startDate":"2025-03-16",
		"endDate":"2025-03-18",
		"startTime":"19:00:00"
		"endTime":"21:00:00"
}
```

### US01 - Realizar Inscrição

Este User Story atende aos requisitos funcionais RF01 e RF02

```bash
Endpoint: POST /subscription/PRETTY_NAME
```

- O usuário poderá fazer inscrição em um evento previamente cadastrado na base de dados, informando seu nome e seu email
- Como é um sistema onde podemos ter vários eventos, pode acontecer de um usuário já estar em nossa base de dados por ter participado de eventos anteriores. Dessa forma, basta recuperar seus dados e realizar a inscrição
- O Usuário não pode se inscrever duas vezes no mesmo evento. Se houver já uma inscrição no respectivo evento pelo usuário, uma mensagem de erro deverá ser enviada (conflict)
- Ao final da realização da inscrição, a resposta será um JSON com o número da inscrição no evento

Requisição Esperada

```bash
{
   "userName":"John Doe",
   "email":"john@doe.com"
}
```

Resposta Esperada

```bash
{ 
  "subscriptionNumber":1,
	"designation": "https://devstage.com/codecraft-summit-2025/123"
}
```

**Casos de uso:**

**Caso base:**

Condições: Evento previamente cadastrado, Usuário ainda inexistente (email não existe)

Ações:

- Inserir usuário na base
- Adicionar nova inscrição para o usuário
- Retornar o resultado da inscrição contendo o ID e o link para indicação

**Caso Alternativo 1:**

Condições: Usuário existe na base, porém não há inscrição dele

Ações:

- Adiciona nova inscrição para o usuário
- Retornar o resultado da inscrição contendo o ID e o link para indicação

Caso Alternativo 2:

Condições: Evento não existe

Ações:

- Lançar uma exceção EventNotFound indicando que o evento não existe

**Caso Alternativo 3:**

Condições: Já existe inscrição do usuário no evento

Ações:

- Lançar uma exceção indicando conflito

### US02 - Gerar Ranking de Inscritos

Este User Story atende ao requisito Funcional RF03

```bash
Endpoint: GET /subscription/PRETT_NAME/ranking
```

- Possibilidade de gerar um ranking de número de inscritos por indicação (ou seja, ordenado pela somatória de inscritos por indicação)
- Ideal: o ranking exibir os 3 primeiros colocados (gold, silver e bronze)

```bash
http://localhost:8080/subscription/codecraft-summit-2025/ranking
[
	{
		"userName":"John Doe",
		"subscribers":1000
	},
	{
		"userName":"Mary Page",
		"subscribers":873	
	},
	{
		"userName":"Frank Lynn",
		"subscribers":690	
	}
]
```

### US03 - Gerar Estatísticas de número de inscritos por participante

Este User Story atende ao requisito Funcional RF04

```bash
Endpoint: GET /subscription/PRETTY_NAME/ranking/USERID
```

- Recuperar o número de inscritos que efetivaram sua participação no evento indicados por um determinado usuário (USERID), bem como sua colocação no ranking geral.