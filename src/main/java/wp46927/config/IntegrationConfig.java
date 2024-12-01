package wp46927.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import wp46927.User;
import wp46927.service.EmailService;
import wp46927.service.UserService;

@Configuration
public class IntegrationConfig {

	
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;  // Inject EmailService

    // Konfiguracja przepływu dla pobierania informacji o użytkowniku
    @Bean
    public IntegrationFlow userInfoInboundGateway() {
        return IntegrationFlow.from(
                Http.inboundGateway("/integration/user/{username}")
                        .requestMapping(mapping -> mapping.methods(HttpMethod.GET))
                        .requestPayloadType(String.class)
                        .payloadExpression("#pathVariables.username"))
                .channel("user-info")
                .get();
    }

    // Konfiguracja aktywatora usługi do obsługi informacji o użytkowniku
    @Bean
    public IntegrationFlow userInfoServiceActivator() {
        return IntegrationFlow
                .from("user-info")
                .handle(userService, "getUser")
                .get();
    }

    // Konfiguracja przepływu dla dodawania użytkownika
    @Bean
    public IntegrationFlow addUserInboundGateway() {
        return IntegrationFlow.from(
                Http.inboundGateway("/integration/user")
                        .requestMapping(mapping -> mapping.methods(HttpMethod.POST))
                        .requestPayloadType(User.class))
                .channel("addUser")
                .get();
    }

    // Konfiguracja aktywatora usługi do obsługi dodawania użytkownika
    @Bean
    public IntegrationFlow addUserServiceActivator() {
        return IntegrationFlow
                .from("addUser")
                .handle(userService, "addUser")
               // .handle("sendEmailFlow", "sendEmailChannel")
                .get();
    }

    // Konfiguracja przepływu dla usuwania użytkownika
    @Bean
    public IntegrationFlow deleteUserInboundGateway() {
        return IntegrationFlow.from(
                Http.inboundGateway("/integration/user/{username}")
                        .requestMapping(mapping -> mapping.methods(HttpMethod.DELETE))
                        .requestPayloadType(String.class)
                        .payloadExpression("#pathVariables.username"))
                .channel("deleteUser")
                .get();
    }

    // Konfiguracja aktywatora usługi do obsługi usuwania użytkownika
    @Bean
    public IntegrationFlow deleteUserServiceActivator() {
        return IntegrationFlow
                .from("deleteUser")
                .handle(userService, "deleteUser")
                .get();
    }
    
 // Konfiguracja przepływu dla usuwania użytkownika po ID
    @Bean
    public IntegrationFlow deleteUserByIdInboundGateway() {
        return IntegrationFlow.from(
                Http.inboundGateway("/integration/user/delete/{userId}")
                        .requestMapping(mapping -> mapping.methods(HttpMethod.DELETE))
                        .requestPayloadType(String.class)  // Change the payload type to String
                        .extractReplyPayload(true)
                        .headerExpression("userId", "#pathVariables.userId"))  // Extract the user ID from headers
        .channel("deleteUserById")
        .get();
    }
    @Bean
    public IntegrationFlow deleteUserByIdServiceActivator() {
        return IntegrationFlow
                .from("deleteUserById")
                .handle(message -> {
                    String userIdString = (String) message.getHeaders().get("userId");
                    Long userId = Long.parseLong(userIdString);
                    try {
						userService.deleteUserById(userId);
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                })
                .get();
    }



    // Konfiguracja przepływu dla aktualizacji informacji o użytkowniku
    @Bean
    public IntegrationFlow updateUserInboundGateway() {
        return IntegrationFlow.from(
                Http.inboundGateway("/integration/user/{username}")
                        .requestMapping(mapping -> mapping.methods(HttpMethod.PATCH))
                        .requestPayloadType(User.class)
                        .headerExpression("username", "#pathVariables.username"))
                .channel("updateUser")
                .get();
    }
    
    @Bean
    public IntegrationFlow sendEmailFlow() {
        return IntegrationFlow.from("sendEmailChannel")
                .handle(message -> {
                    // Cast the message to a User object
                    User user = (User) message.getPayload();

                    // Update the recipient email address here
                    String recipientEmail = "wp46927@zut.edu.pl";

                    // Send the email
                    sendEmail(recipientEmail);
                })
                .get();
    }


    private void sendEmail(String recipientEmail) {
        try {
            // Log statements for debugging
            System.out.println("Sending email to: " + recipientEmail);

            // Use EmailService to send the email
            emailService.sendSimpleMessage(recipientEmail, "Subject of the Email", "Content of the email");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
        }
    }

}


