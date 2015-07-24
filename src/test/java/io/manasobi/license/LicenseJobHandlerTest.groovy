package io.manasobi.license

import org.jongo.MongoCollection
import org.jongo.Update

import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

import io.manasobi.commons.crypto.Cipher;
import io.manasobi.license.LicenseJobHandler.LicenseJobHandlerImpl
import spock.lang.Shared
import spock.lang.Specification

class LicenseJobHandlerTest extends Specification {
	
	@Shared def licenseJobHandler
	
	@Shared	Cipher cipher
	
	@Shared MongoCollection licenseDetailsRepo

	
	private def setup() {
		licenseJobHandler = new LicenseJobHandlerImpl()
	}
		
	def "process"() {
		
		setup:
			cipher = Mock {
				getKey() >> 'GHv1dr2lIOd5FJ3TMEX/Rt3jIALmZXlrcoETfOnT3BMTho99MZvcn07q/KouLQOi4BzA5JGKyyOULE1yx5Z3WA=='.bytes
				encrypt(_, _, _) >> '014ebb342a91-03-fffffe049f813a20-0068006f00730074004e0061006d0065'
			}
			
			licenseDetailsRepo = Stub {
				update(_, _) >> Stub(Update)
			}
			
			licenseJobHandler.cipher = cipher
			licenseJobHandler.licenseDetailsRepo = licenseDetailsRepo
		
		
			def license = new License()
			license.id = '_id_1'
			license.type = 'Developer'
			license.expirationDate = '-'
			license.expirationDays= 0
			license.hostName = 'hostName'
			license.siteName = 'siteName'
			
		when:
			licenseJobHandler.process(license)
		then:
			notThrown()
	}
	
	def "generateKey :: 라이센스 키 생성"() {
		
		setup:
			cipher = Mock {
				getKey() >> 'GHv1dr2lIOd5FJ3TMEX/Rt3jIALmZXlrcoETfOnT3BMTho99MZvcn07q/KouLQOi4BzA5JGKyyOULE1yx5Z3WA=='.bytes
				encrypt(_, _, _) >> '014ebb342a91-03-fffffe049f813a20-0068006f00730074004e0061006d0065'
			}
			
			licenseJobHandler.cipher = cipher
		
		expect:
			licenseJobHandler.generateKey(license) == licenseKey
		where:
			license || licenseKey	
			new License(
				type: 'Production', 
				expirationDate: '-', 
				hostName: 'hostName-prod') || '014ebb342a91-03-fffffe049f813a20-0068006f00730074004e0061006d0065'
			new License(
				type: 'Trial', 
				expirationDate: '2020-01-30 12:00:00', 
				hostName: 'hostName-trial') || '014ebb342a91-03-fffffe049f813a20-0068006f00730074004e0061006d0065'
			new License(
				type: 'Developer', 
				expirationDate: '-', 
				hostName: 'hostName-dev') || '014ebb342a91-03-fffffe049f813a20-0068006f00730074004e0061006d0065'
	}
	
	def "stringToHex :: 문자열을 16진수로 변환"() {
		
		expect:
			licenseJobHandler.stringToHex(strToHex) == result
		
		where:
			strToHex   || result
			"test"     || '0074006500730074'
			"strToHex" || '0073007400720054006f004800650078'
	}
	
	
}
