package br.com.emersonmendes.resiliencepoc

import feign.*
import feign.gson.GsonDecoder
import feign.slf4j.Slf4jLogger
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
@Retry(name = "contributors")
class GitHubService {

    private val feignClient = createFeignClient()

    fun getGithubContributors(): Boolean {
        try {
            val contributors: List<Contributor> = feignClient.getContributors("OpenFeign", "feign")
            for (contributor in contributors) {
                println(contributor.login + " (" + contributor.contributions + ")")
            }
            return true
        } catch (e: FeignException) {
            throw GitHubException("test retry!")
        }
    }

    private fun createFeignClient(): GithubFeignClient {
        val timeout = Duration.ofSeconds(5).toMillis()
        return Feign.builder()
            .client(feign.okhttp.OkHttpClient())
            .dismiss404()
            .decoder(GsonDecoder())
            .logger(Slf4jLogger(javaClass.canonicalName))
            .logLevel(Logger.Level.FULL)
            .retryer(Retryer.NEVER_RETRY)
            .options(Request.Options(timeout, TimeUnit.MILLISECONDS, timeout, TimeUnit.MILLISECONDS, true))
            .target(GithubFeignClient::class.java, "http://localhost:9020")
    }

}