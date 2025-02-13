package com.example.notification_service.email;
import com.example.notification_service.DTO.OrderConfirmation;
import com.example.notification_service.DTO.ProductDTO;
import com.example.notification_service.DTO.UserDTO;
import com.example.notification_service.client.UserClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private  final JavaMailSender mailSender;

    public void sendOrderConfirmationEmail(OrderConfirmation orderConfirmation, UserDTO userDTO) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("nourhansaeed707@gmail.com");
        helper.setTo(userDTO.getEmail());
        helper.setSubject("Order confirmation - Order #" + orderConfirmation.getOrderId());
        StringBuilder productsTable = new StringBuilder();
        productsTable.append("<table border='1' style='border-collapse:collapse; width:100%;'>")
                .append("<tr><th>Product Name</th><th>Quantity</th><th>Price</th></tr>");

        for (ProductDTO product : orderConfirmation.getProducts()) {
            productsTable.append("<tr>")
                    .append("<td>").append(product.getName()).append("</td>")
                    .append("<td>").append(product.getQuantity()).append("</td>")
                    .append("<td>$").append(product.getPrice()).append("</td>")
                    .append("</tr>");
        }
        productsTable.append("</table>");
        String emailContent = "<html><body>"
                + "<h2>Dear " + userDTO.getFirstName() + " " + userDTO.getLastName()+ ",</h2>"
                + "<p>Thank you for your order! Here are your order details:</p>"
                + "<ul>"
                + "<li><strong>Order ID:</strong> " + orderConfirmation.getOrderId() + "</li>"
                + "<li><strong>Total Price:</strong> $" + orderConfirmation.getTotalAmount() + "</li>"
                + "</ul>"
                + "<h3>Ordered Products:</h3>"
                + productsTable.toString()
                + "<p>We appreciate your business!</p>"
                + "<p>Best Regards, <br/>Your Company Team</p>"
                + "</body></html>";
        helper.setText(emailContent, true);
        mailSender.send(message);
    }


}
