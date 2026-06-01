import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CozinhaCliente {
    // Endereço do nosso servidor Python (FastAPI)
    private static final String API_URL = "http://localhost:8000/pedidos";
    private static final HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1) 
        .build();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("👨‍🍳 Conectado ao Sistema da Cozinha!");

        while (true) {
            System.out.println("\n--- PAINEL DA COZINHA ---");
            System.out.println("1. Ver todos os pedidos");
            System.out.println("2. Atualizar status de um pedido");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();

            try {
                if (opcao == 1) {
                    listarPedidos();
                } else if (opcao == 2) {
                    System.out.print("Digite o ID do Pedido: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Limpa o buffer do teclado
                    
                    System.out.print("Digite o novo status (ex: 'Em preparo', 'Saiu para entrega'): ");
                    String novoStatus = scanner.nextLine();
                    
                    atualizarStatus(id, novoStatus);
                } else if (opcao == 3) {
                    System.out.println("Encerrando painel...");
                    break;
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro ao comunicar com o servidor: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // Método que faz o GET para listar pedidos
    private static void listarPedidos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println("\n📋 [Pedidos no Servidor]:");
        // O servidor devolve um JSON puro. Para simplificar, vamos imprimi-lo direto na tela!
        System.out.println(response.body());
    }

    // Método que faz o PATCH para alterar o status
    private static void atualizarStatus(int id, String status) throws Exception {
        // Montando o JSON manualmente em formato de String
        String json = "{\"status\": \"" + status + "\"}";
        System.out.println("📦 Enviando JSON: " + json); // Adicione esta linha para debugar!

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "/" + id + "/status"))
                .header("Content-Type", "application/json") // Avisa o Python que estamos mandando JSON
                .method("PATCH", HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        System.out.println("\n✅ [Resposta do Servidor]:");
        System.out.println(response.body());
    }
}
