# ♻️ Smart Recycle

O **Smart Recycle** é uma aplicação que utiliza uma API em Java (Spring Boot) conectada a um banco de dados **MongoDB** para gerenciar informações relacionadas à reciclagem inteligente. O projeto é totalmente containerizado com **Docker** e orquestrado via **docker-compose**, garantindo fácil configuração e execução integrada.

---

## 📋 Pré-requisitos

Antes de iniciar, certifique-se de ter os seguintes itens instalados no seu sistema:

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

---

## 🧩 Serviços

O arquivo `docker-compose.yaml` define os seguintes serviços:

### 1. 📦 MongoDB
- **Imagem:** `mongo:latest`
- **Porta:** `27017:27017`
- **Nome do container:** `mongodb`
- **Rede:** `smart-recycle-network`

### 2. 🔧 Smart Recycle (Spring Boot)
- **Build:** A partir do `Dockerfile` na raiz do projeto
- **Porta:** `8080:8080`
- **Nome do container:** `smart-recycle-api`
- **Depende de:** `db`
- **Rede:** `smart-recycle-network`
- **Variáveis de Ambiente:**
    - `MONGODB_URI=mongodb://mongodb:27017/smart-recycle`
    - `JSON_WEB_TOKEN_SECRET=sua_chave_secreta`

---

## ▶️ Como executar

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_REPOSITORIO>
```

Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo:

```env
MONGODB_URI=mongodb://mongodb:27017/smart-recycle
JSON_WEB_TOKEN_SECRET=sua_chave_secreta
```

Para iniciar os serviços, execute:

```bash
docker-compose up
```

> O serviço da API será iniciado automaticamente após o MongoDB estar disponível, graças ao `depends_on`.

---

## 🌐 Acesso aos serviços

- **Swagger - Documentação da API:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **MongoDB:** Porta `27017` disponível no host

A documentação da API está disponível via Swagger. Use a interface para testar endpoints, visualizar parâmetros e entender as rotas disponíveis.

---

## ⛔ Parar os serviços

Para parar e remover os containers:

```bash
docker compose down
```

---

## 🗂️ Estrutura do Projeto

- `docker-compose.yaml`: Orquestração dos serviços (MongoDB + Spring Boot)
- `Dockerfile`: Build da aplicação Java
- `src/`: Código-fonte da aplicação
- `.env`: Variáveis de ambiente utilizadas no ambiente Docker

---

## ⚠️ Observações

- Certifique-se de que as portas `27017` (MongoDB) e `8080` (API) estejam livres no seu sistema antes de executar.
- Atualize o conteúdo do `.env` conforme o ambiente.
- A aplicação e o banco estão na mesma rede Docker (`smart-recycle-network`) e se comunicam usando o nome do serviço `mongodb`.

---

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais informações.
