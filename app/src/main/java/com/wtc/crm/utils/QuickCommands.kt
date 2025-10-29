package com.wtc.crm.utils

/**
 * Utilitário para processar comandos rápidos (/)
 */
object QuickCommands {
    
    data class CommandResult(
        val isCommand: Boolean,
        val command: String? = null,
        val params: Map<String, String> = emptyMap(),
        val processedMessage: String? = null
    )
    
    fun processCommand(input: String, currentUserId: String): CommandResult {
        if (!input.startsWith("/")) {
            return CommandResult(isCommand = false, processedMessage = input)
        }
        
        val parts = input.trim().split(" ", limit = 2)
        val command = parts[0].substring(1).lowercase()
        val message = parts.getOrNull(1) ?: ""
        
        return when (command) {
            "promo" -> CommandResult(
                isCommand = true,
                command = "promo",
                params = mapOf(
                    "title" to message.ifEmpty { "Promoção Especial" },
                    "body" to "Confira nossa promoção especial!",
                    "url" to "https://wtc.com/promo",
                    "type" to "PROMOTION"
                )
            )
            "boleto" -> CommandResult(
                isCommand = true,
                command = "boleto",
                params = mapOf(
                    "title" to "Segunda via de boleto",
                    "body" to "Acesse para gerar sua segunda via de boleto.",
                    "url" to "https://wtc.com/boleto",
                    "type" to "TEXT"
                )
            )
            "agradecer" -> CommandResult(
                isCommand = true,
                command = "agradecer",
                params = mapOf(
                    "title" to "Obrigado!",
                    "body" to "Agradecemos pelo contato! Como podemos ajudar?",
                    "url" to "",
                    "type" to "TEXT"
                )
            )
            "help" -> CommandResult(
                isCommand = true,
                command = "help",
                params = mapOf(
                    "commands" to "/promo - Criar mensagem de promoção\n/boleto - Enviar link de boleto\n/agradecer - Mensagem de agradecimento"
                )
            )
            else -> CommandResult(isCommand = false, processedMessage = input)
        }
    }
    
    fun getAvailableCommands(): List<String> {
        return listOf("/promo", "/boleto", "/agradecer", "/help")
    }
}

