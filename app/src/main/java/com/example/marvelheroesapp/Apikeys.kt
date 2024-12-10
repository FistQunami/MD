package com.example.marvelheroesapp

class ApiKeys {
    companion object {
        val PUBLIC_KEY = ApiKey("40194719adab64b889291299fc5e457e")
        val PRIVATE_KEY = ApiKey("afd8f5333f37f4ede639520016ffc134e4783339")
    }

    class ApiKey(val key: String)
}
