package wp46927.soapendpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import wp46927.User;
import wp46927.UserRepository;
import wp46927.springsoap.gen.GetUserRequest;
import wp46927.springsoap.gen.GetUserResponse;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://www.zut.edu.pl/springsoap/gen";

    @Autowired
    private UserRepository userRepository;

    // Endpoint obsługujący żądanie GetUserRequest
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();

        User user = userRepository.findUserByName(request.getName());

//        wp46927.springsoap.gen.User xmlUser =
//                wp46927.springsoap.gen.User.builder()
//                        .name(user.getName())
//                        .email(user.getEmail())
//                        .file(user.getFile())
//                        .build();
//        System.out.println(xmlUser);

//        response.setUser(xmlUser);

        return response;
    }
}
