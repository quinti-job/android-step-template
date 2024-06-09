package com.quinti.android_step_template.kmp.data.api.serialization

import korlibs.time.DateFormat
import korlibs.time.DateTimeTz
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * ISO 8601 "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX" 形式の変換
 */
class KlockDateTimeTzIso8601Serializer : KSerializer<DateTimeTz> {
    companion object {
        /**
         * "yyyy-MM-dd'T'HH:mm:ss.SX" のフォーマッター(小数部分が存在する)
         * 小数部分の桁数がAPIによって違うので耐えられる1~9桁まで耐えられる'S'で対応
         */
        fun decimalDateFormatter(): DateFormat {
            return DateFormat("yyyy-MM-dd'T'HH:mm:ss.SXXX")
        }

        /**
         * "yyyy-MM-dd'T'HH:mm:ssXXX" のフォーマッター(小数部分が存在しない)
         * 小数部分があるかどうかAPIによって違うのでfallback用として使う
         */
        fun dateFormatter(): DateFormat {
            return DateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        }
    }

    private val decimalFormat: DateFormat = decimalDateFormatter()
    private val format: DateFormat = dateFormatter()
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("DateTimeTz", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTimeTz) {
        encoder.encodeString(decimalFormat.format(value))
    }

    override fun deserialize(decoder: Decoder): DateTimeTz {
        val value = decoder.decodeString()
        return decimalFormat.tryParse(value)
            ?: format.tryParse(value)
            ?: throw IllegalStateException("日付フォーマットが不正です: $value")
    }
}