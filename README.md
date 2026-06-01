# 🍔 Sistema Distribuído de Delivery - API RESTful

[cite_start]Este repositório contém a implementação do **Trabalho 3 - Application Interface Programming (API)** [cite: 9][cite_start], desenvolvido para a disciplina de Sistemas Distribuídos [cite: 5] [cite_start]da Universidade Federal do Ceará - Campus de Quixadá [cite: 4][cite_start], sob a orientação do Professor Rafael Braga.

[cite_start]O objetivo deste projeto é reimplementar um serviço remoto baseado num protocolo de requisição/resposta, focando estritamente na comunicação via API (sem a utilização de Sockets ou RMI)[cite: 11, 12]. [cite_start]O sistema simula um serviço de Delivery de comida, integrando três linguagens de programação diferentes[cite: 15].

---

## 🏗️ Arquitetura e Tecnologias

O sistema é composto por um servidor centralizado e dois clientes independentes que se comunicam através do protocolo HTTP trocando mensagens em formato JSON.

* **Servidor (A Fonte da Verdade):** Desenvolvido em **Python** utilizando o framework **FastAPI**. Ele é o responsável por manter o estado da aplicação e processar as requisições.
* **Cliente 1 (Painel do Usuário):** Desenvolvido em **JavaScript / HTML5**. Executa diretamente no navegador e consome a API através da `Fetch API` nativa. Permite criar novos pedidos e visualizar a lista em tempo real.
* **Cliente 2 (Painel da Cozinha):** Desenvolvido em **Java (11+)**. É uma aplicação de console que utiliza o `java.net.http.HttpClient` (forçado via HTTP/1.1 para compatibilidade) para buscar os pedidos e atualizar o status de preparo.

---

## 🗺️ Mapeamento da API (Endpoints)

O Servidor Python expõe os seguintes serviços:

| Método HTTP | Rota | Descrição | Corpo da Requisição (JSON) |
| :--- | :--- | :--- | :--- |
| **GET** | `/pedidos` | Retorna todos os pedidos registrados. | *Nenhum* |
| **POST** | `/pedidos` | Cria um novo pedido de delivery. | `{"item": "string", "cliente": "string", "preco": float}` |
| **GET** | `/pedidos/{id}` | Busca os detalhes de um pedido específico. | *Nenhum* |
| **PATCH** | `/pedidos/{id}/status` | Atualiza o status atual do pedido. | `{"status": "string"}` |

---

## 🚀 Como Executar o Projeto

Para testar a interoperabilidade do sistema distribuído, você precisará executar o servidor e os dois clientes simultaneamente.

### Pré-requisitos
* **Python 3.8+** instalado.
* **Java 11+** (JDK) instalado.
* Um navegador web atualizado (Chrome, Firefox, Edge).

### Passo 1: Iniciando o Servidor (Python)
1. Abra um terminal na pasta raiz do projeto.
2. Instale as dependências executando: 
   `pip install fastapi uvicorn pydantic`
3. Inicie o servidor FastAPI:
   `uvicorn main:app --reload`
4. O servidor estará rodando em `http://localhost:8000`. Você pode ver a documentação interativa acessando `http://localhost:8000/docs`.

### Passo 2: Iniciando o Cliente do Usuário (JavaScript)
1. Navegue até o diretório onde está o cliente JS.
2. Basta dar um duplo clique no arquivo `index.html`.
3. Ele abrirá no seu navegador padrão, pronto para enviar pedidos ao servidor.

### Passo 3: Iniciando o Cliente da Cozinha (Java)
1. Abra um *novo* terminal na pasta onde está localizado o cliente Java.
2. Compile o código:
   `javac CozinhaCliente.java`
3. Execute o programa:
   `java CozinhaCliente`
4. Utilize o menu no terminal para listar os pedidos feitos pelo cliente JS e atualizar seus status.

---

## 📹 Apresentação e Demonstração

[cite_start]Como parte dos critérios de avaliação, a dupla gravou uma demonstração mostrando o código-fonte de todos os componentes e o sistema em funcionamento real com a interação entre ambos os estudantes[cite: 14, 17].

* **Link do Vídeo:** [Insira o link do YouTube/Drive aqui]
* [cite_start]**Relatório:** [Insira o link ou aviso de que o relatório em PDF está anexo no repositório] [cite: 17]

---

## 👥 Autores
* [Antonio Avelino] - [552416]
* [Andre Luiz] - [538034]
