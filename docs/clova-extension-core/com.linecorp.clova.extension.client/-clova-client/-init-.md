[clova-extension-core](../../index.md) / [com.linecorp.clova.extension.client](../index.md) / [ClovaClient](index.md) / [&lt;init&gt;](./-init-.md)

# &lt;init&gt;

`ClovaClient(applicationId: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, verifier: `[`RequestVerifier`](../-request-verifier/index.md)` = RequestVerifierImpl())`

The ClovaClient provides the functionality for handling the requests from Clova platform,
SDK also provides a DSL [(example)](../clova-client.md) to create [ClovaClient](index.md)

### Parameters

`applicationId` - The application id, It must be the same as the one registered in Clova Developer Center.

`verifier` - The request verifier, default is using the built-in request verifier