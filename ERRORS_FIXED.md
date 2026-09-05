# Suppressed Errors & Log Patterns

This document provides detailed examples of the specific log spam, exceptions, and warnings that **QuietAuth** catches and suppresses.

---

### 1. Skin Signature Base64 Exceptions

Occurs when servers send invalid or malformed Base64 strings in skin properties.

```text
[Worker-Main-1/ERROR]: Malformed signature encoding on property Property[name=textures, value=..., signature=BUILDER_KIWI]
java.lang.IllegalArgumentException: Illegal base64 character 5f
	at java.util.Base64$Decoder.decode0
	at com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo.validateProperty
	...

```

### 2. Signature Verification Exceptions

Occurs when property signature lengths do not match expected cryptographic standards.

```text
[Worker-Main-10/ERROR]: Failed to verify signature on property Property[name=textures, value=..., signature=]
java.security.SignatureException: Bad signature length: got 0 but was expecting 512
	at sun.security.rsa.RSASignature.engineVerify
	at com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo.validateProperty
	...

```

### 3. Invalid Skin Signature Warnings

```text
[Worker-Main-1/WARN]: Profile contained invalid signature for textures property (profile id: 04049c90-...)

```

### 4. Unknown Team Packets

Triggered when custom scoreboard or team handling on large servers sends removal packets for untracked teams.

```text
[Render thread/WARN]: Received packet for unknown team a679-79569b33: team action: REMOVE, player action: null

```

### 5. Item Model Parse Errors

Spam caused by custom server-side resource packs with custom item model formats.

```text
[Worker-Main-4/ERROR]: Couldn't parse item model 'hypixel_skyblock:item/...' from pack 'server/...': Not a JSON object: "..."

```

### 6. Missing Texture References

```text
[Worker-Main-10/WARN]: Missing texture references in model hypixel_skyblock:item/...: particle

```

### 7. File Hash Mismatch Warnings

```text
[Download-5/WARN]: Existing file C:\...\416bcc8... not found or had mismatched hash

```
