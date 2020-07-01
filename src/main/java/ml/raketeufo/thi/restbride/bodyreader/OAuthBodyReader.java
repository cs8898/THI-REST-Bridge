package ml.raketeufo.thi.restbride.bodyreader;

import ml.raketeufo.thi.restbride.entity.dto.request.OAuthBody;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

@Provider
@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
public class OAuthBodyReader implements MessageBodyReader<OAuthBody> {

    @Context
    Providers providers;

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == OAuthBody.class;
    }

    @Override
    public OAuthBody readFrom(Class<OAuthBody> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        MessageBodyReader<Form> formProvider = providers.getMessageBodyReader(Form.class, Form.class, annotations, mediaType);
        if (formProvider == null) {
            throw new IllegalStateException("Cannot find default form entity provider");
        }
        Form form = formProvider.readFrom(Form.class, Form.class, annotations, mediaType, httpHeaders, entityStream);
        MultivaluedMap<String, String> map = form.asMap();

        OAuthBody oAuthBody = new OAuthBody();
        oAuthBody.grantType = map.getFirst("grant_type");
        oAuthBody.clientId = map.getFirst("client_id");
        oAuthBody.clientSecret = map.getFirst("client_secret");
        oAuthBody.username = map.getFirst("username");
        oAuthBody.password = map.getFirst("password");

        return oAuthBody;
    }
}
