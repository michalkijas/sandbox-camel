package dev.skibi.camel;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class AppTest extends CamelTestSupport {

    @Test
    public void testMock() throws Exception {
        // given expected result
        getMockEndpoint("mock:result")
                .expectedBodiesReceived("New message body");

        // when send message
        template.sendBody("direct:start", "Initial message body");

        // then final result should be equal to mock result
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(exchange -> exchange.getIn().setBody("New message body"))
                        .to("mock:result");
            }
        };
    }

}
