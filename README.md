# template-backend-kotlin

Backend template for [DynamiaTools](https://dynamia.tools) using Spring Boot and Kotlin. This repository is
consumed by the [DynamiaTools CLI](https://github.com/dynamiatools/framework/tree/main/platform/packages/cli)
(`dynamia new`) to scaffold new Kotlin backends — it is not meant to be used standalone, though it builds and its
test suite runs as-is (`groupId`/`artifactId`/`package` default to `com.example`/`demo`; the CLI renames them to
your own coordinates during generation).

## Stack

- Java 25 (LTS) runtime, Kotlin 2.4.20 (`jvmTarget=25`, `kotlin-spring` compiler plugin for auto-open classes)
- Spring Boot 4 (`spring-boot-starter-parent`)
- DynamiaTools (`tools.dynamia.app`, `tools.dynamia.domain.jpa`)
- Spring Data JPA + HSQLDB (in-memory, for local/dev runs)
- springdoc-openapi (OpenAPI/Swagger UI)
- Virtual threads enabled (`spring.threads.virtual.enabled: true`)

## Requirements

| Tool | Version |
|---|---|
| JDK | 25 (a `.sdkmanrc` is included — run `sdk env` if you use [SDKMAN!](https://sdkman.io)) |
| Maven | none required — use the bundled `./mvnw` wrapper |

## Running locally

```bash
./mvnw spring-boot:run
```

Or build and run the jar:

```bash
./mvnw clean verify
java -jar target/*.jar
```

OpenAPI UI is available at `/swagger-ui.html` once the app is running.

## Template author conventions

This template is consumed by the DynamiaTools CLI's token-replacement pipeline
(`platform/packages/cli/src/utils/replace.ts` in the `framework` repo). When editing this template, keep in mind:

- `groupId`/`artifactId`/`version` in `pom.xml` **must stay valid Maven identifiers** (`com.example` / `demo` /
  `0.0.1-SNAPSHOT`) — Maven rejects `{{TOKEN}}`-style placeholders in those fields outright, so the CLI replaces
  them via exact-string matching instead of literal tokens.
- The parent's Spring Boot `<version>` and the `<dynamia.version>` property must stay **real, resolvable versions**
  at all times (not placeholders) — this repo needs to build standalone for CI and for local development.
- The main source package must stay `com.example.demo` (under `src/main/kotlin` and `src/test/kotlin`) — the CLI
  moves this directory tree to the user's computed base package.
- The main application class must stay named `DemoApplication` — the CLI renames the file and class to
  `<ArtifactId>Application`.
- `<description>` is fully replaced by the CLI when the user provides one; keep a sensible default here for
  standalone builds.

## Why there's a `homeModuleProvider()` bean

DynamiaTools' `ModuleContainer` requires at least one `ModuleProvider` bean at startup. `List<ModuleProvider>`
injection is additive, so this one can stay alongside real modules you add later, or be deleted once you do.
See [dynamiatools/framework#102](https://github.com/dynamiatools/framework/issues/102) for why the framework's own
`emptyModuleProvider()` fallback doesn't currently cover this case.

## CI

`.github/workflows/build.yml` runs `./mvnw -B verify` on every push/PR to catch template breakage before a user
hits it via `dynamia new`.

## License

Apache-2.0 — © Dynamia Soluciones IT SAS
