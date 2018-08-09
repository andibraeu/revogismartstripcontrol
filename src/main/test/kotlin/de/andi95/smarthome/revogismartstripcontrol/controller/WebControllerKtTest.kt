package de.andi95.smarthome.revogismartstripcontrol.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebControllerKtTest(@Autowired val restTemplate: TestRestTemplate) {

    @Test
    fun indexController() {
        val entity = restTemplate.getForEntity<String>("/")
        assert(entity.statusCode.equals(HttpStatus.OK))
        assert(entity.body!!.contains("<h1>Revogi Smart Strip Control</h1>"))
    }
}