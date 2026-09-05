# QuietAuth

A tiny client-side Fabric mod for Minecraft that suppresses log spam from Hypixel. Eliminates skin signature exceptions, resource pack warnings, team packet spam, and reduces log file size and disk writes.

## What It Does

QuietAuth uses two mechanisms:

1. **MixinExtras mixins:** Intercepts Base64 decoding and signature verification in `YggdrasilServicesKeyInfo.validateProperty()`. This prevents `IllegalArgumentException` and `SignatureException` from being thrown, eliminating exception object allocation, stack trace fill-in, and the ERROR log messages that contain full stack traces.

2. **Log4j2 filter:** Intercepts log events before they reach appenders (console + file). For known spam patterns, returns `DENY` which prevents message formatting, appender I/O, and file writes.

## Errors Fixed

For a complete breakdown and raw log examples of all exceptions, warnings, and packet spam suppressed by this mod, see **[ERRORS_FIXED.md](ERRORS_FIXED.md)**.

## Performance Impact

### What is actually improved:

| Benefit              | Impact      | Notes                                                                                                                            |
| -------------------- | ----------- | -------------------------------------------------------------------------------------------------------------------------------- |
| Log file size        | Significant | Removes 2,000+ lines per session. Log file shrinks from ~50-100MB to ~5-50MB.                                                    |
| Disk writes          | Significant | Less data written to SSD. Extends SSD lifespan by reducing wear.                                                                 |
| Exception allocation | Modest      | Prevents ~2,000+ exception object allocations. Each is ~48-64 bytes + stack trace elements. Total saved: ~100-300KB per session. |
| Worker thread CPU    | Modest      | Eliminates stack trace fill-in (expensive JVM operation) and exception propagation. Frees worker thread time.                    |
| Console output       | Visible     | Less terminal/GUI spam. Easier to read actual important logs.                                                                    |

**The primary benefit is cleaner logs and less disk writes.**

## Installation

1. [Fabric Loader](https://fabricmc.net/). For the supported Minecraft versions, check the [latest release on GitHub](https://github.com/Mitra-88/Mitras-Auto-Sprinter).
2. Download the QuietAuth `.jar`
3. Drop it into your `.minecraft/mods` folder
4. Start the game

No configuration needed. The mod works automatically.

## License

[Unlicense](https://unlicense.org/): Public domain. Do whatever you want.
