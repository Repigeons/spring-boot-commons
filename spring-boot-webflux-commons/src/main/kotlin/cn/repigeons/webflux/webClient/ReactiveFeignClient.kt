package cn.repigeons.webflux.webClient

import feign.Client
import feign.Request
import feign.Response
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity

class ReactiveFeignClient(
    private val webClient: WebClient
) : Client {
    private val HttpStatusCode.reasonPhrase: String get() = HttpStatus.valueOf(value()).reasonPhrase
    override fun execute(request: Request, options: Request.Options) =
        webClient.method(HttpMethod.valueOf(request.httpMethod().name))
            .uri(request.url())
            .headers { request.headers().forEach { (t, u) -> it[t] = u.toList() } }
            .body(BodyInserters.fromValue(request.body()))
            .retrieve()
            .toEntity<ByteArray>()
            .map { entity ->
                Response.builder()
                    .status(entity.statusCode.value())
                    .reason(entity.statusCode.reasonPhrase)
                    .headers(entity.headers.toMap())
                    .body(entity.body)
                    .build()
            }
            .blockOptional()
            .get()
}