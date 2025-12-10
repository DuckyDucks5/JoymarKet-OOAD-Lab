package view;

import java.util.ArrayList;

import controller.CourierHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Courier;

public class CourierListPage extends VBox {

    CourierHandler handler = new CourierHandler(); // handler untuk mengambil atau mengubah data courier

    // Ukuran kolom agar konsisten
    private final int COL1 = 120;
    private final int COL2 = 160;
    private final int COL3 = 130;
    private final int COL4 = 180;
    private final int COL5 = 130;
    private final int COL6 = 130;

    public CourierListPage() {
        
        setSpacing(20);
        setPadding(new Insets(25));

        //Label courier list
        Label title = new Label("Courier List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        getChildren().add(title);

        // Header tabel
        GridPane header = new GridPane();
        header.setHgap(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 6;");

        addHeader(header, "Name", 0, COL1);
        addHeader(header, "Email", 1, COL2);
        addHeader(header, "Phone", 2, COL3);
        addHeader(header, "Address", 3, COL4);
        addHeader(header, "Vehicle Type", 4, COL5);
        addHeader(header, "Vehicle Plate", 5, COL6);

        getChildren().add(header);

        // Ambil list courier
        ArrayList<Courier> couriers = handler.getAllCouriers();

        // Tampilkan setiap courier sebagai row
        for (Courier c : couriers) {

            GridPane row = new GridPane();
            row.setHgap(10);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color: #F9FAFB; -fx-background-radius: 6;");

            // Isi row
            addCell(row, c.getFullname(), 0, COL1);
            addCell(row, c.getEmail(), 1, COL2);
            addCell(row, c.getPhone(), 2, COL3);
            addCell(row, c.getAddress(), 3, COL4);
            addCell(row, c.getVehicleType(), 4, COL5);
            addCell(row, c.getVehiclePlate(), 5, COL6);

            getChildren().add(row);
        }
    }

    // Menambahkan header label dengan styling 
    private void addHeader(GridPane grid, String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setPrefWidth(width);
        grid.add(lbl, col, 0);
    }

    // Menambahkan cell data
    private void addCell(GridPane grid, String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", 13));
        lbl.setPrefWidth(width);
        grid.add(lbl, col, 0);
    }
}
