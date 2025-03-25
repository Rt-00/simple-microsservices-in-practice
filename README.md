# Microsserviços de Usuário e E-mail com RabbitMQ

Este projeto é uma implementação moderna de microsserviços usando **Spring Boot**, **RabbitMQ** e **PostgreSQL**. Ele demonstra a integração basica entre serviços independentes, utilizando filas de mensagens para comunicação assíncrona, garantindo escalabilidade e confiabilidade no processamento de eventos.

## 🚀 Tecnologias Utilizadas

- **Java com Spring Boot** - Desenvolvimento robusto e escalável.
- **RabbitMQ** - Mensageria para comunicação assíncrona eficiente.
- **PostgreSQL** - Banco de dados relacional confiável.
- **Docker e Docker Compose** - Containerização apenas para os bancos de dados.

## 🔍 Arquitetura do Projeto

Este sistema é composto por dois microsserviços independentes:

### 🟢 Microsserviço de Usuário

- Disponibiliza uma API REST para cadastro de usuários.
- Envia eventos para uma fila RabbitMQ ao cadastrar um novo usuário.

### 📧 Microsserviço de E-mail

- Consome eventos da fila RabbitMQ.
- Registra os dados no banco de dados.
- Envia um e-mail de "bem-vindo" para o usuário.

Cada microsserviço possui seu próprio banco de dados PostgreSQL, que roda em containers Docker.

---

## 🛠️ Configuração e Execução

### Clonar o Repositório

```sh
  git clone <URL_DO_REPOSITORIO>
  cd <NOME_DO_REPOSITORIO>
```

### Criar uma conta no CloudAMQP e criar uma fila. Utilizar a URL da fila no microsserviço de email.
```sh
application.properties

spring.rabbitmq.addresses=amqps://wlbypmry:8dmLyjYz0rJtjgh68cI3Muun5xSukJJo@jaragua.lmq.cloudamqp.com/wlbypmry
```

### Subir os Containers do PostgreSQL

```sh
  docker compose -f email/docker-compose.yml up -d
  docker compose -f user/docker-compose.yml up -d
```

### Iniciar os Microsserviços

```sh
  cd user; ./mvnw spring-boot:run
  cd email; ./mvnw spring-boot:run
```

---

### ✅ Testando a API
```sh
curl -X POST http://localhost:8080/users -H "Content-Type: application/json" -d '{"name": "João Silva", "email": "joao@email.com"}'
```
---

### ⚠️ Configuração do E-mail no Google

Para que o serviço de e-mail funcione corretamente, será necessário configurar Senhas de Apps no Google.

### 📌 Como gerar uma Senha de App no Gmail:

1. Acesse Minha Conta Google - Segurança.
2. Ative a Verificação em duas etapas (caso ainda não esteja ativada).
3. Vá até Senhas de App e gere uma nova senha para este serviço.
4. Utilize essa senha gerada no arquivo de configuração do microsserviço de e-mail.

---

### 🔥 Melhorias Futuras

🔐 Autenticação JWT para comunicação segura entre os serviços.

🧪 Testes automatizados com JUnit e integração contínua.

📊 Monitoramento com Prometheus e Grafana.

⬆️ Mensageria avançada com Dead Letter Queue (DLQ) para mensagens não processadas.
