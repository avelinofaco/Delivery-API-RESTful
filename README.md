🍔 Sistema Distribuído de Delivery - Comunicação Indireta (Message Queues)
Este repositório contém a implementação do Trabalho 4 - Comunicação Indireta, desenvolvido para a disciplina de Sistemas Distribuídos da Universidade Federal do Ceará - Campus de Quixadá, sob a orientação do Professor Rafael Braga.
O objetivo deste projeto foi evoluir a arquitetura do nosso sistema de Delivery anterior (baseado puramente em requisição/resposta direta) para uma arquitetura com nível de indireção na camada de comunicação. Para isso, adotamos a Opção C: Filas de Mensagens (Message Queues), utilizando o RabbitMQ como Message Broker.

--------------------------------------------------------------------------------
🏗️ Arquitetura e Tecnologias
O sistema agora é mediado por uma fila de mensagens persistente, garantindo o desacoplamento espacial e temporal completo entre quem faz o pedido e a cozinha que o prepara.
Message Broker (O Intermediário): Servidor RabbitMQ operando na porta 5672. Responsável por armazenar de forma durável as mensagens em disco e distribuí-las aos consumidores.
Produtor / Servidor API: Desenvolvido em Python utilizando o framework FastAPI e a biblioteca pika. Ele recebe as requisições HTTP do cliente web, valida os dados e atua como Producer, publicando o pedido na fila "pedidos" do RabbitMQ de forma assíncrona.
Cliente 1 (Painel do Usuário): Desenvolvido em JavaScript / HTML5. Executa no navegador e envia dados para a API Python, simulando o cliente realizando o pedido.
Cliente 2 (Painel da Cozinha / Consumidor): Desenvolvido em Java (11+) utilizando a biblioteca amqp-client. Atua como um Worker/Consumer, que consome os dados diretamente da fila do RabbitMQ e processa os pedidos utilizando confirmação manual de recebimento (ACK manual) para garantir a tolerância a falhas.

--------------------------------------------------------------------------------
💡 Princípios de Sistemas Distribuídos Aplicados
Desacoplamento Espacial: O Produtor (API Python) e o Consumidor (Cozinha Java) não conhecem o IP ou a porta um do outro. Ambos conectam-se apenas ao RabbitMQ.
Desacoplamento Temporal: A Cozinha pode estar desligada no momento em que o cliente faz o pedido. O RabbitMQ reterá os dados em segurança (fila durável) e os entregará assim que a cozinha for religada.
Tolerância a Falhas: Mensagens marcadas como persistentes no disco (delivery_mode=2) e uso de autoAck=false na cozinha garantem que nenhum pedido seja perdido caso a energia acabe durante o processamento.

--------------------------------------------------------------------------------
🚀 Como Executar o Projeto
Para testar o fluxo assíncrono e a interoperabilidade do sistema, siga a ordem de inicialização abaixo:
Pré-requisitos
Docker instalado (para rodar o servidor RabbitMQ).
Python 3.8+ instalado.
Java 11+ (JDK) instalado.
Passo 1: Iniciando o Message Broker (RabbitMQ)
Abra um terminal e certifique-se de que o Docker está rodando.
Inicie o servidor do RabbitMQ através do comando:
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
Passo 2: Iniciando o Produtor (API Python)
Abra um segundo terminal na pasta raiz do projeto.
Instale as dependências:
Inicie o servidor FastAPI:
pip install fastapi uvicorn pydantic pika
uvicorn main:app --reload
Passo 3: Iniciando o Consumidor da Cozinha (Java)
Abra um terceiro terminal na pasta cozinha_java (onde está localizado o arquivo .java e as bibliotecas .jar).
Compile o código:
Windows: javac -cp "amqp-client-5.21.0.jar;slf4j-api-2.0.13.jar" KitchenConsumer.java
Linux/Mac: javac -cp "amqp-client-5.21.0.jar:slf4j-api-2.0.13.jar" KitchenConsumer.java
Execute o programa da Cozinha:
Windows: java -cp ".;amqp-client-5.21.0.jar;slf4j-api-2.0.13.jar" KitchenConsumer
Linux/Mac: java -cp ".:amqp-client-5.21.0.jar:slf4j-api-2.0.13.jar" KitchenConsumer
Passo 4: Iniciando o Cliente do Usuário (Navegador)
Dê um duplo clique no arquivo index.html (o cliente1) para abri-lo no seu navegador.
Preencha os campos e confirme o pedido. Observe que ele será processado instantaneamente pela cozinha no terminal Java!

--------------------------------------------------------------------------------

👥 Autores
[Antonio Avelino] 
[Andre Luiz] 
