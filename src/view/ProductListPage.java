package view;

import java.util.ArrayList;

import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Product;

public class ProductListPage extends GridPane{

	ProductHandler handler = new ProductHandler();
	
	public ProductListPage() {
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);

        add(new Label("Name"), 0, 0);
        add(new Label("Price"), 1, 0);
        add(new Label("Stock"), 2, 0);
        add(new Label("Category"), 3, 0);

        ArrayList<Product> products = handler.getAllProducts();

        int row = 1;
        for (Product p : products) {
            add(new Label(p.getName()), 0, row);
            add(new Label(String.valueOf(p.getPrice())), 1, row);
            add(new Label(String.valueOf(p.getStock())), 2, row);
            add(new Label(p.getCategory()), 3, row);
            row++;
        }
		
	}

}
