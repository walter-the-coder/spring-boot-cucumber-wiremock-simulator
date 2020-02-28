package features

import com.fasterxml.jackson.databind.ObjectMapper
import com.ultraclearance.kotlin.controller.InformationDto
import com.ultraclearance.kotlin.controller.ResponseDto
import com.ultraclearance.kotlin.utils.getValidResponse
import com.ultraclearance.kotlin.utils.bodyTo
import io.cucumber.datatable.DataTable
import io.cucumber.java8.En
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient

class InformationFlowStepDefs : En {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var information: InformationDto

    private lateinit var response: ClientResponse

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
                    .getValidResponse()
        }

        When("we post the information to the application") {
            response = client.post().uri("/submit")
                    .bodyValue(objectMapper.writeValueAsString(information))
                    .getValidResponse()
        }

        Then("we should receive a response with the text {string}") { responseText: String ->
            assert(response.bodyTo(String::class.java) == responseText)
        }

        Then("we should receive a response informing us that the information processed with status {string}") {
            expectedStatus: String ->

            assert(response.statusCode().is2xxSuccessful)
            val responseDto = response.bodyTo(ResponseDto::class.java)
            assert(responseDto.message  == "Information processed with status=$expectedStatus")
        }
    }
}
