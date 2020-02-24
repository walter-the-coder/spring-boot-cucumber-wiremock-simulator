package features

import com.fasterxml.jackson.databind.ObjectMapper
import com.ultraclearance.kotlin.controller.InformationDto
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import java.lang.NullPointerException

class InformationFlowStepDefs : En {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var information: InformationDto

    private lateinit var response: String

    private val client = WebClient.builder()
            .baseUrl("http://localhost:8080/api/information")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()

    init {
        Given("some information") { dataTable: DataTable ->
            val input = dataTable.asMap<String, String>(String::class.java, String::class.java)

            information = InformationDto(
                    details = input["details"] ?: throw NullPointerException("Missing value for 'details'")
            )
        }

        When("we test the applications API by posting the information") {
            response = client.post().uri("/test")
                    .bodyValue(objectMapper.writeValueAsString(information))
                    .exchange()
                    .flatMap { it.bodyToMono(String::class.java) }
                    .block() ?: throw NullPointerException("We did not get a valid response from the application!")
        }

        Then("we should receive a response with the text {string}") { responseText: String ->
            assert(response == responseText)
        }
    }
}
