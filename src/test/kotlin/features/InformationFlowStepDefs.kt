package features

import com.fasterxml.jackson.databind.ObjectMapper
import com.ultraclearance.kotlin.controller.InformationDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.lang.NullPointerException

class InformationFlowStepDefs : En {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var information: InformationDto

    private lateinit var response: String

    private val klient = WebClient.builder()
            .baseUrl("http://localhost:8080/api/information")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

    init {
        Given("a set of information") { dataTable: DataTable ->
            val input = dataTable.asMap<String, String>(String::class.java, String::class.java)

            information = InformationDto(
                    details = input["details"] ?: throw NullPointerException("Missing value for 'details'")
            )


        }

        When("we post the information to the applications API") {
            response = klient.post().uri("/store")
                    .bodyValue(objectMapper.writeValueAsString(information))
                    .exchange()
                    .block()
                    ?.bodyToMono<String>()
                    ?.block() ?: throw NullPointerException("We did not get a valid response from the application!")
        }

        Then("we should receive a response with the text {string}") { responseText: String ->
            assert(response == responseText)
        }
    }
}