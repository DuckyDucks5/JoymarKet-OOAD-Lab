package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.User;

public class UserEditProfilePage extends GridPane {

    private User user; // menyimpan data user yang sedang login
    private Button confirmBtn;

    public UserEditProfilePage(User user) {
        this.user = user;

        // Mengatur Layout GridPane
        setVgap(15);
        setHgap(15);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);

        // Label Judul Halaman
        Label title = new Label("Edit Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Field Input Untuk Edit Profil
        TextField fullNameTf = new TextField(user.getFullname());
        TextField phoneTf = new TextField(user.getPhone());
        TextField addressTf = new TextField(user.getAddress());

        fullNameTf.setPrefWidth(250);
        phoneTf.setPrefWidth(250);
        addressTf.setPrefWidth(250);

        // Tombol Konfirmasi, Awalnya Tersembunyi
        confirmBtn = new Button("Confirm");
        confirmBtn.setVisible(false);
        confirmBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");
        confirmBtn.setPrefWidth(100);

        // Menampilkan Tombol Konfirmasi Saat Ada Perubahan Di Field
        fullNameTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));
        phoneTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));
        addressTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));

        // Menambahkan Komponen Ke GridPane
        add(title, 0, 0, 2, 1);
        add(new Label("Full Name"), 0, 1);
        add(fullNameTf, 1, 1);

        add(new Label("Phone"), 0, 2);
        add(phoneTf, 1, 2);

        add(new Label("Address"), 0, 3);
        add(addressTf, 1, 3);

        add(confirmBtn, 1, 4);

        // Event Tombol Konfirmasi Untuk Menyimpan Perubahan Profil
        confirmBtn.setOnAction(e -> {
            UserHandler handler = new UserHandler();
            String result = handler.editProfile(
                user,
                fullNameTf.getText(),
                phoneTf.getText(),
                addressTf.getText()
            );

            // Menampilkan Alert Berdasarkan Hasil Edit
            if("SUCCESS".equals(result)) {
                showAlert("Success", "Profile updated successfully!");
                confirmBtn.setVisible(false); // Sembunyikan Tombol Setelah Berhasil
            } else {
                showAlert("Error", result);
            }
        });
    }

    // Method Untuk Menampilkan Alert
    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
