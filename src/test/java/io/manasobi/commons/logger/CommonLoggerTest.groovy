package io.manasobi.commons.logger

import io.manasobi.license.License

import org.junit.Rule
import org.springframework.boot.test.OutputCapture

import spock.lang.Specification

class CommonLoggerTest extends Specification {

	@Rule
	def OutputCapture capture = new OutputCapture();

	
	def "debug :: DEBUG 레벨 메소드에 대한 유효성 테스트"() {
		
		when:
			CommonLogger.debug('debug msg', this.class)
		then:
			notThrown()
			capture.toString().contains('DEBUG')
			capture.toString().contains('[commons] debug msg')
	}
	
	def "info :: INFO 레벨 메소드에 대한 유효성 테스트"() {
		
		when:
			CommonLogger.info('info msg', this.class)
		then:
			notThrown()
			capture.toString().contains('INFO')
			capture.toString().contains('[commons] info msg')
	}
	
	def "warn :: WARN 레벨 메소드에 대한 유효성 테스트"() {
		
		when:
			CommonLogger.warn('warn msg', this.class)
		then:
			notThrown()
			capture.toString().contains('WARN')
			capture.toString().contains('[commons] warn msg')
	}

	def "error :: ERROR 레벨 메소드에 대한 유효성 테스트"() {
		
		when:
			CommonLogger.error('error msg', this.class)
		then:
			notThrown()
			capture.toString().contains('ERROR')
			capture.toString().contains('[commons] error msg')
	}

	def "trace :: TRACE 레벨 메소드에 대한 유효성 테스트"() {
		
		when:
			CommonLogger.trace('trace msg', this.class)
		then:
			notThrown()
	}
	
	def "parseLogPrifix :: 로그 출력시 사용되는 패키지명 조건 테스트"() {
		
		expect:
			CommonLogger.parseLogPrifix(clazz) == result
		where:
			clazz 			   || result
			CommonLogger.class || '[commons] '
			License.class 	   || '[license] '
	}
	
}
