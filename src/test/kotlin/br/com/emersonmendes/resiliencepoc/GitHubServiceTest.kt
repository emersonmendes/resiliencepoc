package br.com.emersonmendes.resiliencepoc

import io.github.logrecorder.api.LogRecord
import io.github.logrecorder.logback.junit5.RecordLoggers
import io.github.resilience4j.retry.RetryRegistry
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL

@SpringBootTest
@TestConstructor( autowireMode = ALL )
internal class GitHubServiceTest(
    private val gitHubService: GitHubService,
    private val retryRegistry: RetryRegistry
) {

    @Test
    fun `Should get retryRegistry injection`() {
        assertThat(retryRegistry).isNotNull
        assertThat(retryRegistry.allRetries.count()).isEqualTo(2)
        assertThat(
            retryRegistry.allRetries.asJava().stream()
                .map { it.name }
                .anyMatch { it == "contributors" }
        ).isTrue
        assertThat(retryRegistry.allRetries.asJava().stream().map { it.retryConfig.maxAttempts }
            .findAny().get()).isEqualTo(2)
    }

    @Test
    @RecordLoggers(GitHubService::class)
    fun `Should retry on get error when try to get contributors`(log: LogRecord) {
        assertThatThrownBy { gitHubService.getGithubContributors() }
            .isInstanceOf(GitHubException::class.java)
        val tries = log.messages.count{ it.contains("---> GET http://localhost:9020/repos/OpenFeign/feign/contributors") }
        assertThat(tries).isEqualTo(2)
    }

}