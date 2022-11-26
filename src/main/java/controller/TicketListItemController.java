package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import database.entity.Ticket;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TicketListItemController {

    @FXML
    private Label dateText;

    @FXML
    private Label emailText;

    @FXML
    private Label nicknameText;

    @FXML
    private Label priceText;

    @FXML
    private Label seatText;

    @FXML
    private Label titleText;

    @FXML
    private Label hallText;

    @FXML
    private Button showQrCode;

    @FXML
    private ImageView imageView;
    private Ticket ticket;


    public void setData(Ticket ticket) {
        priceText.setText("Ár: " + ticket.getPrice() + "Ft");
        seatText.setText("Szék: " + ticket.getSeatId().toString());
        titleText.setText("Cím: " + ticket.getTitle());
        dateText.setText("Dátum: " + ticket.getDate());
        hallText.setText("Helyszín: " + ticket.getHallName());
        nicknameText.setText("Becenév: " + ticket.getNickname());
        emailText.setText("E-mail: " + ticket.getEmail());
        this.ticket = ticket;

        showQrCode.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    showQrCode(event);
                } catch (IOException | WriterException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void showQrCode(ActionEvent event) throws IOException, WriterException {
        String str = ticket.toString();
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        generateQRcode(str,charset, hashMap, 200, 200);
        InputStream stream = new FileInputStream("./ticket.png");
        Image image = new Image(stream);
        imageView.setImage(image);
    }

    public static void generateQRcode(String data, String charset, Map map, int h, int w) throws WriterException, IOException
    {
        String path = "./ticket.png";
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
}
