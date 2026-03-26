package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import model.Amount;
import model.Client;
import model.Product;
import model.Sale;
import java.util.ArrayList;
import java.util.Scanner;
import model.Employee;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private ArrayList<Sale> sales;

    public Shop() {
        inventory = new ArrayList<>();
        sales = new ArrayList<>();
        loadInventory();
    }

    public static void main(String[] args) {
        new view.LoginView();
    }

    public void initSession() {
        Scanner scanner = new Scanner(System.in);
        boolean logged = false;
        Employee employee = new Employee("test");

        while (!logged) {
            System.out.print("Introduzca numero de empleado: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            employee.setEmployeeId(id);

            System.out.print("Introduzca contraseña: ");
            String pass = scanner.nextLine();

            employee.setPassword(pass);

            logged = employee.login(id, pass);

            if (logged) {
                System.out.println("Login correcto");
            } else {
                System.out.println("Datos incorrectos");
            }
        }
    }

    public void loadInventory() {

        /*
        inventory.add(new Product("Manzana", new Amount(10.00), true, 10));
        inventory.add(new Product("Pera", new Amount(20.00), true, 20));
        inventory.add(new Product("Hamburguesa", new Amount(30.00), true, 30));
        inventory.add(new Product("Fresa", new Amount(5.00), true, 20));
         */
        // Añadir llamada metodo readInventory 
        readInventory();
    }

    private void readInventory() {
        // i. Ubicar fichero inputInventory.txt y ruta de lectura 
        File f = new File("files/inputInventory.txt");
        try (Scanner reader = new Scanner(f)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.isEmpty()) {
                    continue;
                }

                // Procesamiento del formato: Product: Manzana; Wholesaler Price: 10.00; Stock:10; 
                String[] parts = line.split(";");
                String name = parts[0].split(":")[1].trim();
                double price = Double.parseDouble(parts[1].split(":")[1].trim());
                int stock = Integer.parseInt(parts[2].split(":")[1].trim());

                // ii. Por cada línea del fichero agregar un producto al inventario 
                this.inventory.add(new Product(name, new Amount(price), true, stock));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encuentra el archivo de inventario.");

        }
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }

        }
        return null;
    }

    public void removeProduct(Product p) {
        if (p != null) {
            this.inventory.remove(p);
            saveInventory(); // <--- Guardamos el cambio
        }
    }

    public void addProduct(Product p) {
        if (p != null) {
            this.inventory.add(p);
            saveInventory(); // Persistencia automática
        }
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    public void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    public void sale() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Realizar venta, escribir nombre cliente");
        String clientName = scanner.nextLine();

        Client client = new Client(clientName);

        ArrayList<Product> productsVenta = new ArrayList<>();
        double totalAmount = 0.0;
        String name = "";

        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = scanner.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);

            if (product != null && product.isAvailable()) {
                productsVenta.add(product);
                totalAmount += product.getPublicPrice().getValue();
                product.setStock(product.getStock() - 1);
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println("Producto añadido con éxito");
            } else {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        if (totalAmount > 0) {
            totalAmount = totalAmount * 1.21;

            Amount totalAmountObj = new Amount(totalAmount);
            boolean isPaid = client.pay(totalAmountObj);

            if (!isPaid) {
                double debt = client.getBalance().getValue() - totalAmount;
                System.out.println("Cliente debe: " + debt + "?");
            }

            sales.add(new Sale(client, productsVenta, totalAmount));
            cash.setValue(cash.getValue() + totalAmount);

            System.out.println("Venta realizada con éxito, total: " + totalAmount);
        } else {
            System.out.println("Venta realizada con éxito, total: " + totalAmount);
        }
    }

    public void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.println(sale.toString());
            }
        }
        System.out.println("¿Desea exportar las ventas a un fichero? (s/n)");
        Scanner sc = new Scanner(System.in);
        if (sc.nextLine().equalsIgnoreCase("s")) {
            writeSales();
        }
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void writeSales() {
        String fileName = "files/sales_" + LocalDate.now() + ".txt";
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            int index = 1;
            for (Sale s : sales) {
                // Formato exacto del Documento Funcional 
                writer.println(index + ";Client " + s.getClient().getName() + ";");
                writer.print(index + "; Products-");
                for (int j = 0; j < s.getProducts().size(); j++) {
                    Product p = s.getProducts().get(j);
                    writer.print(p.getName() + ", " + p.getPublicPrice().getValue() + "?");
                    if (j < s.getProducts().size() - 1) {
                        writer.print("; ");
                    }
                }
                writer.println(";");
                writer.println(index + "; Amount =" + s.getAmount() + "?;");
                index++;
            }
            javax.swing.JOptionPane.showMessageDialog(null, "Ventas exportadas a " + fileName);
        } catch (Exception e) {
            System.out.println("Error al exportar.");
        }
    }

    public model.Amount getCash() {
        return cash;
    }

    public ArrayList<Product> getInventory() {
        return inventory;
    }

    public void saveInventory() {
        File f = new File("files/inputInventory.txt");
        try (PrintWriter writer = new PrintWriter(f)) {
            for (Product p : inventory) {
                writer.println("Product: " + p.getName() + "; Wholesaler Price: "
                        + p.getWholesalerPrice().getValue() + "; Stock:" + p.getStock() + ";");

            }
        } catch (Exception e) {
            System.out.println("Error al guardar inventario.");
        }
    }
}
