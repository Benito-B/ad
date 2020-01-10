import controller.dao.ClientDAO;
import model.Client;

import java.util.Scanner;

public class ProjectMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ClientDAO dao = new ClientDAO();
        Client saveMe = new Client();
        saveMe.setUsername("Benito");
        
        Client get = new Client();
        System.out.print("Introduce el nombre del cliente a buscar:");
        Client client = dao.getClient(sc.nextLine());
        if(client == null)
            System.out.println("El nombre introducido no pertenece a ningún cliente.");
        else
            if(client.getOrders().size() > 0)
                System.out.println("El cliente buscado tiene los siguientes pedidos: " + client.getOrders());
            else
                System.out.println("El cliente buscado no tiene pedidos.");
    }
}
