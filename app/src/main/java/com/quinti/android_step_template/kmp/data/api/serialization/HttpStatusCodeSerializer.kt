package com.quinti.android_step_template.kmp.data.api.serialization

import io.ktor.http.HttpStatusCode
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**å
 * HttpStatusCode シリアライズ
 */
class HttpStatusCodeSerializer : KSerializer<HttpStatusCode> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("HttpStatusCode", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: HttpStatusCode) {
        encoder.encodeInt(value.value)
    }

    override fun deserialize(decoder: Decoder): HttpStatusCode {
        return HttpStatusCode.fromValue(decoder.decodeInt())
    }
}