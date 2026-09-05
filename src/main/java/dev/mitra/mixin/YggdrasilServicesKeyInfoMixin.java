package dev.mitra.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.security.Signature;
import java.util.Base64;

@Mixin(targets = "com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo")
public abstract class YggdrasilServicesKeyInfoMixin {

    @WrapOperation(
            method = "validateProperty",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Base64$Decoder;decode(Ljava/lang/String;)[B"
            ),
            require = 0
    )
    private byte[] quietauth$safeBase64Decode(
            Base64.Decoder decoder,
            String source,
            Operation<byte[]> original
    ) {
        if (source == null || source.isEmpty()) {
            return new byte[0];
        }

        try {
            return original.call(decoder, source);
        } catch (IllegalArgumentException e) {
            return new byte[0];
        }
    }

    @WrapOperation(
            method = "validateProperty",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/security/Signature;verify([B)Z"
            ),
            require = 0
    )
    private boolean quietauth$safeSignatureVerify(
            Signature signature,
            byte[] signatureBytes,
            Operation<Boolean> original
    ) {
        if (signatureBytes == null || signatureBytes.length == 0) {
            return false;
        }

        try {
            return original.call(signature, signatureBytes);
        } catch (Exception e) {
            return false;
        }
    }
}
