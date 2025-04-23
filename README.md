# ♻️ Smart Recycle

O **Smart Recycle** é uma aplicação que utiliza uma API em Java (Spring Boot) conectada a um banco de dados **MongoDB** para gerenciar informações relacionadas à reciclagem inteligente. O projeto é totalmente containerizado com **Docker** e orquestrado via **docker-compose**, garantindo fácil configuração e execução integrada.

---

## 📋 Pré-requisitos

Antes de iniciar, certifique-se de ter os seguintes itens instalados no seu sistema:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🧩 Serviços

O arquivo `docker-compose.yml` define os seguintes serviços:

### 1. 📦 MongoDB
- **Imagem:** `mongo:latest`
- **Porta:** `27017:27017`
- **Nome do container:** `mongodb`
- **Rede:** `app-network`

### 2. 🔧 Smart Recycle (Spring Boot)
- **Build:** A partir do `Dockerfile` na raiz do projeto
- **Porta:** `8080:8080`
- **Nome do container:** `smart-recycle-api`
- **Depende de:** `mongodb`
- **Rede:** `app-network`
- **Variáveis de Ambiente:**
  - `PROFILE=prd`
  - `MONGODB_URI=${MONGODB_URI}`
  - `MONGODB_DATABASE=${MONGODB_DATABASE}`

---

## ▶️ Como executar

Clone o repositório:

```bash
git clone https://github.com/bfernandodco/smart-recycle.git
