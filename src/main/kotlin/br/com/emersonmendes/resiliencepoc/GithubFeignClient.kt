package br.com.emersonmendes.resiliencepoc

import feign.Headers
import feign.Param
import feign.RequestLine

interface GithubFeignClient {

    @RequestLine("GET /repos/{owner}/{repo}/contributors")
    @Headers("Accept: application/json")
    fun getContributors(@Param("owner") owner: String?, @Param("repo") repository: String?): List<Contributor>

}