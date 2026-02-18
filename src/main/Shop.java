package main;

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
        Shop shop = new Shop();

        // Iniciar sesión antes del menú (Punto 6)
        shop.initSession();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver venta total");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;
                case 2:
                    shop.addProduct();
                    break;
                case 3:
                    shop.addStock();
                    break;
                case 4:
                    shop.setExpired();
                    break;
                case 5:
                    shop.showInventory();
                    break;
                case 6:
                    shop.sale();
                    break;
                case 7:
                    shop.showSales();
                    break;
                case 8:
                    // shop.showTotalAmount(); 
                    break;
                case 9:
                    shop.eliminateProduct();
                    break;
                case 10:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (!exit);
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
        inventory.add(new Product("Manzana", new Amount(10.00), true, 10));
        inventory.add(new Product("Pera", new Amount(20.00), true, 20));
        inventory.add(new Product("Hamburguesa", new Amount(30.00), true, 30));
        inventory.add(new Product("Fresa", new Amount(5.00), true, 20));
    }

    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product != null && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void eliminateProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el nombre del producto a eliminar: ");
        String name = scanner.nextLine();

        Product product = findProduct(name);

        if (product != null) {
            inventory.remove(product);
            System.out.println("El producto " + name + " ha sido eliminado correctamente.");
        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    public void addProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        if (findProduct(name) != null) {
            System.out.println("Producto ya existe!");
            return;
        }

        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        inventory.add(new Product(name, new Amount(wholesalerPrice), true, stock));
        System.out.println("Producto añadido con éxito.");
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
                System.out.println(sale);
            }
        }
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }
}
