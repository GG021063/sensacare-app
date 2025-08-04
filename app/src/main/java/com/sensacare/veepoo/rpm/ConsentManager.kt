package com.sensacare.veepoo.rpm

import android.content.Context
import android.util.Log
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

class ConsentManager(private val context: Context) {
    
    companion object {
        private const val TAG = "ConsentManager"
        private const val CONSENT_VERSION = "1.0"
    }
    
    /**
     * Capture consent and generate consent token
     */
    fun captureConsent(
        clientId: String,
        clientName: String,
        carerName: String? = null,
        consentType: ConsentType = ConsentType.DIGITAL_SIGNATURE
    ): ConsentResult {
        return try {
            val consentId = generateConsentId(clientId)
            val timestamp = System.currentTimeMillis()
            val consentToken = generateConsentToken(consentId, timestamp)
            
            val consentRecord = ConsentRecord(
                consentId = consentId,
                clientId = clientId,
                clientName = clientName,
                carerName = carerName,
                consentType = consentType,
                timestamp = timestamp,
                consentToken = consentToken,
                version = CONSENT_VERSION
            )
            
            Log.d(TAG, "Consent captured for client: $clientId")
            
            ConsentResult.Success(consentRecord)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to capture consent", e)
            ConsentResult.Failure(e.message ?: "Unknown error")
        }
    }
    
    /**
     * Validate consent token
     */
    fun validateConsent(token: String): Boolean {
        return try {
            // In a real implementation, this would validate against stored consent records
            // For now, we'll do basic format validation
            token.isNotEmpty() && token.length >= 32
        } catch (e: Exception) {
            Log.e(TAG, "Error validating consent token", e)
            false
        }
    }
    
    /**
     * Include consent in vitals payload
     */
    fun includeConsentInPayload(payload: VitalsUploadPayload, consentToken: String): VitalsUploadPayload {
        return payload.copy(consentToken = consentToken)
    }
    
    /**
     * Generate unique consent ID
     */
    private fun generateConsentId(clientId: String): String {
        val timestamp = System.currentTimeMillis()
        val random = Random().nextInt(10000)
        val input = "$clientId-$timestamp-$random"
        
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        
        return hash.joinToString("") { "%02x".format(it) }.take(16)
    }
    
    /**
     * Generate consent token
     */
    private fun generateConsentToken(consentId: String, timestamp: Long): String {
        val dateFormatter = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US)
        val dateString = dateFormatter.format(Date(timestamp))
        
        val input = "$consentId-$dateString-$CONSENT_VERSION"
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray())
        
        return hash.joinToString("") { "%02x".format(it) }
    }
    
    /**
     * Get consent policy text for display
     */
    fun getConsentPolicyText(): String {
        return """
            SENSACARE REMOTE PATIENT MONITORING CONSENT
            
            By providing your consent, you authorize Sensacare to:
            
            1. Collect vital signs data from your connected health device
            2. Transmit this data securely to the Sensacare RPM system
            3. Allow authorized healthcare providers to access your data
            4. Use your data for clinical monitoring and alert purposes
            5. Store your data in accordance with healthcare privacy regulations
            
            Your data will be:
            - Encrypted during transmission and storage
            - Accessible only to authorized healthcare personnel
            - Used solely for your health monitoring and care
            - Retained according to applicable healthcare regulations
            
            You may withdraw consent at any time by contacting your healthcare provider.
            
            By signing below, you acknowledge that you have read and understood this consent form.
        """.trimIndent()
    }
    
    /**
     * Get consent summary for display
     */
    fun getConsentSummary(consentRecord: ConsentRecord): String {
        val dateFormatter = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.US)
        val consentDate = dateFormatter.format(Date(consentRecord.timestamp))
        
        return """
            Consent Summary:
            - Client: ${consentRecord.clientName}
            - Date: $consentDate
            - Type: ${consentRecord.consentType.displayName}
            - Status: Active
        """.trimIndent()
    }
}

/**
 * Consent types
 */
enum class ConsentType(val displayName: String) {
    DIGITAL_SIGNATURE("Digital Signature"),
    VERBAL_CONFIRMATION("Verbal Confirmation"),
    CARER_PROXY("Carer Proxy Consent")
}

/**
 * Consent record data class
 */
data class ConsentRecord(
    val consentId: String,
    val clientId: String,
    val clientName: String,
    val carerName: String?,
    val consentType: ConsentType,
    val timestamp: Long,
    val consentToken: String,
    val version: String
)

/**
 * Consent result sealed class
 */
sealed class ConsentResult {
    data class Success(val consentRecord: ConsentRecord) : ConsentResult()
    data class Failure(val errorMessage: String) : ConsentResult()
} 