package org.deeplearning4j.ui.weights;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.dropwizard.jackson.Jackson;
import org.deeplearning4j.nn.api.Model;
import org.deeplearning4j.nn.gradient.DefaultGradient;
import org.deeplearning4j.nn.gradient.Gradient;
import org.deeplearning4j.optimize.api.IterationListener;
import org.deeplearning4j.ui.UiServer;
import org.deeplearning4j.ui.providers.ObjectMapperProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.serde.jackson.VectorDeSerializer;
import org.nd4j.serde.jackson.VectorSerializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * A histogram iteration listener that
 * updates the weights of the model
 * with a web based ui.
 *
 * @author Adam Gibson
 */
public class HistogramIterationListener implements IterationListener {

    private Client client = ClientBuilder.newClient().register(JacksonJsonProvider.class).register(new ObjectMapperProvider());
    private WebTarget target;
    private int iterations = 1;

    public HistogramIterationListener(int iterations) {
        this.iterations = iterations;
        target = client.target("http://localhost:8080").path("weights").path("update");
    }




    @Override
    public boolean invoked() {
        return false;
    }

    @Override
    public void invoke() {

    }

    @Override
    public void iterationDone(Model model, int iteration) {
        if(iteration % iterations == 0) {
            ModelAndGradient g = new ModelAndGradient();
            g.setGradients(model.gradient().gradientForVariable());
            g.setParameters(model.paramTable());
            g.setScore(model.score());
            Response resp = target.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(Entity.entity(g,MediaType.APPLICATION_JSON));
            System.out.println(resp);
        }


    }
}
