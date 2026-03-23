# StudyFlash - API de Flashcards com Spaced Repetition

## Sobre o projeto

O StudyFlash é uma API REST que desenvolvi para gerenciar estudos com flashcards.

A ideia do projeto foi ir além de um CRUD simples e implementar um fluxo real de estudo, onde o sistema controla sessões, registra respostas e decide quando o usuário deve revisar cada conteúdo.

---

## Diferencial

Esse projeto não é apenas cadastro de dados. Ele implementa:

* controle de sessões de estudo
* registro de histórico de respostas
* cálculo de estatísticas
* algoritmo de repetição espaçada (inspirado no SM-2 usado no Anki)
* autenticação com JWT
* tratamento de erros com HTTP semântico

---

## Tecnologias

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Security (JWT)
* PostgreSQL
* Maven

---

## Arquitetura

Organizei o projeto por feature (por domínio), separando bem as responsabilidades.

Cada módulo segue um padrão:

* api → controllers
* service → regras de negócio
* domain → entidades e comportamento
* repository → acesso ao banco
* dto → comunicação entre camadas

### Módulos principais

* auth
* flashcard
* session
* statistics
* subject
* topic
* user
* studyresult

Também criei um módulo `shared` para:

* exceptions customizadas
* handler global de erros
* objetos comuns

---

## Design do código

Procurei manter o código o mais limpo possível:

* controllers simples, só orquestram
* regras de negócio concentradas no service e domínio
* entidades com comportamento (não só getters/setters)
* validações feitas dentro do domínio
* exceptions específicas para cada regra de negócio

---

## Sobre o uso de DDD

Não segui DDD de forma formal, mas apliquei vários conceitos:

* separação clara de responsabilidades
* regras no domínio
* organização por feature
* nomes próximos do problema de negócio

---

## Autenticação (JWT)

A API usa autenticação com JWT.

Fluxo:

1. usuário faz login
2. recebe um token
3. envia o token nas requisições protegidas

Header:

```
Authorization: Bearer TOKEN
```

---

## Funcionalidades

* criar subjects, topics e flashcards
* iniciar sessão de estudo
* responder flashcards
* impedir respostas duplicadas na mesma sessão
* calcular acertos, erros e precisão
* gerar estatísticas
* controlar revisão automática (spaced repetition)

---

## Fluxo de uso

1. criar subject
2. criar topic
3. criar flashcards
4. iniciar sessão
5. responder flashcards
6. finalizar sessão
7. consultar estatísticas

---

## Spaced Repetition

Implementei um algoritmo inspirado no SM-2.

A ideia é simples:

* se o usuário acerta → o intervalo aumenta
* se erra → reinicia o ciclo
* a próxima revisão é calculada automaticamente

Isso faz com que o sistema traga apenas os flashcards que precisam ser revisados no dia.

---

## Exemplo de endpoint

POST /sessions/{sessionId}/answer

```json
{
  "flashcardId": "UUID",
  "userAnswer": "Ocultar detalhes internos",
  "responseTime": 5
}
```

Resposta:

```json
{
  "correct": true,
  "totalAnswered": 2,
  "correctAnswers": 1,
  "wrongAnswers": 1,
  "accuracy": 50.0
}
```

---

## Estatísticas

GET /statistics

```json
{
  "totalReviews": 5,
  "correct": 3,
  "wrong": 2,
  "accuracy": 60.0
}
```

---

## Tratamento de erros

Implementei exceptions customizadas com retorno HTTP adequado:

* 400 → dados inválidos
* 404 → recurso não encontrado
* 409 → regra de negócio
* 500 → erro interno

Exemplo:

```json
{
  "status": 409,
  "mensagem": "Flashcard already answered in this session"
}
```

---

## Modelo de dados (resumo)

* Subject → agrupa tópicos
* Topic → agrupa flashcards
* FlashCard → conteúdo de estudo
* StudySession → sessão ativa
* StudyResult → histórico

---

## Como executar

1. clonar o repositório

```
git clone ...
```

2. configurar PostgreSQL

3. rodar a aplicação

```
./mvnw spring-boot:run
```

---

## Testes

Testei os endpoints usando Postman/Insomnia.

---

## Autor

Emmanuel Gomes - 
Backend Developer
