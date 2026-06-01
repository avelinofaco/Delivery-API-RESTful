from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Dict
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI(title="API de Delivery - Sistemas Distribuídos")

# ---------------- MODELOS DE DADOS ----------------
class PedidoBase(BaseModel):
    item: str
    cliente: str
    preco: float

class Pedido(PedidoBase):
    id: int
    status: str = "Recebido"

class AtualizacaoStatus(BaseModel):
    status: str

# ---------------- BANCO DE DADOS (Em Memória) ----------------
# Usamos um dicionário onde a chave é o ID e o valor é o Pedido
banco_de_dados: Dict[int, Pedido] = {}
contador_id = 1

# ---------------- ROTAS (Endpoints) ----------------

@app.post("/pedidos", response_model=Pedido, status_code=201)
def criar_pedido(pedido: PedidoBase):
    global contador_id
    novo_pedido = Pedido(
        id=contador_id,
        item=pedido.item,
        cliente=pedido.cliente,
        preco=pedido.preco
    )
    banco_de_dados[contador_id] = novo_pedido
    contador_id += 1
    return novo_pedido

@app.get("/pedidos", response_model=List[Pedido])
def listar_pedidos():
    # Retorna todos os pedidos como uma lista
    return list(banco_de_dados.values())

@app.get("/pedidos/{pedido_id}", response_model=Pedido)
def buscar_pedido(pedido_id: int):
    if pedido_id not in banco_de_dados:
        # Se o cliente (Java/JS) pedir um ID que não existe, devolvemos erro 404
        raise HTTPException(status_code=404, detail="Pedido não encontrado.")
    return banco_de_dados[pedido_id]

@app.patch("/pedidos/{pedido_id}/status")
def atualizar_status(pedido_id: int, atualizacao: AtualizacaoStatus):
    if pedido_id not in banco_de_dados:
        raise HTTPException(status_code=404, detail="Pedido não encontrado.")
    
    pedido = banco_de_dados[pedido_id]
    pedido.status = atualizacao.status
    return {"mensagem": "Status atualizado com sucesso!", "pedido": pedido}

# Liberando o CORS para o nosso cliente JavaScript conseguir se comunicar
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], # Permite requisições de qualquer origem
    allow_credentials=True,
    allow_methods=["*"], # Permite GET, POST, PATCH, etc.
    allow_headers=["*"],
)