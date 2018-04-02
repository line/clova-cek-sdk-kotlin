/*
 * Copyright (c) 2018 LINE Corporation. All rights Reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linecorp.clova.extension.client

import java.security.KeyFactory
import java.security.PublicKey
import java.security.Signature
import java.security.SignatureException
import java.security.spec.X509EncodedKeySpec
import java.util.*

/**
 *  The clova request verifier.
 */
interface RequestVerifier {
    /**
     * Verify clova request
     *
     * @param signature The signature of the request, it would be included in the http header
     * @param requestBody The request body
     * @return true if verification is passed.
     */
    fun verify(signature: String, requestBody: String): Boolean
}

internal class RequestVerifierImpl(key: String = "") : RequestVerifier {

    //We may need to get the key from remote in the future.
    private var publicKey: PublicKey

    init {
        publicKey = if (key.isEmpty()) {
            createPublicKey(BUILT_IN_REMOTE_PUBLIC_KEY)
        } else {
            createPublicKey(key)
        }
    }

    override fun verify(signature: String, requestBody: String): Boolean {

        val sha256Signature = Signature.getInstance("SHA256withRSA")
        sha256Signature.initVerify(publicKey)
        sha256Signature.update(requestBody.toByteArray(Charsets.UTF_8))
        return try {
            val signatureData = Base64.getDecoder().decode(signature)
            sha256Signature.verify(signatureData)
        } catch (e: SignatureException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun createPublicKey(keyString: String): PublicKey {
        val keyData = Base64.getDecoder().decode(extractPublicKeyString(keyString))
        val keySpec = X509EncodedKeySpec(keyData)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec)
    }

    private fun extractPublicKeyString(key: String): String =
            key.replace("\n", "")
               .replace(KEY_SECTION_START, "")
               .replace(KEY_SECTION_END, "")

    companion object {
        private const val BUILT_IN_REMOTE_PUBLIC_KEY = """
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwiMvQNKD/WQcX9KiWNMb
nSR+dJYTWL6TmqqwWFia69TyiobVIfGfxFSefxYyMTcFznoGCpg8aOCAkMxUH58N
0/UtWWvfq0U5FQN9McE3zP+rVL3Qul9fbC2mxvazxpv5KT7HEp780Yew777cVPUv
3+I73z2t0EHnkwMesmpUA/2Rp8fW8vZE4jfiTRm5vSVmW9F37GC5TEhPwaiIkIin
KCrH0rXbfe3jNWR7qKOvVDytcWgRHJqRUuWhwJuAnuuqLvqTyAawqEslhKZ5t+1Z
0GN8b2zMENSuixa1M9K0ZKUw3unzHpvgBlYmXRGPTSuq/EaGYWyckYz8CBq5Lz2Q
UwIDAQAB
-----END PUBLIC KEY-----"""

        private const val KEY_SECTION_START = "-----BEGIN PUBLIC KEY-----"
        private const val KEY_SECTION_END = "-----END PUBLIC KEY-----"
    }
}
