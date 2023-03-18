package cn.repigeons.webflux.webClient

import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

object RequestFilter : ExchangeFilterFunction {
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> =
        next.exchange(request)
            .publishOn(Schedulers.boundedElastic())
            .doFirst {
                logger.debug("===========================request begin================================================")
                logger.debug("{} {}", request.method(), request.url())
                logger.debug("Headers  : {}", request.headers())
                logger.debug("Request  : ")
                logger.debug("==========================request end================================================")
            }
            .doOnNext { response ->
                response.bodyToMono<String>()
                    .doFirst {
                        logger.debug("============================response begin==========================================")
                    }
                    .doFinally {
                        logger.debug("=======================response end=================================================")
                    }
                    .doOnNext {
                        logger.debug("Status   : {}", response.statusCode())
                        logger.debug("Headers  : {}", response.headers())
                    }
                    .doOnNext { body ->
                        logger.debug("Response : {}", body)
                    }
                    .subscribe()
            }
}