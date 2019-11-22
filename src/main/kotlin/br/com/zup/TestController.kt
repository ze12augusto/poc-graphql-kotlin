package br.com.zup

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test-method")
    fun testMethod(): String {
        return "Pintcher"
    }
}