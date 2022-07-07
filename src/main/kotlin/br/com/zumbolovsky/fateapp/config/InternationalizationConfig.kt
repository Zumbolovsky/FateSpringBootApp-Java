package br.com.zumbolovsky.fateapp.config

import br.com.zumbolovsky.fateapp.config.error.ErrorMessageSourceAccessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.Locale

@Configuration
class InternationalizationConfig : WebMvcConfigurer {

    private val logger: Logger = LoggerFactory.getLogger(InternationalizationConfig::class.java)

    @Bean
    fun errorMessageSourceAccessor(messageSourceAccessor: MessageSourceAccessor): ErrorMessageSourceAccessor =
        logger.info("Configuring ErrorMessageSourceAccessor...")
            .run {
                ErrorMessageSourceAccessor(messageSourceAccessor)
            }

    @Bean
    fun localeResolver(): LocaleResolver =
        SessionLocaleResolver()
            .also {
                it.setDefaultLocale(Locale.ENGLISH)
            }

    @Bean
    fun messageSourceAccessor(messageSource: MessageSource): MessageSourceAccessor =
        MessageSourceAccessor(messageSource)

    @Bean
    fun localeInterceptor(): LocaleChangeInterceptor =
        LocaleChangeInterceptor()
            .also {
                it.paramName = "lang"
            }

    @Bean
    fun validator(messageSource: MessageSource): LocalValidatorFactoryBean =
        LocalValidatorFactoryBean()
            .also {
                it.setValidationMessageSource(messageSource)
            }

    @Bean
    fun messageSource(): MessageSource =
        ReloadableResourceBundleMessageSource()
            .also {
                it.setBasename("classpath:messages")
                it.setDefaultEncoding("UTF-8")
            }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeInterceptor())
    }
}